package model.bugreports.comments;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Class that represents a Comment.
 * 
 */
public abstract class Comment implements Commentable { //A Comment can be commented on.

	//All immutable.
	private final Date creationDate;				//Creation Date of the Comment.
	private final List<ReplyComment> comments;		//Comments to this Comment.
	private final String text;						//Text.

	/**  
	 * Constructor.  
	 * @param text The text of this Comment.  z
	 */  
	public Comment(String text) {
		this.creationDate = new Date();
		this.comments 	  = new ArrayList<ReplyComment>();
		this.text 		  = text;
	}

	@Override
	public void addComment(String commentText) {
		comments.add(new ReplyComment(commentText, this));
	}
	
	//Getters and Setters

	public String getText() {
		return text;
	}
	
	public Date getCreationDate() {
		return creationDate;
	}

	public List<ReplyComment> getComments() {
		List<ReplyComment> returnComments = new ArrayList<ReplyComment>(comments.size());
		
		for (ReplyComment replyComment : comments) returnComments.add(replyComment);
		
		return returnComments;
	}

}