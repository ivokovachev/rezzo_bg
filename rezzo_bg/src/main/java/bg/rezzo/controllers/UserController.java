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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import bg.rezzo.dao.UserDAO;
import bg.rezzo.dto.EditProfileDTO;
import bg.rezzo.dto.EventInputDTO;
import bg.rezzo.dto.ChangePasswordDTO;
import bg.rezzo.dto.ClubInputDTO;
import bg.rezzo.dto.LoginDTO;
import bg.rezzo.dto.OfferInputDTO;
import bg.rezzo.dto.RegistrationDTO;
import bg.rezzo.dto.ReservationDTO;
import bg.rezzo.dto.RestaurantInputDTO;
import bg.rezzo.exception.ForbiddenException;
import bg.rezzo.exception.InvalidPlaceException;
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
	
	@PutMapping("/profile")
	public User editUserProfile(@RequestBody EditProfileDTO user, HttpServletRequest request, HttpServletResponse response) throws SQLException {
		HttpSession session = request.getSession();
		
		if(session.getAttribute("userId") == null) {
			response.setStatus(401);
			return null;
		}
		
		long id = (Long) session.getAttribute("userId");
		return this.userDao.editProfile(id, user);
	}
	
	@PutMapping("/profile/password")
	public void changePassword(@RequestBody ChangePasswordDTO changePassword, HttpServletRequest request, HttpServletResponse response) throws SQLException {
		HttpSession session = request.getSession();
		
		if(session.getAttribute("userId") == null) {
			response.setStatus(401);
			return;
		}
		long id = (Long) session.getAttribute("userId");
		this.userDao.changePassword(id, changePassword);
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
	
	
	@PostMapping("/offers")
	public Long addOffer(@RequestBody OfferInputDTO offerInputDTO, HttpServletRequest request, HttpServletResponse response) throws LoginException, ForbiddenException, SQLException, InvalidPlaceException {
		isAdminlogged(request, response);
		return this.userDao.addOffer(offerInputDTO);
		
	}
	
	@PostMapping("/events")
	public Long addEvent(@RequestBody EventInputDTO eventInputDTO, HttpServletRequest request, HttpServletResponse response) throws LoginException, ForbiddenException, SQLException, InvalidPlaceException {
		isAdminlogged(request, response);
		return this.userDao.addEvent(eventInputDTO);
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
		isAdminlogged(request, response);
		return this.userDao.addRestaurant(restaurantInputDTO);
		
	}
	
	@PostMapping("/clubs")
	public Long addClub(@RequestBody ClubInputDTO clubInputDTO, HttpServletRequest request, HttpServletResponse response) throws LoginException, ForbiddenException, SQLException {
			isAdminlogged(request, response);
		return this.userDao.addClub(clubInputDTO);
	}
	
	private boolean isAdminlogged(HttpServletRequest request, HttpServletResponse response) throws LoginException, ForbiddenException {
		HttpSession session = request.getSession();
		if(session.getAttribute("userId") == null) {
			throw new LoginException("Please login first!");
		}
		if(session.getAttribute("isAdmin").equals(0)) {
			throw new ForbiddenException("You are not allowed to add restaurants!");
		}
		return true;
	}
}
