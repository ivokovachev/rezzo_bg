package bg.rezzo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import bg.rezzo.dto.ClubDTO;
import bg.rezzo.exception.NoSuchClubException;
import bg.rezzo.helper.Helper;
import bg.rezzo.model.Address;
import bg.rezzo.model.Club;

@Component
public class ClubDAO {
	private JdbcTemplate jdbcTemplate;
	public ClubDAO() {}
	
	public List<ClubDTO> getAllClubs(String sortBy, Long clubId) throws SQLException{
		List<ClubDTO> clubs = new LinkedList<ClubDTO>();
		Connection con = jdbcTemplate.getDataSource().getConnection();
		Statement statement = con.createStatement();
		ResultSet resultSet = statement.executeQuery(Helper.GET_DETAILS_FOR_ALL_CLUBS);
		
//		while(resultSet.next()) {
//			if(clubId == null || resultSet.getLong(4) == clubId) {
//				clubs.add(new ClubDTO(resultSet.getLong(1), resultSet.getString(2), resultSet.getString(3), resultSet.getLong(4), resultSet.getDouble(5)));
//			}
//		}
//			return clubs
//					.stream()
//					.sorted((c1, c2) -> {
//						switch(sortBy) {
//							case "clubName": return c1.getClubName().compareTo(c2.getClubName());
//							case "rating": return (int) ((c2.getRating() - c1.getRating()));
//							default: return 1;
//						}
//					})
//					.collect(Collectors.toList());
		while(resultSet.next()) {
			if(clubId == null || resultSet.getLong(4) == clubId) {
					clubs.add(new ClubDTO(resultSet.getLong(1), resultSet.getString(2), resultSet.getString(3), resultSet.getLong(4), resultSet.getDouble(5)));
			}
		}
		if(sortBy != null) {
			switch(sortBy) {
			case "clubName": clubs.sort((r1, r2) -> r1.getClubName().compareTo(r2.getClubName()));
			case "raitng": clubs.sort((r1, r2) -> (int)(r2.getRating() - r1.getRating()));
			default: return clubs;
			}
		}
		return clubs;
	}
	
	public Club getClubById(long id) throws SQLException, NoSuchClubException {
		Connection con = jdbcTemplate.getDataSource().getConnection();
		PreparedStatement statement = con.prepareStatement(Helper.GET_CLUB_DETAILS);
		statement.setLong(1, id);
		Club club = null;
		ResultSet resultSet = statement.executeQuery();
		while(resultSet.next()) {
			club = new Club(resultSet.getLong(1), resultSet.getString(2), resultSet.getTime(3).toString(),
					resultSet.getTime(4).toString(), resultSet.getDouble(5), resultSet.getString(6),
					resultSet.getInt(7), 
					new Address(0, resultSet.getString(8),
							resultSet.getString(9), resultSet.getString(10)), resultSet.getString(11));
		}
		if(club == null) {
			throw new NoSuchClubException("No such club!");
		}
		return club;
	}
	
	@Autowired
	private void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

}
