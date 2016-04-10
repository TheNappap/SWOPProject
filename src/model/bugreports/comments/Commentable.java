package model.bugreports.comments;

import java.util.List;

import model.notifications.Observable;

/**
 * Class tagging objects that can be commented on.
 */
public interface Commentable {
	
	/**  
	 * Add a Comment to this Commentable with given text.  
	 * @param text The text of the comment.  
	 */  
	public void addComment(String text);
	
	/**
	 * Return a List containing the Comments on this Commentable.
	 * @return A List containing the Comments on this Commentable.
	 */
	public List<Comment> getComments();
}
