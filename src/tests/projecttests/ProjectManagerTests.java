package tests.projecttests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import controllers.exceptions.UnauthorizedAccessException;
import model.BugTrap;
import model.projects.IProject;
import model.projects.ISubsystem;
import model.projects.Project;
import model.projects.Role;
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
    public void testUpdateProject() {
        bugTrap.getProjectManager().updateProject(office, "nn", "dd", 3883, new Date(2015, 11, 1));

        Assert.assertEquals(office.getName(), "nn");
        Assert.assertEquals(office.getDescription(), "dd");
        Assert.assertEquals(office.getStartDate(), new Date(2015, 11, 1));
        Assert.assertEquals(office.getBudgetEstimate(), 3883, 0.0001);
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
    public void testAssignToProject() {
        bugTrap.getProjectManager().createProject("n", "d", new Date(2015, 8, 18), new Date(2015, 9, 1), 123, null, new Version(1, 0, 0));
        IProject project = bugTrap.getProjectManager().getProjects().get(bugTrap.getProjectManager().getProjects().size() - 1);
        //to assign, need to be logged in as dev
		bugTrap.getUserManager().loginAs(lead);


        UserManager um = new UserManager();
        um.createDeveloper("", "", "", "D0");
        IUser d0 = um.getDevelopers().get(0);
        um.createDeveloper("", "", "", "D1");
        IUser d1 = um.getDevelopers().get(1);
        um.createDeveloper("", "", "", "D2");
        IUser d2 = um.getDevelopers().get(2);

        bugTrap.getProjectManager().assignToProject(office, d0, Role.PROGRAMMER);
		Assert.assertTrue(office.getProgrammers().contains(d0));
        Assert.assertFalse(office.getProgrammers().contains(d1));
        Assert.assertFalse(office.getProgrammers().contains(d2));

        bugTrap.getProjectManager().assignToProject(office, d1, Role.TESTER);
		Assert.assertFalse(office.getTesters().contains(d0));
        Assert.assertTrue(office.getTesters().contains(d1));
        Assert.assertFalse(office.getTesters().contains(d2));

        bugTrap.getProjectManager().assignToProject(project, d2, Role.LEAD);
		Assert.assertNotEquals(project.getLeadDeveloper(), d0);
        Assert.assertNotEquals(project.getLeadDeveloper(), d1);
        Assert.assertEquals(project.getLeadDeveloper(), d2);
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
        bugTrap.getProjectManager().createSubsystem("name", "description", office, office);
        sub = bugTrap.getProjectManager().getSubsystemWithName("name");

        Assert.assertEquals(sub.getName(), "name");
        Assert.assertEquals(sub.getDescription(), "description");
        Assert.assertEquals(sub.getProject(), office);
        Assert.assertEquals(sub.getParent(), office);
        Assert.assertEquals(sub.getSubsystems().size(), 0);
        Assert.assertTrue(office.getSubsystems().contains(sub));

        ISubsystem subsub = null;
		bugTrap.getProjectManager().createSubsystem("name2", "descr2", office, sub);
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
        bugTrap.getProjectManager().createSubsystem("name", "description", office, office);
        ISubsystem sub = office.getSubsystems().get(office.getSubsystems().size() - 1);
        assertEquals(bugTrap.getProjectManager().getSubsystemWithName("name").getName(), "name");
        assertEquals(bugTrap.getProjectManager().getSubsystemWithName("name"), sub);
    }
}
