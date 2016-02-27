package bugreports;

import java.util.Date;

public class ReplyComment extends Comment {

	private Comment commentOn;

	ReplyComment(String text, Date creationDate) {
		super(text, creationDate);
	}
	
	public Comment getCommentOn() {
		return commentOn;
	}
	
	void setComment(Comment commentOn) {
		this.commentOn = commentOn;
	}
}