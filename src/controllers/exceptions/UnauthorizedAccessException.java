package controllers.exceptions;

/**
 * Exception thrown when trying to do something you should not.
 * For example: a developer trying to create a project will get
 * this exception, because he is not authorized to create projects.
 */
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
