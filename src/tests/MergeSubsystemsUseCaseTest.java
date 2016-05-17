package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import controllers.exceptions.UnauthorizedAccessException;
import model.projects.ISubsystem;
import model.projects.forms.MergeSubsystemForm;

public class MergeSubsystemsUseCaseTest extends BugTrapTest {
    @Test
    public void mergeSiblings() throws UnauthorizedAccessException {
        userController.loginAs(admin);

        // Step 1. The administrator indicates he wants to merge two subsystems
        MergeSubsystemForm form = projectController.getMergeSubsystemForm();
        // Step 2. The system shows a list of project
        projectController.getProjectList();
        // Step 3. The administrator selects a project
        // Step 4. The system shows a list of all subsystems
        office.getAllDirectOrIndirectSubsystems();
        // Step 5. The admin selects the first subsystem.
        form.setSubsystem1(word);
        // Step 6. The system shows a list of compatible systems to merge the selected system with
        word.mergeableWith();
        // Step 7. The admin selects a second subsystem
        form.setSubsystem2(excel);
        // Step 8. The system asks for a name and description for the new subsystem
        // Step 9. The admin enters name and description.
        form.setName("Wordcel");
        form.setDescription("Words that sound excellent.");
        // Step 10. The system creates a new subsystem with the lowest milestone of the
        //          two original subsystems. The bug reports and subsystems that are part
        //          of the original two subsystems are migrated to the new subsystem. The
        //          two original subsystems are removed.

        projectController.mergeSubsystem(form);
        ISubsystem wordpoint = office.getSubsystems().get(office.getSubsystems().size()-1);

        assertEquals("Wordcel", wordpoint.getName());
        assertEquals("Words that sound excellent.", wordpoint.getDescription());
        assertTrue(wordpoint.getSubsystems().contains(clippy));
        assertTrue(wordpoint.getSubsystems().contains(wordArt));
        assertTrue(wordpoint.getSubsystems().contains(comicSans));
        assertTrue(wordpoint.getSubsystems().contains(excelTable));
        assertFalse(office.getSubsystems().contains(word));
        assertFalse(office.getSubsystems().contains(excel));


    }
}
