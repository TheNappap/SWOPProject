package bugreports;

import java.util.Date;

import bugreports.forms.CommentCreationForm;

public abstract class Comment {

	private String text;
	private Date creationDate;

	Comment(String text, Date creationDate) {
		setText(text);
		setCreationDate(creationDate);
	}
	
	/**
	 * 
	 * @param form
	 */
	public void createComment(CommentCreationForm form) {
		// TODO - implement Comment.createComment
		throw new UnsupportedOperationException();
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

}