package bugreports;

public class ReplyComment extends Comment {

	private Comment commentOn;

	ReplyComment(String text, Comment commentOn) {
		super(text);
		setCommentOn(commentOn);
	}
	
	//Getters and Setters
	
	public Comment getCommentOn() {
		return commentOn;
	}
	
	void setCommentOn(Comment commentOn) {
		this.commentOn = commentOn;
	}
}