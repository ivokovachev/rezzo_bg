package bg.rezzo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class ForbiddenException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8358356235234468548L;

	public ForbiddenException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ForbiddenException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public ForbiddenException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public ForbiddenException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public ForbiddenException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}


}
