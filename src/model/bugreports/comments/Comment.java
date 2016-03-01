package model.bugreports.comments;

import java.util.ArrayList;
import java.util.Date;

public abstract class Comment implements Commentable {

	private final Date creationDate;
	private final ArrayList<ReplyComment> comments;
	
	private String text;

	public Comment(String text) {
		this.creationDate = new Date();
		this.comments = new ArrayList<ReplyComment>();
		
		setText(text);
	}
	
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