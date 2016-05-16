package tests.bugreporttests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import model.bugreports.BugReport;
import model.bugreports.bugtag.BugTag;
import model.bugreports.comments.Comment;
import tests.BugTrapTest;

import java.util.Date;

public class CommentTest extends BugTrapTest {

	Comment comment;
	String text = "This is a comment.";

	@Before
	public void setUp() {
		super.setUp();
		comment = new Comment((BugReport) wordArtBug, text);
		comment.terminate();
	}

	@Test
	public void constructorTest() {
		assertTrue(text.equals(comment.getText()));
		assertNotNull(comment.getCreationDate());
		assertNotNull(comment.getComments());
		assertEquals(0, comment.getComments().size());
		assertTrue(Math.abs(comment.getCreationDate().getTime() - new Date().getTime()) < 500);
	}
	
	@Test
	public void addCommentTest() {
		assertEquals(0, comment.getComments().size());
		comment.addComment("This is a comment on a comment");
		assertEquals(1, comment.getComments().size());
	}

	@Test
	public void terminateTest() {
		comment.addComment("Comment 1");
		Comment subComment = comment.getComments().get(0);
		comment.addComment("Comment 2");
		Comment subComment2 = comment.getComments().get(1);
		subComment.addComment("Subcomment 1.1");
		Comment subsubComment11 = subComment.getComments().get(0);
		subComment.addComment("Subcomment 1.2");
		Comment subsubComment12 = subComment.getComments().get(1);
		subComment2.addComment("Subcomment 2.1");
		subComment2.addComment("Subcomment 2.2");
		subComment2.addComment("Subcomment 2.3");
		subComment2.addComment("Subcomment 2.4");

		comment.terminate();
		assertEquals(0, comment.getComments().size());
		assertEquals(0, subComment.getComments().size());
		assertEquals(0, subsubComment11.getComments().size());
		assertEquals(0, subsubComment12.getComments().size());
		assertEquals(0, subComment2.getComments().size());
	}
}
