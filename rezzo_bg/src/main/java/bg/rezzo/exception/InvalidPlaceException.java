package bg.rezzo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class InvalidPlaceException extends Exception {

	public InvalidPlaceException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public InvalidPlaceException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public InvalidPlaceException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public InvalidPlaceException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public InvalidPlaceException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	private static final long serialVersionUID = 2971549414844160059L;

}
