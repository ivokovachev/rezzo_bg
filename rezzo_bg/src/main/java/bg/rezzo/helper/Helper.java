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
			+ "values(null, ?, ?, ?, ?, ?, 0)";
	public static String GET_ALL_EVENTS_QUERY = "select e.id, e.date, e.event_url, e.event_description, e.event_title, p.name\r\n" + 
			"from events e\r\n" + 
			"join places p on (e.place_id = p.id);";
	public static final String GET_ALL_OFFERS_QUERY = "select o.id, o.offer_description, o.offer_title, o.offer_url, p.name\r\n" + 
			"from offers o\r\n" + 
			"join places p on (o.place_id = p.id);";
	public static final String GET_USER_BOOKINGS = "select b.id, b.number_of_tables\r\n" + 
			"from users u\r\n" + 
			"join bookings b on (u.id = b.user_id)\r\n" + 
			"where u.id = ?;";
	public static final String GET_SLOTS = "select s.*\r\n" + 
			"from places p\r\n" + 
			"join slots s on (p.id = s.places_id)\r\n" + 
			"where p.name = ? and s.date = ? and (s.start >= ? and s.end <= ?);";
	public static final String INSERT_SLOT_QUERY = "insert into slots values(null, ?, ?, ?, ?, ?, \r\n" + 
			"(select id\r\n" + 
			"from places\r\n" + 
			"where name = ?), ?);";
	public static final String INSERT_BOOKING_QUERY = "insert into bookings values(null, ?, ?);";
}
