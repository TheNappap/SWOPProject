package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;

import controllers.exceptions.UnauthorizedAccessException;
import model.projects.ISubsystem;
import model.projects.forms.SplitSubsystemForm;

public class SplitSubsystemUseCaseTest extends BugTrapTest {
    @Test
    public void split() throws UnauthorizedAccessException {
        userController.loginAs(admin);

        // Step 1. The admin indicates he wants to split a subsystem or project
        SplitSubsystemForm form = projectController.getSplitSubsystemForm();
        // Step 2. The system shows a list of projects
        projectController.getProjectList();

        // Step 3. The administrator selects a project
        // Step 4. The system shows a list of all subsystems
        office.getAllDirectOrIndirectSubsystems();

        // Step 5. The administrator selects a subsystem
        form.setSubsystem(word);

        // Step 6. The system asks for a new name and description for both new subsystems
        // Step 7. The administrator enters both names and descriptions
        form.setName1("Editor");
        form.setName2("Extras");
        form.setDescription1("The actual word processor.");
        form.setDescription2("The extra gimmick to make the software slow and unstable.");

        // Step 8. For each bug report and subsystem that is part of the original subsystem, the system asks to which new subsystem to migrate it to.
        // Step 9. The administrator answers for each bug report and subsystem.
        form.setBugReports1(Arrays.asList(wordBug));
        form.setSubsystems1(Arrays.asList(comicSans));

        // Step 10. The system creates two new subsystems with the same milestones as the original system. The original system is removed.
        projectController.splitSubsystem(form);
        ISubsystem editor = office.getSubsystems().get(office.getSubsystems().size() - 2);
        ISubsystem extras = office.getSubsystems().get(office.getSubsystems().size() - 1);

        assertFalse(office.getSubsystems().contains(word));
        assertEquals(0, word.getSubsystems().size());
        assertEquals("Editor", editor.getName());
        assertEquals("Extras", extras.getName());
        assertEquals("The actual word processor.", editor.getDescription());
        assertEquals("The extra gimmick to make the software slow and unstable.", extras.getDescription());
        assertEquals(1, editor.getBugReports().size());
        assertEquals(0, extras.getBugReports().size());
        assertEquals(2, extras.getAllBugReports().size());
        assertTrue(editor.getBugReports().contains(wordBug));
        assertFalse(extras.getAllBugReports().contains(wordBug));
        assertFalse(editor.getAllBugReports().contains(clippyBug));
        assertTrue(extras.getAllBugReports().contains(clippyBug));
        assertFalse(editor.getAllBugReports().contains(wordArtBug));
        assertTrue(extras.getAllBugReports().contains(wordArtBug));
        assertEquals(editor, wordBug.getSubsystem());
        assertEquals(word.getAchievedMilestone(), extras.getAchievedMilestone());
        assertEquals(extras.getAchievedMilestone(), editor.getAchievedMilestone());
    }

    @Test (expected = UnauthorizedAccessException.class)
    public void splitNotAllowed() throws UnauthorizedAccessException {
        userController.loginAs(issuer);
        projectController.getSplitSubsystemForm();
    }

    @Test (expected = UnauthorizedAccessException.class)
    public void splitNotAllowed2() throws UnauthorizedAccessException {
        userController.loginAs(prog);
        projectController.getSplitSubsystemForm();
    }

    @Test (expected = UnauthorizedAccessException.class)
    public void splitNotAllowed3() throws UnauthorizedAccessException {
        userController.loginAs(tester);
        projectController.getSplitSubsystemForm();
    }

    @Test (expected = UnauthorizedAccessException.class)
    public void splitNotAllowed4() throws UnauthorizedAccessException {
        userController.loginAs(lead);
        projectController.getSplitSubsystemForm();
    }
}
