package bg.rezzo.exception;

public class NoSuchClubException extends Exception {

	public NoSuchClubException() {
		super();
	}

	public NoSuchClubException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public NoSuchClubException(String message, Throwable cause) {
		super(message, cause);
	}

	public NoSuchClubException(String message) {
		super(message);
	}

	public NoSuchClubException(Throwable cause) {
		super(cause);
	}

	private static final long serialVersionUID = 1172738537819693983L;

}
