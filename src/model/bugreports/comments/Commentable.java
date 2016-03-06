package model.bugreports.comments;

/**
 * Class tagging objects that can be commented on.
 */
public interface Commentable { 
	
	/**  
	 * Add a Comment to this Commentable with given text.  
	 * @param text The text of the comment.  
	 */  
	public void addComment(String text);
}
