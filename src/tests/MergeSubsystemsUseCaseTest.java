package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import controllers.exceptions.UnauthorizedAccessException;
import model.projects.AchievedMilestone;
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
        ISubsystem wordcel = office.getSubsystems().get(office.getSubsystems().size()-1);

        assertEquals("Wordcel", wordcel.getName());
        assertEquals("Words that sound excellent.", wordcel.getDescription());
        // Check subsystems are all present in the merged system
        assertTrue(wordcel.getSubsystems().contains(clippy));
        assertTrue(wordcel.getSubsystems().contains(wordArt));
        assertTrue(wordcel.getSubsystems().contains(comicSans));
        assertTrue(wordcel.getSubsystems().contains(excelTable));
        assertFalse(office.getSubsystems().contains(word));
        assertFalse(office.getSubsystems().contains(excel));
        assertEquals(wordcel, wordBug.getSubsystem());
        assertEquals(wordcel, excelBug.getSubsystem());
        // Check if Milestone is the lowest of both
        AchievedMilestone milestone = word.getAchievedMilestone();
        if (excel.getAchievedMilestone().compareTo(milestone) == -1)
            milestone = excel.getAchievedMilestone();
        assertEquals(milestone, wordcel.getAchievedMilestone());
    }

    @Test
    public void mergeParentChild() throws UnauthorizedAccessException {
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
        form.setSubsystem2(comicSans);
        // Step 8. The system asks for a name and description for the new subsystem
        // Step 9. The admin enters name and description.
        form.setName("ComicWord");
        form.setDescription("Comic Sans Word");
        // Step 10. The system creates a new subsystem with the lowest milestone of the
        //          two original subsystems. The bug reports and subsystems that are part
        //          of the original two subsystems are migrated to the new subsystem. The
        //          two original subsystems are removed.

        projectController.mergeSubsystem(form);
        ISubsystem comicword = office.getSubsystems().get(office.getSubsystems().size()-1);

        assertEquals("ComicWord", comicword.getName());
        assertEquals("Comic Sans Word", comicword.getDescription());
        // Check subsystems are all present in the merged system
        assertTrue(comicword.getSubsystems().contains(clippy));
        assertTrue(comicword.getSubsystems().contains(wordArt));
        assertFalse(comicword.getSubsystems().contains(comicSans));
        assertFalse(comicword.getSubsystems().contains(powerpoint));
        assertFalse(office.getSubsystems().contains(word));
        assertTrue(office.getSubsystems().contains(excel));
        assertTrue(office.getSubsystems().contains(powerpoint));
        assertEquals(comicword, wordBug.getSubsystem());
        assertNotEquals(comicword, excelBug.getSubsystem());
        // Check if Milestone is the lowest of both
        AchievedMilestone milestone = word.getAchievedMilestone();
        if (comicSans.getAchievedMilestone().compareTo(milestone) == -1)
            milestone = comicSans.getAchievedMilestone();
        assertEquals(milestone, comicword.getAchievedMilestone());
    }
}
