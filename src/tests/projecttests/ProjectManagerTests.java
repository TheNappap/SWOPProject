package tests.projecttests;

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
import model.projects.Subsystem;
import model.projects.Version;
import model.users.IUser;
import model.users.UserManager;

public class ProjectManagerTests {

    private ProjectManager projectManager;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
        BugTrap bugTrap = new BugTrap();
        projectManager = new ProjectManager(bugTrap);
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
        IProject project = projectManager.createProject("n", "d", new Date(2015, 8, 18), new Date(2015, 9, 1), 123, null, new Version(1, 0, 0));
        IProject fork = projectManager.createFork(project, 123592929, new Version(2, 1, 0), new Date(2016, 1, 1));

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
        IProject project = projectManager.createProject("n", "d", new Date(2015, 8, 18), new Date(2015, 9, 1), 123, null, new Version(1, 0, 0));
        UserManager um = new UserManager();
        um.createDeveloper("", "", "", "D");
        projectManager.updateProject(project, "nn", "dd", 3883, new Date(2015, 11, 1));

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
        IProject project = projectManager.createProject("n", "d", new Date(2015, 8, 18), new Date(2015, 9, 1), 123, null, new Version(1, 0, 0));
        UserManager um = new UserManager();
        um.createDeveloper("", "", "", "D0");
        IUser d0 = um.getDevelopers().get(0);
        um.createDeveloper("", "", "", "D1");
        IUser d1 = um.getDevelopers().get(1);
        um.createDeveloper("", "", "", "D2");
        IUser d2 = um.getDevelopers().get(2);

        projectManager.assignToProject(project, d0, Role.PROGRAMMER);
        Assert.assertTrue(project.getProgrammers().contains(d0));
        Assert.assertFalse(project.getProgrammers().contains(d1));
        Assert.assertFalse(project.getProgrammers().contains(d2));

        projectManager.assignToProject(project, d1, Role.TESTER);
        Assert.assertFalse(project.getTesters().contains(d0));
        Assert.assertTrue(project.getTesters().contains(d1));
        Assert.assertFalse(project.getTesters().contains(d2));

        projectManager.assignToProject(project, d2, Role.LEAD);
        Assert.assertNotEquals(project.getLeadDeveloper(), d0);
        Assert.assertNotEquals(project.getLeadDeveloper(), d1);
        Assert.assertEquals(project.getLeadDeveloper(), d2);
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testGetProjectsForLeadDeveloper() {
        UserManager um = new UserManager();
        um.createDeveloper("", "", "", "D1");
        IUser d1 = um.getDevelopers().get(0);
        um.createDeveloper("", "", "", "D2");
        IUser d2 = um.getDevelopers().get(1);
        um.createDeveloper("", "", "", "D3");
        IUser d3 = um.getDevelopers().get(2);

        IProject p1 = projectManager.createProject("n", "d", new Date(2015, 8, 18), new Date(2015, 9, 1), 123, d1, new Version(1, 0, 0));
        IProject p2 = projectManager.createProject("n", "d", new Date(2015, 8, 18), new Date(2015, 9, 1), 123, d2, new Version(1, 0, 0));
        IProject p3 = projectManager.createProject("n", "d", new Date(2015, 8, 18), new Date(2015, 9, 1), 123, d3, new Version(1, 0, 0));

        Assert.assertTrue(projectManager.getProjectsForLeadDeveloper(d1).contains(p1));
        Assert.assertTrue(projectManager.getProjectsForLeadDeveloper(d2).contains(p2));
        Assert.assertTrue(projectManager.getProjectsForLeadDeveloper(d3).contains(p3));

        Assert.assertFalse(projectManager.getProjectsForLeadDeveloper(d1).contains(p2));
        Assert.assertFalse(projectManager.getProjectsForLeadDeveloper(d2).contains(p3));
        Assert.assertFalse(projectManager.getProjectsForLeadDeveloper(d3).contains(p1));
        Assert.assertFalse(projectManager.getProjectsForLeadDeveloper(d1).contains(p3));
        Assert.assertFalse(projectManager.getProjectsForLeadDeveloper(d2).contains(p1));
        Assert.assertFalse(projectManager.getProjectsForLeadDeveloper(d3).contains(p2));
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testCreateSubsystem() {
        Project project = (Project)projectManager.createProject("n", "d", new Date(2015, 8, 18), new Date(2015, 9, 1), 123, null, new Version(1, 0, 0));
        Subsystem sub = (Subsystem)projectManager.createSubsystem("name", "description", project, project, new Version(1, 0, 0));

        Assert.assertEquals(sub.getName(), "name");
        Assert.assertEquals(sub.getDescription(), "description");
        Assert.assertEquals(sub.getVersion(), new Version(1, 0, 0));
        Assert.assertEquals(sub.getProject(), project);
        Assert.assertEquals(sub.getParent(), project);
        Assert.assertEquals(sub.getSubsystems().size(), 0);
        Assert.assertTrue(project.getSubsystems().contains(sub));

        ISubsystem subsub = projectManager.createSubsystem("name2", "descr2", project, sub, new Version(1, 0, 1));
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
        Project project = (Project)projectManager.createProject("n", "d", new Date(2015, 8, 18), new Date(2015, 9, 1), 123, null, new Version(1, 0, 0));
        ISubsystem sub = projectManager.createSubsystem("name", "description", project, project, new Version(1, 0, 0));

        Assert.assertEquals(projectManager.getSubsystemWithName("name"), sub);
    }
}
