package bg.rezzo.helper;



public class Helper {
	public static final String GET_ALL_USERS_DATA_QUERY =
			"select u.id, u.email, u.password, u.telephone, a.id, a.street, a.country, c.name\r\n" + 
			"from users u\r\n" + 
			"join address a on (u.address_id = a.id)\r\n" + 
			"join cities c on (a.city_id = c.id);\r\n" + 
			"";
	public static final String QUERY = "select u.id, u.email, u.password, u.telephone, a.id, a.street, a.country, c.name, u.is_admin\r\n" + 
			"from users u\r\n" + 
			"join address a on (u.address_id = a.id)\r\n" + 
			"join cities c on (a.city_id = c.id)\r\n" + 
			"where u.email = ?;";

	public static final String GET_USER_PROFILE_QUERY = "select u.id, u.email, u.password, u.telephone, u.date_of_birth, a.id, a.street, a.country, c.name, u.is_admin\r\n" + 
			"from users u\r\n" + 
			"join address a on (u.address_id = a.id)\r\n" + 
			"join cities c on (a.city_id = c.id)\r\n" + 
			"where u.id = ?;";
	public static final String GET_ALL_USERS_EMAILS_QUERY = "select u.email\r\n" + 
			"from users u\r\n" + 
			"join address a on (u.address_id = a.id)\r\n" + 
			"join cities c on (a.city_id = c.id);";
	public static final String INSERT_CITY_QUERY = "insert into cities "
			+ "values(null, ?)";
	public static final String INSERT_ADDRESS_QUERY = "insert into address "
			+ "values(null, ?, 'Bulgaria', ?)";
	public static final String INSERT_USER_QUERY = "insert into users "
			+ "values(null, ?, ?, ?, ?, ?, 1)";
	public static final String GET_ALL_EVENTS_QUERY = "select e.id, e.date, e.event_url, e.event_description, e.event_title, p.name\r\n" + 
			"from events e\r\n" + 
			"join places p on (e.place_id = p.id);";
	public static final String GET_ALL_OFFERS_QUERY = "select o.id, o.offer_description, o.offer_title, o.offer_url, p.name, o.offer_price\r\n" + 
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
	public static final String INSERT_SLOT_QUERY = "insert into slots values(null, ?, ?, ?, ?, ?, \r\n" + 
			"(select id\r\n" + 
			"from places\r\n" + 
			"where name = ?), ?);";
	public static final String INSERT_BOOKING_QUERY = "insert into bookings values(null, ?, ?);";
	public static final String INSERT_CITY = "insert into cities values(null, ?);";
	public static final String INSERT_ADDRESS = "insert into address values(null, ?, ?, ?);";
	public static final String INSERT_KITCHEN = "insert into kitchens values(null, ?);";
	public static final String INSERT_GENRE = "insert into music values(null, ?);";
	public static final String INSERT_RESTAURANT_MIDDLE = "insert into restaurants values (null, ?);";
	public static final String INSERT_CLUB_MIDDLE = "insert into clubs values (null, ?);";
	public static final String GET_RESTAURANT = "select r.id from restaurants r where r.kitchen_id = ?;";
	public static final String GET_CLUB = "select c.id from clubs c where c.music_id = ?;";
	public static final String INSERT_CLUB = "insert into places values (null, ?, ?, ?, ?, ?, 10, ?, null, ?);";
	public static final String INSERT_RESTAURANT = "insert into places values (null, ?, ?, ?, ?, ?, 10, ?, ?, null);";
	public static final String INSERT_OFFER = "insert into offers values (null, ?, ?, ?, ?, ?);";
	public static final String GET_RESTAURANT_ID = "select p.id from places p where p.name = ?;";
	public static final String INSERT_EVENT = "insert into events values(null, ?, ?, ?, ?, ?);";

	public static final String UPDATE_SLOT_QUERY = "update slots set "
			+ "free_tables = ? where id = ?;";
	public static final String GET_ALL_RESTAURANTS_WITH_EVENTS_QUERY = "select p.id, p.name, k.name, k.id, p.rating "
				+ "from kitchens k "
				+ "join restaurants r on (k.id = r.kitchen_id) "
				+ "join places p on (r.id = p.restaurant_id) "
				+ "join events e on (p.id = e.place_id)";
	public static final String GET_ALL_RESTAURANTS_WITH_OFFERS_QUERY = "select p.id, p.name, k.name, k.id, p.rating "
				+ "from kitchens k "
				+ "join restaurants r on (k.id = r.kitchen_id) "
				+ "join places p on (r.id = p.restaurant_id) "
				+ "join offers o on (p.id = o.place_id)";
	public static final String GET_ALL_CLUBS_WITH_EVENTS_QUERY = "select p.id, p.name, m.genre, m.id, p.rating "
				+ "from music m "
				+ "join clubs c on (m.id = c.music_id) "
				+ "join places p on (c.id = p.restaurant_id) "
				+ "join events e on (p.id = e.place_id)";
	public static final String GET_ALL_CLUBS_WITH_OFFERS_QUERY = "select p.id, p.name, m.genre, m.id, p.rating "
				+ "from music m "
				+ "join clubs c on (m.id = c.music_id) "
				+ "join places p on (c.id = p.restaurant_id) "
				+ "join offers o on (p.id = o.place_id)";
}
