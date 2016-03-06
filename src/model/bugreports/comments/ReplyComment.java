package model.bugreports.comments;

public class ReplyComment extends Comment {

	private final Comment commentOn; //On which Comment is this ReplyComment on?

	/**
	 * Constructor.
	 * @param text Text of this ReplyComment.
	 * @param commentOn On which Comment is this ReplyComment on?
	 */
	public ReplyComment(String text, Comment commentOn) {
		super(text);

		this.commentOn = commentOn;
	}
	
	//Getters and Setters
	
	public Comment getCommentOn() {
		return commentOn;
	}

}