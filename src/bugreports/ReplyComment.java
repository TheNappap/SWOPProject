package bugreports;

import java.util.Date;

public class ReplyComment extends Comment {

	private Comment commentOn;

	ReplyComment(String text, Comment commentOn, Date creationDate) {
		super(text, creationDate);
		setCommentOn(commentOn);
	}
	
	public Comment getCommentOn() {
		return commentOn;
	}
	
	void setCommentOn(Comment commentOn) {
		this.commentOn = commentOn;
	}
}