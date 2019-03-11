package bg.rezzo.controllers;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import bg.rezzo.dao.CityDAO;
import bg.rezzo.dto.RestaurantOutputDTO;

@RestController
public class CityController {
	
	@Autowired
	private CityDAO cityDAO;
	
	@GetMapping("/restaurants/cities/{cityId}")
	public List<RestaurantOutputDTO> getRestaurantsByCity (@PathVariable long cityId, HttpServletResponse response) throws SQLException {
			return this.cityDAO.getRestaurantsByCity(cityId);
		
	}
	
	
}
