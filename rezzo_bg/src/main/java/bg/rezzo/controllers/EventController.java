package bg.rezzo.controllers;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import bg.rezzo.dao.EventDAO;
import bg.rezzo.model.Event;


@RestController
public class EventController {
	
	@Autowired
	private EventDAO eventDao;
	
	
	@GetMapping("/events")
	public List<Event> getAllEvents(@RequestParam(name = "sortBy", required = false) String sortBy) throws SQLException {
		return this.eventDao.getAllEventsSortedByDate(sortBy);
	}
	
}
