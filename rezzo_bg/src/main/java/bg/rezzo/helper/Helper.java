package bg.rezzo.helper;



public class Helper {
	public static String GET_ALL_USERS_DATA_QUERY =
			"select u.id, u.email, u.password, u.telephone, a.id, a.street, a.country, c.name\r\n" + 
			"from users u\r\n" + 
			"join address a on (u.address_id = a.id)\r\n" + 
			"join cities c on (a.city_id = c.id);\r\n" + 
			"";
	public static String QUERY = "select u.id, u.email, u.password, u.telephone, a.id, a.street, a.country, c.name\r\n" + 
			"from users u\r\n" + 
			"join address a on (u.address_id = a.id)\r\n" + 
			"join cities c on (a.city_id = c.id)\r\n" + 
			"where u.email = ?;";
	public static String GET_USER_PROFILE_QUERY = "select u.id, u.email, u.password, u.telephone, a.id, a.street, a.country, c.name\r\n" + 
			"from users u\r\n" + 
			"join address a on (u.address_id = a.id)\r\n" + 
			"join cities c on (a.city_id = c.id)\r\n" + 
			"where u.id = ?;";
	public static String GET_ALL_USERS_EMAILS_QUERY = "select u.email\r\n" + 
			"from users u\r\n" + 
			"join address a on (u.address_id = a.id)\r\n" + 
			"join cities c on (a.city_id = c.id);";
	public static String INSERT_CITY_QUERY = "insert into cities "
			+ "values(null, ?)";
	public static String INSERT_ADDRESS_QUERY = "insert into address "
			+ "values(null, '', 'Bulgaria', ?)";
	public static String INSERT_USER_QUERY = "insert into users "
			+ "values(null, ?, ?, ?, ?, ?)";
	public static String GET_ALL_EVENTS_QUERY = "select e.id, e.date, e.event_url, e.event_description, e.event_title, p.name\r\n" + 
			"from events e\r\n" + 
			"join places p on (e.place_id = p.id);";
	public static final String GET_ALL_OFFERS_QUERY = "select o.id, o.offer_description, o.offer_title, o.offer_url, p.name\r\n" + 
			"from offers o\r\n" + 
			"join places p on (o.place_id = p.id);";
	public static final String GET_USER_BOOKINGS = "select b.id, b.number_of_tables, b.date, b.place_id, s.id, s.start, s.end, s.free_tables, s.discount\r\n" + 
			"from slots s\r\n" + 
			"join bookings b on (s.id = b.slot_id)\r\n" + 
			"join places p on (b.place_id = p.id)\r\n" + 
			"where b.user_id = ?;";
	public static final String GET_SLOT_FREE_TABLES = "select s.free_tables\r\n" + 
			"from places p\r\n" + 
			"join restaurants r on (p.restaurant_id = r.id)\r\n" + 
			"join restaurants_has_slots rs on (r.id = rs.restaurant_id)\r\n" + 
			"join slots s on (rs.slot_id = s.id) \r\n" + 
			"where p.name = ? and s.start = ?;";
	
	public static final String GET_DETAILS_FOR_ALL_RESTAURANTS = "select p.id, p.name, k.name, k.id, p.rating from places p\r\n" + 
			"join restaurants r on (r.id = p.restaurant_id)\r\n" + 
			"join kitchens k  on (k.id = r.kitchen_id)\r\n" + 
			"where restaurant_id is not null;";
	
	public static final String GET_DETAILS_FOR_ALL_CLUBS = "select p.id, p.name, m.genre, m.id, p.rating from places p\r\n" + 
			"join clubs c on (c.id = p.club_id)\r\n" + 
			"join music m on (m.id = c.music_id)\r\n" + 
			"where club_id is not null;";
	
	public static final String GET_CLUB_DETAILS = "select p.id, p.name, p.start_working_day, p.end_working_day, p.rating, p.description, p.max_free_tables, a.street, a.country, ci.name, m.genre\r\n" + 
			"from cities ci \r\n" + 
			"join address a on (ci.id = a.city_id)\r\n" + 
			"join places p on (a.id = p.address_id)\r\n" + 
			"join clubs c on (p.club_id = c.id)\r\n" + 
			"join music m on (c.music_id = m.id)\r\n" + 
			"where p.id = ?;";
	public static final String GET_RESTAURANT_DETAILS = "select p.id, p.name, p.start_working_day, p.end_working_day, p.rating, p.description, p.max_free_tables, a.street, a.country, ci.name, k.name\r\n" + 
			"from cities ci \r\n" + 
			"join address a on (ci.id = a.city_id)\r\n" + 
			"join places p on (a.id = p.address_id)\r\n" + 
			"join restaurants r on (p.restaurant_id = r.id)\r\n" + 
			"join kitchens k on (r.kitchen_id = k.id)\r\n" + 
			"where p.id = ?;";
}
