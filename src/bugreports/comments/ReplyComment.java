package bugreports.comments;

public class ReplyComment extends Comment {

	private Comment commentOn;

	public ReplyComment(String text, Comment commentOn) {
		super(text);
		setCommentOn(commentOn);
	}
	
	//Getters and Setters
	
	public Comment getCommentOn() {
		return commentOn;
	}
	
	public void setCommentOn(Comment commentOn) {
		this.commentOn = commentOn;
	}
}