package bg.rezzo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import bg.rezzo.dto.ClubOutputDTO;
import bg.rezzo.dto.RestaurantOutputDTO;
import bg.rezzo.helper.Helper;
import bg.rezzo.model.Address;
import bg.rezzo.model.Club;
import bg.rezzo.model.Restaurant;

@Component
public class CityDAO {
	private JdbcTemplate jdbcTemplate;
	
	public CityDAO(){}

	public List<RestaurantOutputDTO> getRestaurantsByCity(long cityId) throws SQLException {
		Connection con = jdbcTemplate.getDataSource().getConnection();
		PreparedStatement statement = con.prepareStatement(Helper.GET_RESTAURANTS_BY_CITY);
		statement.setLong(1, cityId);
		
		ResultSet resultSet = statement.executeQuery();
		List<Restaurant> restaurants = new LinkedList<Restaurant>();

		while(resultSet.next()) {
			restaurants.add(new Restaurant(resultSet.getLong(1), resultSet.getString(2), resultSet.getTime(3).toString(),
					resultSet.getTime(4).toString(), resultSet.getDouble(5), resultSet.getString(6),
					resultSet.getInt(7), 
					new Address(0, resultSet.getString(8),
							resultSet.getString(9), resultSet.getString(10)), resultSet.getString(11)));
		}
		

		PreparedStatement returnCity = con.prepareStatement(Helper.GET_CITY);
		returnCity.setLong(1, cityId);
		ResultSet citiesSet = returnCity.executeQuery();
		String city = "";
		while(citiesSet.next()) {
			city = citiesSet.getString(1);
		}
		
		
		List<RestaurantOutputDTO> outputRestaurants = new LinkedList<RestaurantOutputDTO>();
		if(restaurants.size() != 0) {
			for(Restaurant r : restaurants) {
				outputRestaurants.add(new RestaurantOutputDTO(r.getId(), r.getName(), r.getKitchen(), r.getRating(), city));
			}
		}
			
		return outputRestaurants;
	}
	
	
	
	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<ClubOutputDTO> getClubsByCity(long cityId) throws SQLException {
		Connection con = jdbcTemplate.getDataSource().getConnection();
		PreparedStatement statement = con.prepareStatement(Helper.GET_CLUBS_BY_CITY);
		statement.setLong(1, cityId);
		
		ResultSet resultSet = statement.executeQuery();
		List<Club> clubs = new LinkedList<Club>();
		while(resultSet.next()) {
			clubs.add(new Club(resultSet.getLong(1), resultSet.getString(2), resultSet.getTime(3).toString(),
					resultSet.getTime(4).toString(), resultSet.getDouble(5), resultSet.getString(6),
					resultSet.getInt(7), 
					new Address(0, resultSet.getString(8),
							resultSet.getString(9), resultSet.getString(10)), resultSet.getString(11)));
		}
		
		PreparedStatement returnCity = con.prepareStatement(Helper.GET_CITY);
		returnCity.setLong(1, cityId);
		ResultSet citiesSet = returnCity.executeQuery();
		String city = "";
		while(citiesSet.next()) {
			 city = citiesSet.getString(1);
		}
		
		List<ClubOutputDTO> outputClubs = new LinkedList<ClubOutputDTO>();
		if(clubs.size() > 0) {
			for(Club c : clubs) {
				outputClubs.add(new ClubOutputDTO(c.getId(), c.getName(), c.getMusic(), c.getRating(), city));
			}
		}
		
		return outputClubs;
	}
}
