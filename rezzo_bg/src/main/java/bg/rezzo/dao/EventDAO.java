package bg.rezzo.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import bg.rezzo.helper.Helper;
import bg.rezzo.model.Address;
import bg.rezzo.model.Club;
import bg.rezzo.model.Event;
import bg.rezzo.model.Offer;
import bg.rezzo.model.Restaurant;

@Component
public class EventDAO {
	
	private JdbcTemplate jdbcTemplate;
	
	public EventDAO() {}
	
	
	public List<Event> getAllEvents() throws SQLException {
		List<Event> events = new LinkedList<Event>();
		Connection con = this.jdbcTemplate.getDataSource().getConnection();
		Statement statement = con.createStatement();
		ResultSet rs = statement.executeQuery(Helper.GET_ALL_EVENTS_QUERY);
		while(rs.next()) {
			events.add(new Event(rs.getLong(1), rs.getDate(2).toLocalDate(), rs.getString(6),
					rs.getString(3), rs.getString(4), rs.getString(5)));
		}
		
		return events;
	}
	
	public List<Event> getAllEventsSortedByDate(String sortBy) throws SQLException {
		List<Event> events = new LinkedList<Event>();
		Connection con = this.jdbcTemplate.getDataSource().getConnection();
		Statement statement = con.createStatement();
		ResultSet rs = statement.executeQuery(Helper.GET_ALL_EVENTS_QUERY);
		while(rs.next()) {
			events.add(new Event(rs.getLong(1), rs.getDate(2).toLocalDate(), rs.getString(6),
					rs.getString(3), rs.getString(4), rs.getString(5)));
		}
		
		if(sortBy != null && sortBy.equals("date")) {
			events = events
			.stream()
			.sorted((event1, event2) -> (event1.getDate().compareTo(event2.getDate())))
			.collect(Collectors.toList());	
		}
		
		return events;
	}
	
	
	@Autowired
	private void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
}
