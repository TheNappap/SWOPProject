package tests.projecttests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import controllers.exceptions.UnauthorizedAccessException;
import model.bugreports.BugReport;
import model.bugreports.bugtag.BugTag;
import model.notifications.Mailbox;
import model.notifications.observers.BugReportChangeObserver;
import model.notifications.observers.Observer;
import model.projects.Project;
import model.projects.Subsystem;
import model.projects.System;
import model.projects.Version;
import tests.BugTrapTest;

public class SystemTests extends BugTrapTest {

    @Test
    public void testAddSubsystem() {
        Subsystem s = new Subsystem(bugTrap, "sub", "descr", (Subsystem)word, new ArrayList<>(), (Project)office, null);

        Assert.assertEquals(s.getParent(), word);
        Assert.assertEquals(s.getSubsystems().size(), 0);
        Assert.assertFalse(office.getSubsystems().contains(s));
        assertTrue(office.getAllDirectOrIndirectSubsystems().contains(s));
        assertTrue(word.getSubsystems().contains(s));
    }

    @Test
    public void testEquals() {
        assertTrue(powerpoint.equals(new Subsystem(bugTrap, "PowerPoint", "Powerfully pointless", (System)office, null, (Project)office, null)));
        // Compare "identical" Projects but with different subs
        Project p2 = new Project(bugTrap, "Office", "This project is huge. Lots of subsystems", new ArrayList<Subsystem>(), Version.firstVersion(), new Date(1302), new Date(1302), 12345, null, null);
        assertFalse(office.equals(p2));

        Project projA = new Project(null, "n", "d", new ArrayList<Subsystem>(), Version.firstVersion(), null, null, 12345, null, null);
        Project projB = new Project(null, "n", "d", new ArrayList<Subsystem>(), Version.firstVersion(), null, null, 12345, null, null);
        Subsystem subA = new Subsystem(null, "", "", projA, new ArrayList<Subsystem>(), projA, null);
        Subsystem subB = new Subsystem(null, "", "", projB, new ArrayList<Subsystem>(), projB, null);
        assertTrue(projA.equals(projB));
        Assert.assertEquals(projA, projB);
        assertTrue(subA.equals(subB));
        Assert.assertEquals(subA, subB);
    }

    @Test
    public void testAttachDetach() throws UnauthorizedAccessException {
        Mailbox box = bugTrap.getNotificationManager().getMailboxForUser(admin);
        int notificationCount = box.getNotifications().size();
        Observer o = new BugReportChangeObserver(box, office);
        office.attach(o);

        bugTrap.getUserManager().loginAs(lead);
        ((BugReport)wordArtBug).updateBugTag(BugTag.CLOSED);
        // Attached, so should drop a new notification in the mailbox
        assertEquals(notificationCount + 1, box.getNotifications().size());

        office.detach(o);
        ((BugReport)clippyBug).updateBugTag(BugTag.CLOSED);
        // Detached, so notification count should stay the same
        assertEquals(notificationCount + 1, box.getNotifications().size());
    }

    @Test
    public void testGetBugReports() {
        assertTrue(office.getAllBugReports().contains(wordBug));
        assertTrue(office.getAllBugReports().contains(wordArtBug));
        assertTrue(office.getAllBugReports().contains(clippyBug));

        assertTrue(word.getAllBugReports().contains(wordBug));
        assertTrue(word.getAllBugReports().contains(wordArtBug));
        assertTrue(word.getAllBugReports().contains(clippyBug));
        assertFalse(excel.getAllBugReports().contains(wordBug));
        assertFalse(excel.getAllBugReports().contains(wordArtBug));
        assertFalse(excel.getAllBugReports().contains(clippyBug));
        assertFalse(powerpoint.getAllBugReports().contains(wordBug));
        assertFalse(powerpoint.getAllBugReports().contains(wordArtBug));
        assertFalse(powerpoint.getAllBugReports().contains(clippyBug));

        assertFalse(wordArt.getAllBugReports().contains(wordBug));
        assertTrue(wordArt.getAllBugReports().contains(wordArtBug));
        assertFalse(wordArt.getAllBugReports().contains(clippyBug));
        assertFalse(comicSans.getAllBugReports().contains(wordBug));
        assertFalse(comicSans.getAllBugReports().contains(wordArtBug));
        assertFalse(comicSans.getAllBugReports().contains(clippyBug));
        assertFalse(clippy.getAllBugReports().contains(wordBug));
        assertFalse(clippy.getAllBugReports().contains(wordArtBug));
        assertTrue(clippy.getAllBugReports().contains(clippyBug));
    }
}
