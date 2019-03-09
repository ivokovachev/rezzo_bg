package bg.rezzo.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;


import bg.rezzo.dto.LoginDTO;
import bg.rezzo.dto.RegistrationDTO;
import bg.rezzo.dto.ReservationDTO;
import bg.rezzo.helper.Helper;
import bg.rezzo.model.Address;
import bg.rezzo.model.Booking;
import bg.rezzo.model.Slot;
import bg.rezzo.model.User;

@Component
public class UserDAO {	
	
	private JdbcTemplate jdbcTemplate;
	private List<Booking> bookings = new LinkedList<Booking>();
	
	public UserDAO() throws SQLException {}
	
	public User login(LoginDTO user) throws SQLException {
		Connection con = this.jdbcTemplate.getDataSource().getConnection();
		PreparedStatement st = con.prepareStatement(Helper.QUERY);
		st.setString(1, user.getEmail());
		ResultSet rs = st.executeQuery();
		
		User u = null;
		while(rs.next()) {
			u = new User(rs.getLong(1), rs.getString(2), rs.getString(3),
					rs.getString(4), LocalDate.now(), new Address(rs.getLong(5),
							rs.getString(6), rs.getString(8), rs.getString(7)));
		}
		
		this.loadUserBookings(u.getId());
		
		return u;
	}

	public User getUser(long id) throws SQLException {
		Connection con = this.jdbcTemplate.getDataSource().getConnection();
		PreparedStatement st = con.prepareStatement(Helper.GET_USER_PROFILE_QUERY);
		st.setLong(1, id);
		ResultSet rs = st.executeQuery();
		
		User u = null;
		while(rs.next()) {
			u = new User(rs.getLong(1), rs.getString(2), rs.getString(3),
					rs.getString(4), LocalDate.now(), new Address(rs.getLong(5),
							rs.getString(6), rs.getString(8), rs.getString(7)));
		}
		
		return u;
	}
	
	public boolean registration(RegistrationDTO user) throws SQLException {
		List<String> emails = new LinkedList<String>();
		Connection con = this.jdbcTemplate.getDataSource().getConnection();
		con.setAutoCommit(false);
		try {
			PreparedStatement st = con.prepareStatement(Helper.GET_ALL_USERS_EMAILS_QUERY);
			ResultSet rs = st.executeQuery();
			while(rs.next()) {
				emails.add(rs.getString(1));
			}
			
			if(emails.contains(user.getEmail())) {
				return false;
			}
			
			PreparedStatement insertCityStatement = con.prepareStatement(Helper.INSERT_CITY_QUERY,
					Statement.RETURN_GENERATED_KEYS);
			insertCityStatement.setString(1, user.getCity());
			insertCityStatement.executeUpdate();
			
			ResultSet rs2 = insertCityStatement.getGeneratedKeys();
			rs2.next();
			long primaryKey1 = rs2.getLong(1);
			
			PreparedStatement insertAddressStatement = con.prepareStatement(Helper.INSERT_ADDRESS_QUERY,
					Statement.RETURN_GENERATED_KEYS);
			insertAddressStatement.setLong(1, primaryKey1);
			insertAddressStatement.executeUpdate();
			
			ResultSet rs3 = insertAddressStatement.getGeneratedKeys();
			rs3.next();
			long primaryKey2 = rs3.getLong(1);
			
			PreparedStatement insertUserStatement = con.prepareStatement(Helper.INSERT_USER_QUERY,
					Statement.RETURN_GENERATED_KEYS);
			insertUserStatement.setString(1, user.getEmail());
			insertUserStatement.setString(2, user.getPassword());
			insertUserStatement.setString(3, user.getTelephone());
			insertUserStatement.setDate(4, Date.valueOf(user.getDateOfBirth()));
			insertUserStatement.setLong(5, primaryKey2);
			insertUserStatement.executeUpdate();
			con.commit();
			
			return true;
		} catch(Exception ex) {
			con.rollback();
			throw ex;
		} finally {
			con.setAutoCommit(true);
		}
	}
	
	public List<Booking> getUserBookings(long id) throws SQLException {
		List<Booking> bookings = new LinkedList<Booking>();
		Connection con = this.jdbcTemplate.getDataSource().getConnection();
		PreparedStatement st = con.prepareStatement(Helper.GET_USER_BOOKINGS);
		st.setLong(1, id);
		ResultSet rs = st.executeQuery();
		while(rs.next()) {
			bookings.add(new Booking(rs.getLong(1), rs.getInt(2), null));
		}
		
		return bookings;
	}
	
