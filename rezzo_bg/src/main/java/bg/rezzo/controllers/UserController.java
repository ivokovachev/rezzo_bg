package bg.rezzo.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import bg.rezzo.dao.UserDAO;
import bg.rezzo.dto.BookingDTO;
import bg.rezzo.dto.LoginDTO;
import bg.rezzo.dto.RegistrationDTO;
import bg.rezzo.dto.ReservationDTO;
import bg.rezzo.exception.LoginException;
import bg.rezzo.exception.ReservationException;
import bg.rezzo.model.Booking;
import bg.rezzo.model.User;

@RestController
public class UserController {
	
	@Autowired
	private UserDAO userDao;
	
	
	@PostMapping("/login")
	public void login(@RequestBody LoginDTO user, HttpServletRequest request) throws SQLException {
		User u = this.userDao.login(user);
		HttpSession session = request.getSession();
		session.setAttribute("userId", u.getId());
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
	
	
}
