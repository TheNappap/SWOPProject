package bugreports;

import java.util.ArrayList;
import java.util.Date;

import bugreports.forms.CommentCreationForm;

public abstract class Comment {

	private String text;
	private Date creationDate;
	private ArrayList<Comment> comments;

	Comment(String text) {
		setText(text);
		setCreationDate(new Date());
		setComments(new ArrayList<Comment>());
	}
	
	/**
	 * Creates and adds a ReplyComment to this Comment.
	 * @param form The CommentCreationForm that contains information to create a new Comment.
	 */
	public void createComment(CommentCreationForm form) {
		getComments().add(new ReplyComment(form.getText(), this));
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