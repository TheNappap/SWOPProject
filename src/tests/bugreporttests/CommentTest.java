package tests.bugreporttests;

import model.bugreports.comments.Comment;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CommentTest {

	Comment comment;
	String text = "This is a comment.";
	
	@Before
	public void setUp() throws Exception {
		comment = new Comment(text);
	}

	@Test
	public void constructorTest() {
		assertTrue(text.equals(comment.getText()));
		assertNotNull(comment.getCreationDate());
		assertNotNull(comment.getComments());
	}
	
	@Test
	public void addCommentTest() {
		assertEquals(0, comment.getComments().size());
		comment.addComment("This is a comment on a comment");
		assertEquals(1, comment.getComments().size());
	}

}
