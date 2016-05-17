package tests.projecttests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import controllers.exceptions.UnauthorizedAccessException;
import model.projects.IProject;
import model.projects.ISubsystem;
import model.projects.Project;
import model.projects.Subsystem;
import model.projects.Version;
import model.users.IUser;
import model.users.UserManager;
import tests.BugTrapTest;

public class ProjectManagerTests extends BugTrapTest {

    @SuppressWarnings("deprecation")
    @Test
    public void testCreateProject() throws UnauthorizedAccessException {
    	
    	bugTrap.getProjectManager().createProject("New Project", "Description", new Date(2015, 8, 18), new Date(2015, 9, 1), 123, null, new Version(1, 0, 0));
        Project project = (Project) bugTrap.getProjectManager().getProjects().get(bugTrap.getProjectManager().getProjects().size() - 1);

        //System variables.
        assertEquals("New Project", 		project.getName());
        assertEquals("Description", 		project.getDescription());
        assertEquals(null,					project.getParent());
        assertEquals(0,						project.getSubsystems().size());
        assertEquals("M0", 					project.getAchievedMilestone().toString());
        
        //Project variables.
        assertEquals(new Version(1, 0, 0), 	project.getVersion());
        assertEquals(new Date(2015, 8, 18), project.getCreationDate());
        assertEquals(new Date(2015, 9, 1), 	project.getStartDate());
        assertEquals(123, 					project.getBudgetEstimate(), 0.0000001);
  
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testCreateFork() {
    	IProject fork = null;
        		
        //cases where fork's Version isn't incremented.
        try {
            bugTrap.getProjectManager().createFork(office, 123592929, new Version(0, 9, 0), new Date(2016, 1 , 1));
            fail("Fork version should be higher than original Version");
        } catch(IllegalArgumentException e) { }
        try {
            bugTrap.getProjectManager().createFork(office, 123592929, new Version(1, 0, 0), new Date(2016, 1 , 1));
            fail("Fork version should be higher than original Version");
        } catch(IllegalArgumentException e) { }

        //Version is actually incremented.
        bugTrap.getProjectManager().createFork(office, 123592929, new Version(2, 1, 1), new Date(2016, 1, 1));
        fork = bugTrap.getProjectManager().getProjects().get(1);

        Assert.assertTrue(office.equals(fork));
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testDeleteProject() throws UnauthorizedAccessException {
        int projects = bugTrap.getProjectManager().getProjects().size();

    	bugTrap.getProjectManager().createProject("n", "d", new Date(2015, 8, 18), new Date(2015, 9, 1), 123, null, new Version(1, 0, 0));
        IProject project = bugTrap.getProjectManager().getProjects().get(projects);
        Assert.assertEquals(bugTrap.getProjectManager().getProjects().size(), projects + 1);

    	bugTrap.getProjectManager().deleteProject(project);
        Assert.assertEquals(bugTrap.getProjectManager().getProjects().size(), projects);
        Assert.assertFalse(bugTrap.getProjectManager().getProjects().contains(project));
    }


    @SuppressWarnings("deprecation")
    @Test
    public void testGetProjectsForLeadDeveloper() {
        UserManager um = bugTrap.getUserManager();
        um.createDeveloper("", "", "", "D1");
        IUser d1 = um.getDevelopers().get(0);
        um.createDeveloper("", "", "", "D2");
        IUser d2 = um.getDevelopers().get(1);
        um.createDeveloper("", "", "", "D3");
        IUser d3 = um.getDevelopers().get(2);

        int projects = bugTrap.getProjectManager().getProjects().size();

        IProject p1 = null;
        IProject p2 = null;
        IProject p3 = null;

        bugTrap.getProjectManager().createProject("n", "d", new Date(2015, 8, 18), new Date(2015, 9, 1), 123, d1, new Version(1, 0, 0));
        bugTrap.getProjectManager().createProject("n", "d", new Date(2015, 8, 18), new Date(2015, 9, 1), 123, d2, new Version(1, 0, 0));
        bugTrap.getProjectManager().createProject("n", "d", new Date(2015, 8, 18), new Date(2015, 9, 1), 123, d3, new Version(1, 0, 0));
        p1 = bugTrap.getProjectManager().getProjects().get(projects);
        p2 = bugTrap.getProjectManager().getProjects().get(projects+1);
        p3 = bugTrap.getProjectManager().getProjects().get(projects+2);

        bugTrap.getUserManager().loginAs(d1);
        Assert.assertTrue(bugTrap.getProjectManager().getProjectsForSignedInLeadDeveloper().contains(p1));
        Assert.assertFalse(bugTrap.getProjectManager().getProjectsForSignedInLeadDeveloper().contains(p2));
        Assert.assertFalse(bugTrap.getProjectManager().getProjectsForSignedInLeadDeveloper().contains(p3));

        bugTrap.getUserManager().loginAs(d2);
        Assert.assertTrue(bugTrap.getProjectManager().getProjectsForSignedInLeadDeveloper().contains(p2));
        Assert.assertFalse(bugTrap.getProjectManager().getProjectsForSignedInLeadDeveloper().contains(p3));
        Assert.assertFalse(bugTrap.getProjectManager().getProjectsForSignedInLeadDeveloper().contains(p1));

        bugTrap.getUserManager().loginAs(d3);
        Assert.assertTrue(bugTrap.getProjectManager().getProjectsForSignedInLeadDeveloper().contains(p3));
        Assert.assertFalse(bugTrap.getProjectManager().getProjectsForSignedInLeadDeveloper().contains(p1));
        Assert.assertFalse(bugTrap.getProjectManager().getProjectsForSignedInLeadDeveloper().contains(p2));
    }

    @Test
    public void testCreateSubsystem() {
        ISubsystem sub = null;
        ((Project) office).createSubsystem("name", "description");
        sub = bugTrap.getProjectManager().getSubsystemWithName("name");

        Assert.assertEquals(sub.getName(), "name");
        Assert.assertEquals(sub.getDescription(), "description");
        Assert.assertEquals(sub.getProject(), office);
        Assert.assertEquals(sub.getParent(), office);
        Assert.assertEquals(sub.getSubsystems().size(), 0);
        Assert.assertTrue(office.getSubsystems().contains(sub));

        ISubsystem subsub = null;
        ((Subsystem) sub).createSubsystem("name2", "descr2");
		subsub = bugTrap.getProjectManager().getSubsystemWithName("name2");

        Assert.assertEquals(subsub.getName(), "name2");
        Assert.assertEquals(subsub.getDescription(), "descr2");
        Assert.assertEquals(subsub.getProject(), office);
        Assert.assertEquals(subsub.getParent(), sub);
        Assert.assertEquals(subsub.getSubsystems().size(), 0);
        Assert.assertTrue(sub.getSubsystems().contains(subsub));

        Assert.assertEquals(sub.getSubsystems().size(), 1);
        Assert.assertTrue(office.getAllDirectOrIndirectSubsystems().contains(subsub));
        Assert.assertFalse(office.getSubsystems().contains(subsub));
    }

    @Test
    public void testGetSubsystemWithName() {
    	((Project) office).createSubsystem("name", "description");
        ISubsystem sub = office.getSubsystems().get(office.getSubsystems().size() - 1);
        assertEquals(bugTrap.getProjectManager().getSubsystemWithName("name").getName(), "name");
        assertEquals(bugTrap.getProjectManager().getSubsystemWithName("name"), sub);
    }
}
