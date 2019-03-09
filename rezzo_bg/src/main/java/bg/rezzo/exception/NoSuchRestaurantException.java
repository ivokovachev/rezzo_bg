package bg.rezzo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoSuchRestaurantException extends Exception {

	public NoSuchRestaurantException() {
		super();
	}

	public NoSuchRestaurantException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public NoSuchRestaurantException(String message, Throwable cause) {
		super(message, cause);
	}

	public NoSuchRestaurantException(String message) {
		super(message);
	}

	public NoSuchRestaurantException(Throwable cause) {
		super(cause);
	}

	private static final long serialVersionUID = -148746510192794573L;

}
