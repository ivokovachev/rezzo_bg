package bg.rezzo.controllers;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import bg.rezzo.dao.RestaurantDAO;
import bg.rezzo.dto.RestaurantDTO;
import bg.rezzo.exception.NoSuchRestaurantException;
import bg.rezzo.model.Restaurant;

@RestController
public class RestaurantController {
	
	@Autowired
	private RestaurantDAO restaurantDAO;
	
//	@GetMapping("/restaurants")
//	public List<RestaurantDTO> getAllRestaurants() throws SQLException {
//		return this.restaurantDAO.getAllRestaurants();
//	}
	
	@GetMapping("/restaurants")
	public List<RestaurantDTO> getAllRestaurants(@RequestParam (name = "sortBy", required = false) String sortBy,
			@RequestParam (name = "kitchenId", required = false) Long kitchenId) throws SQLException{
		return restaurantDAO.getAllRestaurants(sortBy, kitchenId);
		
	}
	
	
	@GetMapping("/restaurants/{restaurantId}")
	public Restaurant getRestaurantById(@PathVariable long restaurantId, HttpServletResponse response) throws SQLException {
			try {
				return this.restaurantDAO.getRestaurantById(restaurantId);
			} catch (NoSuchRestaurantException e) {
				response.setStatus(404);
				return null;
			}
		}
}
