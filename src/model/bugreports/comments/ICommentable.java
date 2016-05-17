package model.bugreports.comments;

import java.util.List;

/**
 * Class tagging objects that can be commented on.
 */
public interface ICommentable{

	/**
	 * Return a List containing the Comments on this Commentable.
	 * @return A List containing the Comments on this Commentable.
	 */
	public List<Comment> getComments();
}
