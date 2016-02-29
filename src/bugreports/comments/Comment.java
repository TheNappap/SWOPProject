package bugreports.comments;

import java.util.ArrayList;
import java.util.Date;

public abstract class Comment implements Commentable {

	private String text;
	private Date creationDate;
	private ArrayList<ReplyComment> comments;

	public Comment(String text) {
		setText(text);
		setCreationDate(new Date());
		setComments(new ArrayList<ReplyComment>());
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
	
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public ArrayList<ReplyComment> getComments() {
		return comments;
	}

	public void setComments(ArrayList<ReplyComment> comments) {
		this.comments = comments;
	}

}