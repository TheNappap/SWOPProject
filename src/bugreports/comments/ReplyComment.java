package bugreports.comments;

public class ReplyComment extends Comment {

	private final Comment commentOn;

	public ReplyComment(String text, Comment commentOn) {
		super(text);

		this.commentOn = commentOn;
	}
	
	//Getters and Setters
	
	public Comment getCommentOn() {
		return commentOn;
	}

}