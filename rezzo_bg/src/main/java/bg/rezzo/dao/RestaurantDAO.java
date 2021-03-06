package bg.rezzo.dao; 

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import bg.rezzo.dto.RestaurantDTO;
import bg.rezzo.exception.NoSuchRestaurantException;
import bg.rezzo.helper.Helper;
import bg.rezzo.model.Address;
import bg.rezzo.model.Restaurant;

@Component
public class RestaurantDAO {
	private JdbcTemplate jdbcTemplate;
	
	public RestaurantDAO() {}
	
	public List<RestaurantDTO> getAllRestaurants(String sortBy, Long kitchenId) throws SQLException{
		List<RestaurantDTO> restaurants = new LinkedList<RestaurantDTO>();
		Connection con = jdbcTemplate.getDataSource().getConnection();
		Statement statement = con.createStatement();
		ResultSet resultSet = statement.executeQuery(Helper.GET_DETAILS_FOR_ALL_RESTAURANTS);
		
		while(resultSet.next()) {
			if(kitchenId == null || resultSet.getLong(4) == kitchenId) {
					restaurants.add(new RestaurantDTO(resultSet.getLong(1), resultSet.getString(2), resultSet.getString(3), resultSet.getLong(4), resultSet.getDouble(5)));
			}
		}
		
		if(sortBy != null) {
			switch(sortBy) {
			case "restaurantName": restaurants.sort((r1, r2) -> r1.getRestaurantName().compareTo(r2.getRestaurantName()));break;
			case "rating": restaurants = restaurants
					.stream()
					.sorted((r1, r2) -> (new Double(r1.getRating()).compareTo(new Double(r2.getRating()))))
					.collect(Collectors.toList()); break;
			default: return restaurants;
				
			}
		}
		return restaurants;
	}
	
	
	public Restaurant getRestaurantById(long id) throws SQLException, NoSuchRestaurantException {
		Connection con = jdbcTemplate.getDataSource().getConnection();
		PreparedStatement statement = con.prepareStatement(Helper.GET_RESTAURANT_DETAILS);
		statement.setLong(1, id);
		Restaurant restaurant = null;
		ResultSet resultSet = statement.executeQuery();
		while(resultSet.next()) {
			restaurant = new Restaurant(resultSet.getLong(1), resultSet.getString(2), resultSet.getTime(3).toString(),
					resultSet.getTime(4).toString(), resultSet.getDouble(5), resultSet.getString(6),
					resultSet.getInt(7), 
					new Address(0, resultSet.getString(8),
							resultSet.getString(9), resultSet.getString(10)), resultSet.getString(11));
		}
		if(restaurant == null) {
			throw new NoSuchRestaurantException("No such restaurant");
		}
		return restaurant;
		
	}
	@Autowired
	private void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<RestaurantDTO> getAllRestaurantsWithEvents() throws SQLException {
		Connection con = this.jdbcTemplate.getDataSource().getConnection();
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(Helper.GET_ALL_RESTAURANTS_WITH_EVENTS_QUERY);
		
		List<RestaurantDTO> restaurants = new LinkedList<RestaurantDTO>();
		while(rs.next()) {
			restaurants.add(new RestaurantDTO(rs.getLong(1), rs.getString(2),
					rs.getString(3), rs.getLong(4), rs.getDouble(5)));
		}
		
		return restaurants;
	}

	public List<RestaurantDTO> getAllRestaurantsWithOffers() throws SQLException {
		Connection con = this.jdbcTemplate.getDataSource().getConnection();
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(Helper.GET_ALL_RESTAURANTS_WITH_OFFERS_QUERY);
		
		List<RestaurantDTO> restaurants = new LinkedList<RestaurantDTO>();
		while(rs.next()) {
			restaurants.add(new RestaurantDTO(rs.getLong(1), rs.getString(2), rs.getString(3),
					rs.getLong(4), rs.getDouble(5)));
		}
		
		return restaurants;
	}
}
