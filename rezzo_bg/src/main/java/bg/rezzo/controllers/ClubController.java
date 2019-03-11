package bg.rezzo.controllers;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import bg.rezzo.dao.ClubDAO;
import bg.rezzo.dto.ClubDTO;
import bg.rezzo.exception.NoSuchClubException;
import bg.rezzo.model.Club;

@RestController
public class ClubController {
	@Autowired
	private ClubDAO clubDAO;
	
	@GetMapping("/clubs")
	public List<ClubDTO> getAllClubs(@RequestParam(name = "sortBy", required = false) String sortBy,
			@RequestParam(name = "clubId", required = false) Long clubId, HttpServletResponse response) throws SQLException {
		if(this.clubDAO.getAllClubs(sortBy, clubId).size() == 0) {
			response.setStatus(404);
		}
		return this.clubDAO.getAllClubs(sortBy, clubId);
	}
	
	@GetMapping("/clubs/{clubId}")
	public Club getClubDetails(@PathVariable long clubId, HttpServletResponse response) throws SQLException {
		try {
			return clubDAO.getClubById(clubId);
		}catch(NoSuchClubException e) {
			response.setStatus(404);
			return null;
			
		}
	}
	
	@GetMapping("/clubs/events")
	public List<ClubDTO> getAllRestaurantsWithEvents() throws SQLException {
		return this.clubDAO.getAllClubsWithEvents();
	}
	
	@GetMapping("/clubs/offers")
	public List<ClubDTO> getAllRestaurantsWithOffers() throws SQLException {
		return this.clubDAO.getAllClubsWithOffers();
	}
	

}
