package tests;

import model.projects.*;
import model.users.Developer;
import model.users.UserManager;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Date;

public class ProjectManagerTests {

    private ProjectManager projectManager;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
        projectManager = new ProjectManager();
    }

    @Test
    public void testCreateProject() {
        Project project = projectManager.createProject("n", "d", new Date(2015, 8, 18), new Date(2015, 9, 1), 123, new ProjectTeam(), new Version(1, 0, 0));
        Assert.assertTrue(projectManager.getProjects().contains(project));

        Assert.assertEquals(project.getName(), "n");
        Assert.assertEquals(project.getDescription(), "d");
        Assert.assertEquals(project.getBudgetEstimate(), 123, 0.0000001);
        Assert.assertEquals(project.getVersion(), new Version(1, 0, 0));
        Assert.assertEquals(project.getStartDate(), new Date(2015, 9, 1));
        Assert.assertEquals(project.getCreationDate(), new Date(2015, 8, 18));
    }

    @SuppressWarnings("deprecated")
    @Test
    public void testCreateFork() {
        Project project = projectManager.createProject("n", "d", new Date(2015, 8, 18), new Date(2015, 9, 1), 123, new ProjectTeam(), new Version(1, 0, 0));
        Project fork = projectManager.createFork(project, 123592929, new Version(2, 1, 0), new Date(2016, 1, 1));

        Assert.assertEquals(project.getName(), fork.getName());
        Assert.assertEquals(project.getDescription(), fork.getDescription());
        Assert.assertEquals(project.getTeam(), fork.getTeam());
        Assert.assertEquals(project.getVersion(), new Version(1, 0, 0));
        Assert.assertEquals(fork.getVersion(), new Version(2, 1, 0));
        Assert.assertEquals(fork.getBudgetEstimate(), 123592929, 0.0000001);
        Assert.assertEquals(fork.getStartDate(), new Date(2016, 1, 1));
    }

    @Test
    public void testUpdateProject() {
        Project project = projectManager.createProject("n", "d", new Date(2015, 8, 18), new Date(2015, 9, 1), 123, new ProjectTeam(), new Version(1, 0, 0));
        UserManager um = new UserManager();
        um.createDeveloper("", "", "", "D");
        Developer d = um.getDevelopers().get(0);
        projectManager.updateProject(project, "nn", "dd", 3883, new Date(2015, 11, 1), d, new Version(2, 1, 4));

        Assert.assertEquals(project.getName(), "nn");
        Assert.assertEquals(project.getDescription(), "dd");
        Assert.assertEquals(project.getTeam().getLeadDeveloper(), d);
        Assert.assertEquals(project.getStartDate(), new Date(2015, 11, 1));
        Assert.assertEquals(project.getVersion(), new Version(2, 1, 4));
        Assert.assertEquals(project.getBudgetEstimate(), 3883, 0.0000001);
    }

    @Test
    public void testDeleteProject() {
        Project project = projectManager.createProject("n", "d", new Date(2015, 8, 18), new Date(2015, 9, 1), 123, new ProjectTeam(), new Version(1, 0, 0));
        projectManager.deleteProject(project);

        Assert.assertEquals(projectManager.getProjects().size(), 0);
    }

    @Test
    public void testAssignToProject() {
        Project project = projectManager.createProject("n", "d", new Date(2015, 8, 18), new Date(2015, 9, 1), 123, new ProjectTeam(), new Version(1, 0, 0));
        UserManager um = new UserManager();
        um.createDeveloper("", "", "", "D0");
        Developer d0 = um.getDevelopers().get(0);
        um.createDeveloper("", "", "", "D1");
        Developer d1 = um.getDevelopers().get(1);
        um.createDeveloper("", "", "", "D2");
        Developer d2 = um.getDevelopers().get(2);

        projectManager.assignToProject(project, d0, Role.PROGRAMMER);
        Assert.assertTrue(project.getTeam().getProgrammers().contains(d0));
        Assert.assertFalse(project.getTeam().getProgrammers().contains(d1));
        Assert.assertFalse(project.getTeam().getProgrammers().contains(d2));

        projectManager.assignToProject(project, d1, Role.TESTER);
        Assert.assertFalse(project.getTeam().getTesters().contains(d0));
        Assert.assertTrue(project.getTeam().getTesters().contains(d1));
        Assert.assertFalse(project.getTeam().getTesters().contains(d2));

        projectManager.assignToProject(project, d2, Role.LEAD);
        Assert.assertNotEquals(project.getTeam().getLeadDeveloper(), d0);
        Assert.assertNotEquals(project.getTeam().getLeadDeveloper(), d1);
        Assert.assertEquals(project.getTeam().getLeadDeveloper(), d2);
    }

    @Test
    public void testGetProjectsForLeadDeveloper() {
        UserManager um = new UserManager();
        um.createDeveloper("", "", "", "D1");
        Developer d1 = um.getDevelopers().get(0);
        um.createDeveloper("", "", "", "D2");
        Developer d2 = um.getDevelopers().get(1);
        um.createDeveloper("", "", "", "D3");
        Developer d3 = um.getDevelopers().get(2);

        ProjectTeam t1 = new ProjectTeam();
        t1.setLeadDeveloper(d1);
        Project p1 = projectManager.createProject("n", "d", new Date(2015, 8, 18), new Date(2015, 9, 1), 123, t1, new Version(1, 0, 0));

        ProjectTeam t2 = new ProjectTeam();
        t2.setLeadDeveloper(d2);
        Project p2 = projectManager.createProject("n", "d", new Date(2015, 8, 18), new Date(2015, 9, 1), 123, t2, new Version(1, 0, 0));

        ProjectTeam t3 = new ProjectTeam();
        t3.setLeadDeveloper(d3);
        Project p3 = projectManager.createProject("n", "d", new Date(2015, 8, 18), new Date(2015, 9, 1), 123, t3, new Version(1, 0, 0));

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

    @Test
    public void testCreateSubsystem() {
        Project project = projectManager.createProject("n", "d", new Date(2015, 8, 18), new Date(2015, 9, 1), 123, new ProjectTeam(), new Version(1, 0, 0));
        Subsystem sub = projectManager.createSubsystem("name", "description", project, project, new Version(1, 0, 0));

        Assert.assertEquals(sub.getName(), "name");
        Assert.assertEquals(sub.getDescription(), "description");
        Assert.assertEquals(sub.getVersion(), new Version(1, 0, 0));
        Assert.assertEquals(sub.getProject(), project);
        Assert.assertEquals(sub.getParent(), project);
        Assert.assertEquals(sub.getSubsystems().size(), 0);
        Assert.assertTrue(project.getSubsystems().contains(sub));

        Subsystem subsub = projectManager.createSubsystem("name2", "descr2", project, sub, new Version(1, 0, 1));
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

    @Test
    public void testGetSubsystemWithName() {
        Project project = projectManager.createProject("n", "d", new Date(2015, 8, 18), new Date(2015, 9, 1), 123, new ProjectTeam(), new Version(1, 0, 0));
        Subsystem sub = projectManager.createSubsystem("name", "description", project, project, new Version(1, 0, 0));

        Assert.assertEquals(projectManager.getSubsystemWithName("name"), sub);
    }
}
