package bg.rezzo.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import bg.rezzo.dto.EditProfileDTO;
import bg.rezzo.dto.ClubInputDTO;
import bg.rezzo.dto.LoginDTO;
import bg.rezzo.dto.OfferInputDTO;
import bg.rezzo.dto.RegistrationDTO;
import bg.rezzo.dto.ReservationDTO;
import bg.rezzo.dto.RestaurantInputDTO;
import bg.rezzo.exception.InvalidPlaceException;
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
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

		Connection con = this.jdbcTemplate.getDataSource().getConnection();
		PreparedStatement st = con.prepareStatement(Helper.QUERY);
		st.setString(1, user.getEmail());
		ResultSet rs = st.executeQuery();
		
		User u = null;
		while(rs.next()) {
			u = new User(rs.getLong(1), rs.getString(2), rs.getString(3),
					rs.getString(4), LocalDate.now(), new Address(rs.getLong(5),
							rs.getString(6), rs.getString(7), rs.getString(8)), rs.getInt(9));
		}
		
		boolean areTheSame = false;
		if(u != null) {
			areTheSame = passwordEncoder.matches(user.getPassword(), u.getPassword());
		}
		
		if(u != null && areTheSame) {
			this.loadUserBookings(u.getId());
		}
		
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
					rs.getString(4), rs.getDate(5).toLocalDate(), new Address(rs.getLong(6),
							rs.getString(7), rs.getString(8), rs.getString(9)), rs.getInt(10));
		}
		
		return u;
	}
	
	public User editProfile(long id, EditProfileDTO user) throws SQLException {
		Connection con = this.jdbcTemplate.getDataSource().getConnection();
		PreparedStatement st = con.prepareStatement("select u.*, a.street, c.name, a.country "
				+ "from users u "
				+ "join address a on(u.address_id = a.id) "
				+ "join cities c on (a.city_id = c.id) "
				+ " where u.id = ?;");
		st.setLong(1, id);
		ResultSet rs = st.executeQuery();
		User u = null;
		while(rs.next()) {
			u = new User(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getString(4),
					rs.getDate(5).toLocalDate(),
					new Address(rs.getLong(6), rs.getString(8), rs.getString(9), rs.getString(10)),
					rs.getInt(7));
		}
		
		Address address = u.getAddress();
		
		if(!(u.getEmail().equals(user.getEmail()) || user.getEmail().equals(""))) {
			u.setEmail(user.getEmail());
		}
		if(!(u.getTelephone().equals(user.getTelephone()) || user.getTelephone().equals(""))) {
			u.setTelephone(user.getTelephone());
		}
		if(!(u.getDateOfBirth().equals(user.getDateOfBirth()))) {
			u.setDateOfBirth(LocalDate.parse(user.getDateOfBirth()));
		}
		if(!(u.getAddress().getStreet().equals(user.getStreet()) || user.getStreet().equals(""))) {
			address.setStreet(user.getStreet());
			u.setAddress(address);
			PreparedStatement streetStatement = con.prepareStatement("update address set street=? where id=?;");
			streetStatement.setString(1, user.getStreet());
			streetStatement.setLong(2, u.getAddress().getId());
			streetStatement.executeUpdate();
		}
		if(!(u.getAddress().getCity().equals(user.getCity()) || user.getCity().equals(""))) {
			address.setCity(user.getCity());
			u.setAddress(address);
			PreparedStatement ps = con.prepareStatement("update address set city_id=?");
			PreparedStatement getCityIdStatement = con.prepareStatement("select id "
					+ "from cities "
					+ "where name=?");
			getCityIdStatement.setString(1, user.getCity());
			ResultSet rs2 = getCityIdStatement.executeQuery();
			long cityId = -1;
			while(rs2.next()) {
				cityId = rs2.getLong(1);
			}
			if(cityId == -1) {
				PreparedStatement insertCityStatement = con.prepareStatement("insert into cities "
						+ "values(null, ?)", Statement.RETURN_GENERATED_KEYS);
				insertCityStatement.setString(1, user.getCity());
				insertCityStatement.executeUpdate();
				ResultSet cityKeys = insertCityStatement.getGeneratedKeys();
				long insertedCityId = -1;
				while(cityKeys.next()) {
					insertedCityId = cityKeys.getLong(1);
				}
				PreparedStatement updateAddressStatement = con.prepareStatement("update address "
						+ "set city_id=? where id=?");
				updateAddressStatement.setLong(1, insertedCityId);
				updateAddressStatement.setLong(2, u.getAddress().getId());
				updateAddressStatement.executeUpdate();
			} else {
				ps.setLong(1, cityId);
				ps.executeUpdate();
			}
		}
		if(!(u.getAddress().getCountry().equals(user.getCountry()) || user.getCountry().equals(""))) {
			address.setCountry(user.getCountry());
			u.setAddress(address);
			PreparedStatement streetStatement = con.prepareStatement("update address set country=? where id=?;");
			streetStatement.setString(1, user.getCountry());
			streetStatement.setLong(2, u.getAddress().getId());
			streetStatement.executeUpdate();
		}
		
		PreparedStatement st2 = con.prepareStatement("update users set "
				+ "email=?, "
				+ "telephone=?, "
				+ "date_of_birth=? "
				+ "where id = ?;");
		st2.setString(1, u.getEmail());
		st2.setString(2, u.getTelephone());
		st2.setDate(3, java.sql.Date.valueOf(u.getDateOfBirth()));
		st2.setLong(4, id);
		st2.executeUpdate();
		
		return u;
	}
	
	
	public boolean registration(RegistrationDTO user) throws SQLException {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(user.getPassword());
		
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
			insertAddressStatement.setString(1, user.getStreet());
			insertAddressStatement.setLong(2, primaryKey1);
			insertAddressStatement.executeUpdate();
			
			ResultSet rs3 = insertAddressStatement.getGeneratedKeys();
			rs3.next();
			long primaryKey2 = rs3.getLong(1);
			
			PreparedStatement insertUserStatement = con.prepareStatement(Helper.INSERT_USER_QUERY,
					Statement.RETURN_GENERATED_KEYS);
			insertUserStatement.setString(1, user.getEmail());
			insertUserStatement.setString(2, hashedPassword);
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
		Integer reservationStart = Integer.parseInt(reservation.getStart())-1;
		Integer reservationEnd = Integer.parseInt(reservation.getEnd())-1;
		
		Connection con = this.jdbcTemplate.getDataSource().getConnection();		
		PreparedStatement st = con.prepareStatement(Helper.GET_SLOTS);
		st.setString(1, reservation.getPlaceName());
		st.setDate(2, java.sql.Date.valueOf(reservation.getDate()));
		st.setString(3, reservationStart.toString()+":00:00");
		st.setString(4, reservationEnd.toString()+":00:00");
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
						PreparedStatement ps = con.prepareStatement(Helper.UPDATE_SLOT_QUERY);
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
				PreparedStatement ps = con.prepareStatement(Helper.UPDATE_SLOT_QUERY);
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
			Integer startTime = s + i - 1 + 3;
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
	
	public Long addRestaurant(RestaurantInputDTO restaurantInputDTO) throws SQLException {
		Connection con = this.jdbcTemplate.getDataSource().getConnection();
		Statement statement = con.createStatement();
		
		ResultSet rs = statement.executeQuery("select * from cities");
		Map<String, Long> cities = new HashMap<String, Long>();
		while(rs.next()) {
			cities.put(rs.getString(2), rs.getLong(1));
		}
		String whichCity = "";
		long whichId = -1;
		for(Entry<String, Long> cityEntry : cities.entrySet()) {
			if(cityEntry.getKey().equals(restaurantInputDTO.getCity())) {
				whichCity = cityEntry.getKey();
				whichId = cityEntry.getValue();
				break;
			}
		}
		PreparedStatement addressStatement = con.prepareStatement(Helper.INSERT_ADDRESS, Statement.RETURN_GENERATED_KEYS);
		addressStatement.setString(1, restaurantInputDTO.getStreet());
		addressStatement.setString(2, restaurantInputDTO.getCountry());

		if(!whichCity.equals("")) {
			addressStatement.setLong(3, whichId);
			addressStatement.executeUpdate();
		} else {
			
			PreparedStatement cityStatement = con.prepareStatement(Helper.INSERT_CITY, Statement.RETURN_GENERATED_KEYS);
			cityStatement.setString(1, restaurantInputDTO.getCity());
			cityStatement.executeUpdate();
			ResultSet keys = cityStatement.getGeneratedKeys();
			long id = -1;
			while(keys.next()) {
				id = keys.getLong(1);
			}
			addressStatement.setLong(3,id);
			addressStatement.executeUpdate();
		}
		
		
		
		ResultSet set = addressStatement.getGeneratedKeys();
		long addressId = -1;
		while(set.next()) {
			 addressId = set.getLong(1);
		}
		Map<String, Long> kitchens = new HashMap<String, Long>();
		ResultSet resSet = statement.executeQuery("select * from kitchens");
		while(resSet.next()) {
			kitchens.put(resSet.getString(2), resSet.getLong(1));
		}
		String whichKitchen = "";
		long kitchenId = -1;
		for(Entry<String, Long> kitchensEntry : kitchens.entrySet()) {
			if(kitchensEntry.getKey().equals(restaurantInputDTO.getKitchenName())) {
				whichKitchen = kitchensEntry.getKey();
				kitchenId = kitchensEntry.getValue();
				break;
			}
		}
		
		Integer start = Integer.parseInt(restaurantInputDTO.getStartWorkingDay()) + 2;
		Integer end = Integer.parseInt(restaurantInputDTO.getEndWorkingDay()) + 2;
		PreparedStatement restaurantStatement = con.prepareStatement(Helper.INSERT_RESTAURANT, Statement.RETURN_GENERATED_KEYS);
		restaurantStatement.setString(1, restaurantInputDTO.getRestaurantName());
		restaurantStatement.setTime(2, java.sql.Time.valueOf(start.toString()+":00:00"));
		restaurantStatement.setTime(3, java.sql.Time.valueOf(end.toString()+":00:00"));
		restaurantStatement.setDouble(4, restaurantInputDTO.getRating());
		restaurantStatement.setString(5, restaurantInputDTO.getDescription());
		restaurantStatement.setLong(6, addressId);
		
		if(!whichKitchen.equals("")) {
			PreparedStatement prepStatement = con.prepareStatement(Helper.GET_RESTAURANT, Statement.RETURN_GENERATED_KEYS);
			prepStatement.setLong(1, kitchenId);
			ResultSet rSet = prepStatement.executeQuery();
			long rId = -1;
			while(rSet.next()) {
				rId = rSet.getLong(1);
			}
			restaurantStatement.setLong(7, rId);
		} else {
			PreparedStatement kitchenStatement = con.prepareStatement(Helper.INSERT_KITCHEN, Statement.RETURN_GENERATED_KEYS);
			kitchenStatement.setString(1, restaurantInputDTO.getKitchenName());
			kitchenStatement.executeUpdate();
			
			ResultSet keys = kitchenStatement.getGeneratedKeys();
			long id = -1;
			while(keys.next()) {
				id = keys.getLong(1);
			}
			
			PreparedStatement midStatement = con.prepareStatement(Helper.INSERT_RESTAURANT_MIDDLE, Statement.RETURN_GENERATED_KEYS);
			midStatement.setLong(1, id);
			midStatement.executeUpdate();
			ResultSet ids = midStatement.getGeneratedKeys();
			long midId = -1;
			while(ids.next()) {
				midId = ids.getLong(1);
			}
			
			restaurantStatement.setLong(7, midId);
		}
		
		restaurantStatement.executeUpdate();
		ResultSet restaurantIdSet = restaurantStatement.getGeneratedKeys();
		long returnedId = -1;
		while(restaurantIdSet.next()) {
			returnedId = restaurantIdSet.getLong(1);
		}
		return returnedId;
	}


	public Long addClub(ClubInputDTO clubInputDTO) throws SQLException {
		
		Connection con = this.jdbcTemplate.getDataSource().getConnection();
		Statement statement = con.createStatement();
		ResultSet rs = statement.executeQuery("select * from cities");
		Map<String, Long> cities = new HashMap<String, Long>();
		while(rs.next()) {
			cities.put(rs.getString(2), rs.getLong(1));
		}
		String whichCity = "";
		long whichId = -1;
		for(Entry<String, Long> cityEntry : cities.entrySet()) {
			if(cityEntry.getKey().equals(clubInputDTO.getCity())) {
				whichCity = cityEntry.getKey();
				whichId = cityEntry.getValue();
				break;
			}
		}
		PreparedStatement addressStatement = con.prepareStatement(Helper.INSERT_ADDRESS, Statement.RETURN_GENERATED_KEYS);
		addressStatement.setString(1, clubInputDTO.getStreet());
		addressStatement.setString(2, clubInputDTO.getCountry());

		if(!whichCity.equals("")) {
			addressStatement.setLong(3, whichId);
			addressStatement.executeUpdate();
		} else {
			PreparedStatement cityStatement = con.prepareStatement(Helper.INSERT_CITY, Statement.RETURN_GENERATED_KEYS);
			cityStatement.setString(1, clubInputDTO.getCity());
			cityStatement.executeUpdate();
			ResultSet keys = cityStatement.getGeneratedKeys();
			long id = -1;
			while(keys.next()) {
				id = keys.getLong(1);
			}
			addressStatement.setLong(3,id);
			addressStatement.executeUpdate();
		}
		ResultSet set = addressStatement.getGeneratedKeys();
		long addressId = -1;
		while(set.next()) {
			 addressId = set.getLong(1);
		}
		
		Map<String, Long> genres = new HashMap<String, Long>();
		ResultSet resSet = statement.executeQuery("select * from music");
		while(resSet.next()) {
			genres.put(resSet.getString(2), resSet.getLong(1));
		}
		String whichMusic = "";
		long musicId = -1;
		for(Entry<String, Long> musicEntry : genres.entrySet()) {
			if(musicEntry.getKey().equals(clubInputDTO.getGenreName())) {
				whichMusic = musicEntry.getKey();
				musicId = musicEntry.getValue();
				break;
			}
		}
		
		Integer start = Integer.parseInt(clubInputDTO.getStartWorkingDay())+2;
		Integer end = Integer.parseInt(clubInputDTO.getEndWorkingDay())+2;
		PreparedStatement clubStatement = con.prepareStatement(Helper.INSERT_CLUB, Statement.RETURN_GENERATED_KEYS);
		clubStatement.setString(1, clubInputDTO.getClubName());
		System.out.println(clubInputDTO.getStartWorkingDay());
		System.out.println(clubInputDTO.getEndWorkingDay());
		clubStatement.setTime(2, java.sql.Time.valueOf(start.toString()+":00:00"));
		clubStatement.setTime(3, java.sql.Time.valueOf(end.toString()+":00:00"));
		clubStatement.setDouble(4, clubInputDTO.getRating());
		clubStatement.setString(5, clubInputDTO.getDescription());
		clubStatement.setLong(6, addressId);
		
		if(!whichMusic.equals("")) {
			PreparedStatement prepStatement = con.prepareStatement(Helper.GET_CLUB, Statement.RETURN_GENERATED_KEYS);
			prepStatement.setLong(1, musicId);
			ResultSet rSet = prepStatement.executeQuery();
			long rId = -1;
			while(rSet.next()) {
				rId = rSet.getLong(1);
			}
			clubStatement.setLong(7, rId);
		} else {
			PreparedStatement musicStatement = con.prepareStatement(Helper.INSERT_GENRE, Statement.RETURN_GENERATED_KEYS);
			musicStatement.setString(1, clubInputDTO.getGenreName());
			musicStatement.executeUpdate();
			
			ResultSet keys = musicStatement.getGeneratedKeys();
			long id = -1;
			while(keys.next()) {
				id = keys.getLong(1);
			}
			
			PreparedStatement midStatement = con.prepareStatement(Helper.INSERT_CLUB_MIDDLE, Statement.RETURN_GENERATED_KEYS);
			midStatement.setLong(1, id);
			midStatement.executeUpdate();
			ResultSet ids = midStatement.getGeneratedKeys();
			long midId = -1;
			while(ids.next()) {
				midId = ids.getLong(1);
			}
			
			clubStatement.setLong(7, midId);
		}
		
		clubStatement.executeUpdate();
		ResultSet clubIdSet = clubStatement.getGeneratedKeys();
		long returnedId = -1;
		while(clubIdSet.next()) {
			returnedId = clubIdSet.getLong(1);
		}
		return returnedId;
	}


	public Long addOffer(OfferInputDTO offerInputDTO) throws SQLException, InvalidPlaceException {
		Connection con = jdbcTemplate.getDataSource().getConnection();
		PreparedStatement offerStatement = con.prepareStatement(Helper.INSERT_OFFER, Statement.RETURN_GENERATED_KEYS);
		offerStatement.setString(1, offerInputDTO.getDescription());
		offerStatement.setString(2, offerInputDTO.getTitle());
		offerStatement.setString(3, offerInputDTO.getUrl());
		offerStatement.setInt(5, offerInputDTO.getPrice());
		
		PreparedStatement placeStatement = con.prepareStatement(Helper.GET_RESTAURANT_ID);
		placeStatement.setString(1, offerInputDTO.getPlaceName());
		ResultSet rs = placeStatement.executeQuery();
		while(rs.next()) {
			Long id = rs.getLong(1);
			offerStatement.setLong(4, id);
			offerStatement.executeUpdate();
			ResultSet keySet = offerStatement.getGeneratedKeys();
			keySet.next();
			long returnedId = keySet.getLong(1);
			return returnedId;
			
		}
		throw new InvalidPlaceException("There is no such place in the site!");
	}
}
