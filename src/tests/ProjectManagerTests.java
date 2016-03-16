package tests;

import model.projects.Project;
import model.projects.ProjectManager;
import model.projects.ProjectTeam;
import model.projects.Version;
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
}
