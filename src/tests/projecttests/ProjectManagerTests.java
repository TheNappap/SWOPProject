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

public class ProjectManagerTests {

    private BugTrap bugTrap;

    @Before
    public void setUp() throws Exception {
    	//New Bugtrap system.
        bugTrap = new BugTrap();
        
        //Create Administrator/Developer.
        bugTrap.getUserManager().createAdmin("", "", "", "ADMIN");
        bugTrap.getUserManager().createDeveloper("", "", "", "DEV");
        
        //Log in with Administrator.
        bugTrap.getUserManager().loginAs(bugTrap.getUserManager().getUser("ADMIN"));
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testCreateProject() throws UnauthorizedAccessException {
    	
    	bugTrap.getProjectManager().createProject("New Project", "Description", new Date(2015, 8, 18), new Date(2015, 9, 1), 123, null, new Version(1, 0, 0));
        Project project = (Project) bugTrap.getProjectManager().getProjects().get(0);

        //System variables.
        assertEquals("New Project", 		project.getName());
        assertEquals("Description", 		project.getDescription());
        assertEquals(null,					project.getParent());
        assertEquals(0,						project.getSubsystems().size());
        assertEquals("M0", 					project.getAchievedMilestones().get(0).toString());
        
        //Project variables.
        assertEquals(new Version(1, 0, 0), 	project.getVersion());
        assertEquals(new Date(2015, 8, 18), project.getCreationDate());
        assertEquals(new Date(2015, 9, 1), 	project.getStartDate());
        assertEquals(123, 					project.getBudgetEstimate(), 0.0000001);
  
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testCreateFork() {
    	IProject project = null;
        IProject fork = null;
        		
		try {
			//Make a Project as usual.
			bugTrap.getProjectManager().createProject("n", "d", new Date(2015, 8, 18), new Date(2015, 9, 1), 123, null, new Version(2, 1, 0));
			project = bugTrap.getProjectManager().getProjects().get(0);
			
			//Three cases where fork's Version isn't incremented.
			try {
				bugTrap.getProjectManager().createFork(project, 123592929, new Version(1, 0, 0), new Date(2016, 1 , 1));
				fail("Fork version should be higher than original Version");
			} catch(Exception e) { }
			try {
				bugTrap.getProjectManager().createFork(project, 123592929, new Version(2, 1, 1), new Date(2016, 1 , 1));
				fail("Fork version should be higher than original Version");
			} catch(Exception e) { }
			try {
				bugTrap.getProjectManager().createFork(project, 123592929, new Version(2, 1, 0), new Date(2016, 1 , 1));
				fail("Fork version should be higher than original Version");
			} catch(Exception e) { }
			
			//Version is actually incremented.
			bugTrap.getProjectManager().createFork(project, 123592929, new Version(2, 1, 1), new Date(2016, 1, 1));
			fork = bugTrap.getProjectManager().getProjects().get(1);
			
		} catch (UnauthorizedAccessException e) {
			//Should be authorized user.
			fail("not authorized");
			e.printStackTrace();
		}
		
		//Check if forked values are correct.
        Assert.assertEquals(project.getName(), 			fork.getName());
        Assert.assertEquals(project.getDescription(), 	fork.getDescription());
        Assert.assertEquals(project.getLeadDeveloper(), fork.getLeadDeveloper());
        Assert.assertEquals(project.getProgrammers(), 	fork.getProgrammers());
        Assert.assertEquals(project.getTesters(), 		fork.getTesters());
        
        //Check if fork is correct.
        Assert.assertEquals(new Version(2, 1, 1), 	fork.getVersion());
        Assert.assertEquals(123592929,				fork.getBudgetEstimate(), 0.0000001);
        Assert.assertEquals(new Date(2016, 1, 1),	fork.getStartDate());
        Assert.assertEquals("M0",					fork.getAchievedMilestones().get(0).toString());
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testUpdateProject() {
        IProject project = null;
		try {
			bugTrap.getProjectManager().createProject("n", "d", new Date(2015, 8, 18), new Date(2015, 9, 1), 123, null, new Version(1, 0, 0));
			project = bugTrap.getProjectManager().getProjects().get(0);
		} catch (UnauthorizedAccessException e) {
			fail("not authorized");
			e.printStackTrace();
		}
        UserManager um = new UserManager();
        um.createDeveloper("", "", "", "D");
        bugTrap.getProjectManager().updateProject(project, "nn", "dd", 3883, new Date(2015, 11, 1));

        Assert.assertEquals(project.getName(), "nn");
        Assert.assertEquals(project.getDescription(), "dd");
        Assert.assertEquals(project.getStartDate(), new Date(2015, 11, 1));
        Assert.assertEquals(project.getBudgetEstimate(), 3883, 0.0001);
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testDeleteProject() throws UnauthorizedAccessException {
    	bugTrap.getProjectManager().createProject("n", "d", new Date(2015, 8, 18), new Date(2015, 9, 1), 123, null, new Version(1, 0, 0));
        
    	bugTrap.getProjectManager().deleteProject(bugTrap.getProjectManager().getProjects().get(0));

        Assert.assertEquals(bugTrap.getProjectManager().getProjects().size(), 0);
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testAssignToProject() {
    	
        IProject project = null;
		try {
			bugTrap.getProjectManager().createProject("n", "d", new Date(2015, 8, 18), new Date(2015, 9, 1), 123, null, new Version(1, 0, 0));
			project = bugTrap.getProjectManager().getProjects().get(0);
		} catch (UnauthorizedAccessException e) {
			fail("not authorized");
			e.printStackTrace();
		}
		
		//to assign, need to be logged in as dev
		IUser dev =  bugTrap.getUserManager().getUser("DEV");
    	bugTrap.getUserManager().loginAs(dev);
    	
		
        UserManager um = new UserManager();
        um.createDeveloper("", "", "", "D0");
        IUser d0 = um.getDevelopers().get(0);
        um.createDeveloper("", "", "", "D1");
        IUser d1 = um.getDevelopers().get(1);
        um.createDeveloper("", "", "", "D2");
        IUser d2 = um.getDevelopers().get(2);

        bugTrap.getProjectManager().assignToProject(project, d0, Role.PROGRAMMER);
		Assert.assertTrue(project.getProgrammers().contains(d0));
        Assert.assertFalse(project.getProgrammers().contains(d1));
        Assert.assertFalse(project.getProgrammers().contains(d2));

        bugTrap.getProjectManager().assignToProject(project, d1, Role.TESTER);
		Assert.assertFalse(project.getTesters().contains(d0));
        Assert.assertTrue(project.getTesters().contains(d1));
        Assert.assertFalse(project.getTesters().contains(d2));

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

        IProject p1 = null;
        IProject p2 = null;
        IProject p3 = null;
		try {
			bugTrap.getProjectManager().createProject("n", "d", new Date(2015, 8, 18), new Date(2015, 9, 1), 123, d1, new Version(1, 0, 0));
			bugTrap.getProjectManager().createProject("n", "d", new Date(2015, 8, 18), new Date(2015, 9, 1), 123, d2, new Version(1, 0, 0));
			bugTrap.getProjectManager().createProject("n", "d", new Date(2015, 8, 18), new Date(2015, 9, 1), 123, d3, new Version(1, 0, 0));
	        p1 = bugTrap.getProjectManager().getProjects().get(0);
	        p2 = bugTrap.getProjectManager().getProjects().get(1);
	        p3 = bugTrap.getProjectManager().getProjects().get(2);
		} catch (UnauthorizedAccessException e1) {
			fail("not authorized");
			e1.printStackTrace();
		}
        
        
        try {
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
		} catch (UnauthorizedAccessException e) {
			fail("not authorized");
			e.printStackTrace();
		}
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testCreateSubsystem() {

        IProject project = null;
        ISubsystem sub = null;
		try {
			bugTrap.getProjectManager().createProject("n", "d", new Date(2015, 8, 18), new Date(2015, 9, 1), 123, null, new Version(1, 0, 0));
			project = bugTrap.getProjectManager().getProjects().get(0);
			bugTrap.getProjectManager().createSubsystem("name", "description", project, project);
			sub = bugTrap.getProjectManager().getSubsystemWithName("name");
		} catch (UnauthorizedAccessException e) {
			fail("not authorized");
			e.printStackTrace();
		}

        Assert.assertEquals(sub.getName(), "name");
        Assert.assertEquals(sub.getDescription(), "description");
        Assert.assertEquals(sub.getProject(), project);
        Assert.assertEquals(sub.getParent(), project);
        Assert.assertEquals(sub.getSubsystems().size(), 0);
        Assert.assertTrue(project.getSubsystems().contains(sub));

        ISubsystem subsub = null;
		try {
			bugTrap.getProjectManager().createSubsystem("name2", "descr2", project, sub);
			subsub = bugTrap.getProjectManager().getSubsystemWithName("name2");
		} catch (UnauthorizedAccessException e) {
			fail("not authorized");
			e.printStackTrace();
		}
        Assert.assertEquals(subsub.getName(), "name2");
        Assert.assertEquals(subsub.getDescription(), "descr2");
        Assert.assertEquals(subsub.getProject(), project);
        Assert.assertEquals(subsub.getParent(), sub);
        Assert.assertEquals(subsub.getSubsystems().size(), 0);
        Assert.assertTrue(sub.getSubsystems().contains(subsub));

        Assert.assertEquals(sub.getSubsystems().size(), 1);
        Assert.assertTrue(project.getAllDirectOrIndirectSubsystems().contains(subsub));
        Assert.assertFalse(project.getSubsystems().contains(subsub));
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testGetSubsystemWithName() {
        IProject project;
		try {
			bugTrap.getProjectManager().createProject("n", "d", new Date(2015, 8, 18), new Date(2015, 9, 1), 123, null, new Version(1, 0, 0));
			
			project = bugTrap.getProjectManager().getProjects().get(0);
			bugTrap.getProjectManager().createSubsystem("name", "description", project, project);

	        Assert.assertEquals(bugTrap.getProjectManager().getSubsystemWithName("name").getName(), "name");
		} catch (UnauthorizedAccessException e) {
			fail("not authorized");
			e.printStackTrace();
		}
    }
}
