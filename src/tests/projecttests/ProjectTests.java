package tests.projecttests;

import java.util.ArrayList;
import java.util.Date;

import model.users.Developer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import model.projects.Project;
import model.projects.ProjectTeam;
import model.projects.Subsystem;
import model.projects.Version;
import tests.BugTrapTest;

import static org.junit.Assert.*;

public class ProjectTests extends BugTrapTest {

    @SuppressWarnings("deprecation")
    @Test
    public void testConstructor(){
        ProjectTeam team = new ProjectTeam();
        Project p = new Project(null, "name", "descr", new ArrayList<Subsystem>(), Version.firstVersion(), new Date(2012, 7, 28), new Date(2013, 1, 1), 12345, team, null);

        Assert.assertEquals(p.getName(), "name");
        Assert.assertEquals(p.getDescription(), "descr");
        Assert.assertEquals(p.getSubsystems(), new ArrayList<Subsystem>());
        Assert.assertEquals(p.getVersion(), Version.firstVersion());
        Assert.assertEquals(p.getCreationDate(), new Date(2012, 7, 28));
        Assert.assertEquals(p.getStartDate(), new Date(2013, 1, 1));
        Assert.assertEquals(p.getBudgetEstimate(), 12345, 0.0000001);
    }

    @Test
    public void testAddProgrammer() {
        Developer dev = bugTrap.getUserManager().createDeveloper("D", "E", "V", "developer");
        office.addProgrammer(dev);
        assertTrue(office.getProgrammers().contains(dev));
        assertFalse(office.getTesters().contains(dev));
        assertNotEquals(office.getLeadDeveloper(), dev);
    }

     @Test
    public void testAddTester() {
         Developer dev = bugTrap.getUserManager().createDeveloper("D", "E", "V", "developer");
         office.addTester(dev);
         assertFalse(office.getProgrammers().contains(dev));
         assertTrue(office.getTesters().contains(dev));
         assertNotEquals(office.getLeadDeveloper(), dev);
     }
}
