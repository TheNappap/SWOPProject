package controllers.exceptions;

public class UnauthorizedAccessException extends Exception {
	
	private String message;
	
	public UnauthorizedAccessException() {
		this.message = "";
	}
	
	public UnauthorizedAccessException(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}

	private static final long serialVersionUID = 1L;

}
