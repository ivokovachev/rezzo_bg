package bg.rezzo.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import bg.rezzo.helper.Helper;
import bg.rezzo.model.Offer;

@Component
public class OfferDAO {
	
private JdbcTemplate jdbcTemplate;
	
	public OfferDAO() {}
	
	
	public List<Offer> getAllOffers() throws SQLException {
		List<Offer> offers = new LinkedList<Offer>();
		Connection con = this.jdbcTemplate.getDataSource().getConnection();
		Statement statement = con.createStatement();
		ResultSet rs = statement.executeQuery(Helper.GET_ALL_OFFERS_QUERY);
		while(rs.next()) {
			offers.add(new Offer(rs.getLong(1), rs.getString(2), rs.getString(5),
					rs.getString(3), rs.getString(4), rs.getInt(6)));
		}
		
		return offers;
	}
	
	public List<Offer> getAllOffersSortedByPrice(String sortBy) throws SQLException {
		List<Offer> offers = new LinkedList<Offer>();
		Connection con = this.jdbcTemplate.getDataSource().getConnection();
		Statement statement = con.createStatement();
		ResultSet rs = statement.executeQuery(Helper.GET_ALL_OFFERS_QUERY);
		while(rs.next()) {
			offers.add(new Offer(rs.getLong(1), rs.getString(2), rs.getString(5),
					rs.getString(3), rs.getString(4), rs.getInt(6)));
		}
		
		if(sortBy != null && sortBy.equals("price")) {
			offers = offers
			.stream()
			.sorted((offer1, offer2) -> (offer1.getPrice() - offer2.getPrice()))
			.collect(Collectors.toList());	
		}
		
		return offers;
	}
	
	@Autowired
	private void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
}
