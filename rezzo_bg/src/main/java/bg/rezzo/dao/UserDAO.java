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

import org.apache.tomcat.jni.Time;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;


import bg.rezzo.dto.BookingDTO;
import bg.rezzo.dto.LoginDTO;
import bg.rezzo.dto.RegistrationDTO;
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
			bookings.add(new Booking(rs.getLong(1), rs.getInt(2), rs.getDate(3).toLocalDate(),
					new Slot(rs.getLong(5), rs.getInt(8), rs.getDouble(9),
							rs.getTime(6).toString(), rs.getTime(7).toString()), null, rs.getLong(4)));
		}
		
		return bookings;
	}
	
	public boolean hasFreeTables(String placeName, String start, String end, int tables) throws SQLException {
		Connection con = this.jdbcTemplate.getDataSource().getConnection();
		PreparedStatement st = con.prepareStatement(Helper.GET_SLOT_FREE_TABLES);
		st.setString(1, placeName);
		st.setString(2, start);
		ResultSet rs = st.executeQuery();
		
		int actualFreeTables = 0;
		while(rs.next()) {
			actualFreeTables = rs.getInt(1);
		}
		
		return actualFreeTables > tables ? true : false;
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
			this.bookings.add(new Booking(rs.getLong(1), rs.getInt(2), rs.getDate(3).toLocalDate(),
					new Slot(rs.getLong(5), rs.getInt(8), rs.getDouble(9),
							rs.getTime(6).toString(), rs.getTime(7).toString()), null, rs.getLong(4)));
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
