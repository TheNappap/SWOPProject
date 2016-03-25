package tests.projecttests;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import controllers.exceptions.UnauthorizedAccessException;
import model.BugTrap;
import model.projects.IProject;
import model.projects.ISubsystem;
import model.projects.Project;
import model.projects.ProjectManager;
import model.projects.Role;
import model.projects.Version;
import model.users.IUser;
import model.users.UserManager;

public class ProjectManagerTests {

    private ProjectManager projectManager;
    private BugTrap bugTrap;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
        bugTrap = new BugTrap();
        projectManager = new ProjectManager(bugTrap);
        IUser admin = bugTrap.getUserManager().createAdmin("", "", "", "ADMIN");
        bugTrap.getUserManager().createDeveloper("", "", "", "DEV");
        bugTrap.getUserManager().loginAs(admin);
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testCreateProject() throws UnauthorizedAccessException {
        IProject project = projectManager.createProject("n", "d", new Date(2015, 8, 18), new Date(2015, 9, 1), 123, null, new Version(1, 0, 0));
        Assert.assertTrue(projectManager.getProjects().contains(project));

        Assert.assertEquals(project.getName(), "n");
        Assert.assertEquals(project.getDescription(), "d");
        Assert.assertEquals(project.getBudgetEstimate(), 123, 0.0000001);
        Assert.assertEquals(project.getVersion(), new Version(1, 0, 0));
        Assert.assertEquals(project.getStartDate(), new Date(2015, 9, 1));
        Assert.assertEquals(project.getCreationDate(), new Date(2015, 8, 18));
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testCreateFork() {
        IProject project = null;
        IProject fork = null;
		try {
			project = projectManager.createProject("n", "d", new Date(2015, 8, 18), new Date(2015, 9, 1), 123, null, new Version(1, 0, 0));
			fork = projectManager.createFork(project, 123592929, new Version(2, 1, 0), new Date(2016, 1, 1));
		} catch (UnauthorizedAccessException e) {
			fail("not authorized");
			e.printStackTrace();
		}

        Assert.assertEquals(project.getName(), fork.getName());
        Assert.assertEquals(project.getDescription(), fork.getDescription());
        Assert.assertEquals(project.getLeadDeveloper(), fork.getLeadDeveloper());
        Assert.assertEquals(project.getProgrammers(), fork.getProgrammers());
        Assert.assertEquals(project.getTesters(), fork.getTesters());
        Assert.assertEquals(project.getVersion(), new Version(1, 0, 0));
        Assert.assertEquals(fork.getVersion(), new Version(2, 1, 0));
        Assert.assertEquals(fork.getBudgetEstimate(), 123592929, 0.0000001);
        Assert.assertEquals(fork.getStartDate(), new Date(2016, 1, 1));
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testUpdateProject() {
        IProject project = null;
		try {
			project = projectManager.createProject("n", "d", new Date(2015, 8, 18), new Date(2015, 9, 1), 123, null, new Version(1, 0, 0));
		} catch (UnauthorizedAccessException e) {
			fail("not authorized");
			e.printStackTrace();
		}
        UserManager um = new UserManager();
        um.createDeveloper("", "", "", "D");
        try {
			projectManager.updateProject(project, "nn", "dd", 3883, new Date(2015, 11, 1));
		} catch (UnauthorizedAccessException e) {
			fail("not authorized");
			e.printStackTrace();
		}

        Assert.assertEquals(project.getName(), "nn");
        Assert.assertEquals(project.getDescription(), "dd");
        Assert.assertEquals(project.getStartDate(), new Date(2015, 11, 1));
        Assert.assertEquals(project.getBudgetEstimate(), 3883, 0.0001);
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testDeleteProject() throws UnauthorizedAccessException {
        IProject project = projectManager.createProject("n", "d", new Date(2015, 8, 18), new Date(2015, 9, 1), 123, null, new Version(1, 0, 0));
        projectManager.deleteProject(project);

        Assert.assertEquals(projectManager.getProjects().size(), 0);
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testAssignToProject() {
    	
        IProject project = null;
		try {
			project = projectManager.createProject("n", "d", new Date(2015, 8, 18), new Date(2015, 9, 1), 123, null, new Version(1, 0, 0));
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

        try {
			projectManager.assignToProject(project, d0, Role.PROGRAMMER);
		} catch (UnauthorizedAccessException e) {
			fail("not authorized");
			e.printStackTrace();
		}
        Assert.assertTrue(project.getProgrammers().contains(d0));
        Assert.assertFalse(project.getProgrammers().contains(d1));
        Assert.assertFalse(project.getProgrammers().contains(d2));

        try {
			projectManager.assignToProject(project, d1, Role.TESTER);
		} catch (UnauthorizedAccessException e) {
			fail("not authorized");
			e.printStackTrace();
		}
        Assert.assertFalse(project.getTesters().contains(d0));
        Assert.assertTrue(project.getTesters().contains(d1));
        Assert.assertFalse(project.getTesters().contains(d2));

        try {
			projectManager.assignToProject(project, d2, Role.LEAD);
		} catch (UnauthorizedAccessException e) {
			fail("not authorized");
			e.printStackTrace();
		}
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
			p1 = projectManager.createProject("n", "d", new Date(2015, 8, 18), new Date(2015, 9, 1), 123, d1, new Version(1, 0, 0));
			p2 = projectManager.createProject("n", "d", new Date(2015, 8, 18), new Date(2015, 9, 1), 123, d2, new Version(1, 0, 0));
	        p3 = projectManager.createProject("n", "d", new Date(2015, 8, 18), new Date(2015, 9, 1), 123, d3, new Version(1, 0, 0));
		} catch (UnauthorizedAccessException e1) {
			fail("not authorized");
			e1.printStackTrace();
		}
        
        
        try {
        	bugTrap.getUserManager().loginAs(d1);
			Assert.assertTrue(projectManager.getProjectsForSignedInLeadDeveloper().contains(p1));
	        Assert.assertFalse(projectManager.getProjectsForSignedInLeadDeveloper().contains(p2));
	        Assert.assertFalse(projectManager.getProjectsForSignedInLeadDeveloper().contains(p3));
	        
	        bugTrap.getUserManager().loginAs(d2);
	        Assert.assertTrue(projectManager.getProjectsForSignedInLeadDeveloper().contains(p2));
	        Assert.assertFalse(projectManager.getProjectsForSignedInLeadDeveloper().contains(p3));
	        Assert.assertFalse(projectManager.getProjectsForSignedInLeadDeveloper().contains(p1));
	        
	        bugTrap.getUserManager().loginAs(d3);
	        Assert.assertTrue(projectManager.getProjectsForSignedInLeadDeveloper().contains(p3));
	        Assert.assertFalse(projectManager.getProjectsForSignedInLeadDeveloper().contains(p1));
	        Assert.assertFalse(projectManager.getProjectsForSignedInLeadDeveloper().contains(p2));
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
			project = projectManager.createProject("n", "d", new Date(2015, 8, 18), new Date(2015, 9, 1), 123, null, new Version(1, 0, 0));
			sub = projectManager.createSubsystem("name", "description", project, project, new Version(1, 0, 0));
		} catch (UnauthorizedAccessException e) {
			fail("not authorized");
			e.printStackTrace();
		}

        Assert.assertEquals(sub.getName(), "name");
        Assert.assertEquals(sub.getDescription(), "description");
        Assert.assertEquals(sub.getVersion(), new Version(1, 0, 0));
        Assert.assertEquals(sub.getProject(), project);
        Assert.assertEquals(sub.getParent(), project);
        Assert.assertEquals(sub.getSubsystems().size(), 0);
        Assert.assertTrue(project.getSubsystems().contains(sub));

        ISubsystem subsub = null;
		try {
			subsub = projectManager.createSubsystem("name2", "descr2", project, sub, new Version(1, 0, 1));
		} catch (UnauthorizedAccessException e) {
			fail("not authorized");
			e.printStackTrace();
		}
        Assert.assertEquals(subsub.getName(), "name2");
        Assert.assertEquals(subsub.getDescription(), "descr2");
        Assert.assertEquals(subsub.getVersion(), new Version(1, 0, 1));
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
        Project project;
		try {
			project = (Project)projectManager.createProject("n", "d", new Date(2015, 8, 18), new Date(2015, 9, 1), 123, null, new Version(1, 0, 0));

	        ISubsystem sub = projectManager.createSubsystem("name", "description", project, project, new Version(1, 0, 0));

	        Assert.assertEquals(projectManager.getSubsystemWithName("name"), sub);
		} catch (UnauthorizedAccessException e) {
			fail("not authorized");
			e.printStackTrace();
		}
    }
}
