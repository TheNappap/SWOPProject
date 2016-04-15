package model.bugreports.comments;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Class that represents a Comment.
 * 
 */
public class Comment implements Commentable { //A Comment can be commented on.

	//All immutable.
	private final Date creationDate;				//Creation Date of the Comment.
	private final List<Comment> comments;			//Comments to this Comment.
	private final String text;						//Text.

	/**  
	 * Constructor.  
	 * @param text The text of this Comment.
	 */  
	public Comment(String text) {
		this.creationDate = new Date();
		this.comments 	  = new ArrayList<Comment>();
		this.text 		  = text;
	}
	
	@Override
	public void addComment(String commentText) {
		comments.add(new Comment(commentText));
	}

	public String getText() {
		return text;
	}
	
	public Date getCreationDate() {
		return creationDate;
	}

	public List<Comment> getComments() {
		return comments;
	}
}