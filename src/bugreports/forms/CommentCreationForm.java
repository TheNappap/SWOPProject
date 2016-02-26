package bugreports.forms;

public class CommentCreationForm {

	private String initialOrReply;
	private String text;
	
	CommentCreationForm() {
		setInitialOrReply(null);
		setText(null);
	}

	//Getters and Setters
	
	public String getInitialOrReply() {
		return initialOrReply;
	}

	public void setInitialOrReply(String initialOrReply) {
		if (initialOrReply == null) throw new NullPointerException("Given initialOrReply is null.");
		
		this.initialOrReply = initialOrReply;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		if (text == null) throw new NullPointerException("Given text is null");
		
		this.text = text;
	}

}