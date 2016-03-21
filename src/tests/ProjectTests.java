package tests;

import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import model.projects.Project;
import model.projects.ProjectTeam;
import model.projects.Version;

public class ProjectTests {
    private Project project;

    @Before
    public void setUp() throws Exception {

    }

    @SuppressWarnings("deprecation")
    @Test
    public void testConstructor(){
        ProjectTeam team = new ProjectTeam();
        Project p = new Project("name", "descr", Version.firstVersion(), new Date(2012, 7, 28), new Date(2013, 1, 1), 12345, team);

        Assert.assertEquals(p.getName(), "name");
        Assert.assertEquals(p.getDescription(), "descr");
        Assert.assertEquals(p.getVersion(), Version.firstVersion());
        Assert.assertEquals(p.getCreationDate(), new Date(2012, 7, 28));
        Assert.assertEquals(p.getStartDate(), new Date(2013, 1, 1));
        Assert.assertEquals(p.getBudgetEstimate(), 12345, 0.0000001);
    }
}
