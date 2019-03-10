package bg.rezzo.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import bg.rezzo.dao.UserDAO;
import bg.rezzo.dto.LoginDTO;
import bg.rezzo.dto.RegistrationDTO;
import bg.rezzo.dto.ReservationDTO;
import bg.rezzo.dto.RestaurantInputDTO;
import bg.rezzo.exception.ForbiddenException;
import bg.rezzo.exception.LoginException;
import bg.rezzo.exception.ReservationException;
import bg.rezzo.model.Booking;
import bg.rezzo.model.User;

@RestController
public class UserController {
	
	@Autowired
	private UserDAO userDao;
	
	
	@PostMapping("/login")
	public void login(@RequestBody LoginDTO user, HttpServletRequest request) throws SQLException, LoginException {
		User u = this.userDao.login(user);
		
		if(u == null) {
			throw new LoginException("Invalid login");
		}
		
		HttpSession session = request.getSession();
		session.setAttribute("userId", u.getId());
		session.setAttribute("isAdmin", u.getIsAdmin());
	}
	
	@GetMapping("/profile")
	public User getUserProfile(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		HttpSession session = request.getSession();
		
		if(session.getAttribute("userId") == null) {
			response.setStatus(401);
			return null;
		}
		
		long id = (Long) session.getAttribute("userId");
		return this.userDao.getUser(id);
	}
	
	@PostMapping("/signout")
	public void logout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.invalidate();
	}
	
	@PostMapping("/registration")
	public void registration(@RequestBody RegistrationDTO user) throws SQLException {
		this.userDao.registration(user);
	}
	
	@GetMapping("/bookings")
	public List<Booking> getUserBookings(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		HttpSession session = request.getSession();
		
		if(session.getAttribute("userId") == null) {
			response.setStatus(401);
			return null;
		}
		
		long id = (Long) session.getAttribute("userId");
		return this.userDao.getUserBookings(id);
	}
	
	@PostMapping("/clubs")
	public Long addClub(@RequestBody RestaurantInputDTO restaurantInputDTO, HttpServletRequest request, HttpServletResponse response) throws LoginException, ForbiddenException, SQLException {
		HttpSession session = request.getSession();
		if(session.getAttribute("userId") == null) {
			throw new LoginException("Please login first!");
		}
		if(session.getAttribute("isAdmin").equals(0)) {
			throw new ForbiddenException("You are not allowed to add restaurants!");
		}
		return this.userDao.addRestaurant(restaurantInputDTO);
		
	}
	
	
	@PostMapping("/reservation")
	public void reserve(@RequestBody ReservationDTO reservation, HttpServletRequest request, HttpServletResponse response) throws SQLException, ReservationException, LoginException, IOException {
		HttpSession session = request.getSession();
		
		if(session.getAttribute("userId") == null) {
			response.setStatus(401);
			//throw new LoginException("Please, login first!");
			response.getWriter().println("Please, login first!");
			return;
		}
		
		long id = (Long) session.getAttribute("userId");
		boolean isReservationSuccessful = this.userDao.makeReservation(reservation, id);
		if(!isReservationSuccessful) {
			throw new ReservationException("No enough tables!");
		}		
	}
	
	@PostMapping("/restaurants")
	public Long addRestaurant(@RequestBody RestaurantInputDTO restaurantInputDTO, HttpServletRequest request, HttpServletResponse response) throws LoginException, ForbiddenException, SQLException {
		HttpSession session = request.getSession();
		if(session.getAttribute("userId") == null) {
			throw new LoginException("Please login first!");
		}
		if(session.getAttribute("isAdmin").equals(0)) {
			throw new ForbiddenException("You are not allowed to add restaurants!");
		}
		return this.userDao.addRestaurant(restaurantInputDTO);
		
	}
	
	
	
	
}
