package bugreports;

import java.util.Date;

import bugreports.forms.CommentCreationForm;

public abstract class Comment {

	private String text;
	private Date creationDate;

	/**
	 * 
	 * @param form
	 */
	public void createComment(CommentCreationForm form) {
		// TODO - implement Comment.createComment
		throw new UnsupportedOperationException();
	}

}