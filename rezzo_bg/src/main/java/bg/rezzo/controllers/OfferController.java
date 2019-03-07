package bg.rezzo.controllers;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import bg.rezzo.dao.OfferDAO;
import bg.rezzo.model.Offer;


@RestController
public class OfferController {
	
	@Autowired
	private OfferDAO offerDao;
	
	
	@GetMapping("/offers")
	public List<Offer> getAllEvents() throws SQLException {
		return this.offerDao.getAllOffers();
	}
	
}
