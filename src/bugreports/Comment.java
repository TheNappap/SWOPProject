package bugreports;

import java.util.ArrayList;
import java.util.Date;

import bugreports.forms.CommentCreationForm;

public abstract class Comment {

	private String text;
	private Date creationDate;
	private ArrayList<Comment> comments;

	Comment(String text, Date creationDate) {
		setText(text);
		setCreationDate(creationDate);
		setComments(new ArrayList<Comment>());
	}
	
	/**
	 * 
	 * @param form
	 */
	public void createComment(CommentCreationForm form) {
		getComments().add(new ReplyComment(form.getText(), this, new Date()));
	}
	
	//Getters and Setters

	public String getText() {
		return text;
	}
	
	void setText(String text) {
		this.text = text;
	}
	
	public Date getCreationDate() {
		return creationDate;
	}
	
	void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public ArrayList<Comment> getComments() {
		return comments;
	}

	void setComments(ArrayList<Comment> comments) {
		this.comments = comments;
	}

}