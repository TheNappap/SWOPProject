package model.bugreports.comments;

import java.util.ArrayList;
import java.util.Date;

public abstract class Comment implements Commentable {

	private final Date creationDate;	//Comments to this Comment. 
	private final ArrayList<ReplyComment> comments;	//Comments to this Comment. 
	private String text; //Text.

	/**  
	 * Constructor.  
	 * @param text The text of this Comment.  
	 */  
	public Comment(String text) {
		this.creationDate = new Date();
		this.comments = new ArrayList<ReplyComment>();
		this.text = text;
	}
	
	@Override
	public void addComment(String commentText) {
		getComments().add(new ReplyComment(commentText, this));
	}
	
	//Getters and Setters

	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public Date getCreationDate() {
		return creationDate;
	}

	public ArrayList<ReplyComment> getComments() {
		return comments;
	}

}