	public boolean makeReservation(ReservationDTO reservation, long id) throws SQLException {
		List<Slot> slots = new LinkedList<Slot>();
		Connection con = this.jdbcTemplate.getDataSource().getConnection();		
		PreparedStatement st = con.prepareStatement(Helper.GET_SLOTS);
		st.setString(1, reservation.getPlaceName());
		st.setDate(2, java.sql.Date.valueOf(reservation.getDate()));
		st.setString(3, reservation.getStart()+":00:00");
		st.setString(4, reservation.getEnd()+":00:00");
		ResultSet rs = st.executeQuery();

		while(rs.next()) {
			slots.add(new Slot(rs.getLong(1), rs.getInt(2), rs.getDouble(3),
					rs.getTime(4).toString(), rs.getTime(5).toString(),
					rs.getDate(6).toLocalDate(), rs.getLong(7), rs.getLong(8)));
		}

		if(slots.isEmpty()) {		
			PreparedStatement insertBookingStatement = con.prepareStatement(Helper.INSERT_BOOKING_QUERY,
					Statement.RETURN_GENERATED_KEYS);
			insertBookingStatement.setInt(1, reservation.getNumberOfTables());
			insertBookingStatement.setLong(2, id);
			insertBookingStatement.executeUpdate();
			
			ResultSet rsKeys = insertBookingStatement.getGeneratedKeys();
			rsKeys.next();
			long bookingId = rsKeys.getLong(1);
			this.insertSlots(reservation, bookingId);
			this.bookings.add(new Booking(bookingId, reservation.getNumberOfTables(), null));
			return true;
		} 
		
		for(Slot slot : slots) {
			if(slot.getFreeTables() < reservation.getNumberOfTables()) {
				return false;
			}
		}
		
		PreparedStatement insertBookingStatement = con.prepareStatement(Helper.INSERT_BOOKING_QUERY,
				Statement.RETURN_GENERATED_KEYS);
		
		insertBookingStatement.setInt(1, reservation.getNumberOfTables());
		insertBookingStatement.setLong(2, id);
		insertBookingStatement.executeUpdate();
		ResultSet rsKeys = insertBookingStatement.getGeneratedKeys();
		rsKeys.next();
		long bookingId = rsKeys.getLong(1);
		
		int start = Integer.parseInt(reservation.getStart());
		int end = Integer.parseInt(reservation.getEnd());
		int diff = end - start;
		if(start > end) {
			diff = end+24-end;
		}
		if(slots.size() < diff) {
			for(int i = start; i <= end-1; i++) {
				boolean isPresented = false;
				for(Slot s : slots) {
					int j = i;
					if(j >= 24) {
						j -= 24;
					}
					if(j == Integer.parseInt(s.getStart().split(":")[0])+1) {
						PreparedStatement ps = con.prepareStatement("update slots set"
								+ " free_tables = ? where id = ?;");
						ps.setInt(1, s.getFreeTables()-reservation.getNumberOfTables());
						ps.setLong(2, s.getId());
						ps.executeUpdate();
						isPresented = true;
					}
				}
				if(!isPresented) {
					this.insertSlots(new ReservationDTO(reservation.getPlaceName(), reservation.getDate(),
							new Integer(i).toString(), new Integer(i+1).toString(),
							reservation.getNumberOfTables()), bookingId);
				}
			}
		} else {
			for(Slot s : slots) {
				PreparedStatement ps = con.prepareStatement("update slots set"
						+ " free_tables = ? where id = ?;");
				ps.setInt(1, s.getFreeTables()-reservation.getNumberOfTables());
				ps.setLong(2, s.getId());
				ps.executeUpdate();
			}
		}
		
		return true;
	}
	
	 
	
	
	@Autowired
	private void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	private void loadUserBookings(long id) throws SQLException {
		Connection con = this.jdbcTemplate.getDataSource().getConnection();
		PreparedStatement st = con.prepareStatement(Helper.GET_USER_BOOKINGS);
		st.setLong(1, id);
		ResultSet rs = st.executeQuery();
		while(rs.next()) {
			this.bookings.add(new Booking(rs.getLong(1), rs.getInt(2), null));
		}
	}
	
	private void insertSlots(ReservationDTO reservation, Long bookingId) throws SQLException {
		Connection con = this.jdbcTemplate.getDataSource().getConnection();
		int s = Integer.parseInt(reservation.getStart());
		int e = Integer.parseInt(reservation.getEnd());
		int diff = e-s;
		if(s > e) {
			diff = e+24-s;
		}
		for(int i = 0; i < diff; i++) {
			Integer startTime = s + i - 1;
			Integer endTime = startTime + 1;
			if(startTime >= 24) {
				startTime -= 24;
			}
			PreparedStatement st = con.prepareStatement(Helper.INSERT_SLOT_QUERY);
			st.setInt(1, 10 - reservation.getNumberOfTables());
			st.setDouble(2, 0.25);
			st.setTime(3, java.sql.Time.valueOf(startTime.toString()+":00:00"));
			st.setTime(4, java.sql.Time.valueOf(endTime.toString()+":00:00"));
			st.setDate(5, java.sql.Date.valueOf(reservation.getDate()));
			st.setString(6, reservation.getPlaceName());
			st.setLong(7, bookingId);
			st.executeUpdate();
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
