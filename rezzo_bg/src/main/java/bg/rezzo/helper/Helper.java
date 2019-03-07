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
}
