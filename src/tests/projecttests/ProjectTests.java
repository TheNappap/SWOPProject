package tests.projecttests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;

import org.junit.Test;

import model.projects.Project;
import model.projects.ProjectTeam;
import model.projects.Role;
import model.projects.Subsystem;
import model.projects.Version;
import model.users.Developer;
import model.users.IUser;
import model.users.UserManager;
import tests.BugTrapTest;

public class ProjectTests extends BugTrapTest {

    @SuppressWarnings("deprecation")
    @Test
    public void testConstructor(){
        ProjectTeam team = new ProjectTeam();
        Project p = new Project(null, "name", "descr", new ArrayList<Subsystem>(), Version.firstVersion(), new Date(2012, 7, 28), new Date(2013, 1, 1), 12345, team, null);

        assertEquals(p.getName(), "name");
        assertEquals(p.getDescription(), "descr");
        assertEquals(p.getSubsystems(), new ArrayList<Subsystem>());
        assertEquals(p.getVersion(), Version.firstVersion());
        assertEquals(p.getCreationDate(), new Date(2012, 7, 28));
        assertEquals(p.getStartDate(), new Date(2013, 1, 1));
        assertEquals(p.getBudgetEstimate(), 12345, 0.0000001);
    }

    @Test
    public void testAddProgrammer() {
        Developer dev = bugTrap.getUserManager().createDeveloper("D", "E", "V", "developer");
        ((Project) office).addProgrammer(dev);
        assertTrue(office.getProgrammers().contains(dev));
        assertFalse(office.getTesters().contains(dev));
        assertNotEquals(office.getLeadDeveloper(), dev);
    }

     @Test
    public void testAddTester() {
         Developer dev = bugTrap.getUserManager().createDeveloper("D", "E", "V", "developer");
         ((Project) office).addTester(dev);
         assertFalse(office.getProgrammers().contains(dev));
         assertTrue(office.getTesters().contains(dev));
         assertNotEquals(office.getLeadDeveloper(), dev);
     }
     
     @SuppressWarnings("deprecation")
     @Test
     public void testUpdateProject() {
         ((Project)office).update("nn", "dd", 3883, new Date(2015, 11, 1), new Version(6, 3, 2));

         assertEquals(office.getName(), "nn");
         assertEquals(office.getDescription(), "dd");
         assertEquals(office.getStartDate(), new Date(2015, 11, 1));
         assertEquals(office.getBudgetEstimate(), 3883, 0.0001);
         assertEquals(office.getVersion(), new Version(6, 3, 2));
     }
     
     @SuppressWarnings("deprecation")
     @Test
     public void testAssignToProject() {
         bugTrap.getProjectManager().createProject("n", "d", new Date(2015, 8, 18), new Date(2015, 9, 1), 123, null, new Version(1, 0, 0));
         Project project = ((Project)office);
         //to assign, need to be logged in as dev
 		 bugTrap.getUserManager().loginAs(lead);


         UserManager um = new UserManager();
         um.createDeveloper("", "", "", "D0");
         IUser d0 = um.getDevelopers().get(0);
         um.createDeveloper("", "", "", "D1");
         IUser d1 = um.getDevelopers().get(1);
         um.createDeveloper("", "", "", "D2");
         IUser d2 = um.getDevelopers().get(2);

         project.assignToProject(d0, Role.PROGRAMMER);
 		 assertTrue(office.getProgrammers().contains(d0));
         assertFalse(office.getProgrammers().contains(d1));
         assertFalse(office.getProgrammers().contains(d2));

         project.assignToProject(d1, Role.TESTER);
 		 assertFalse(office.getTesters().contains(d0));
         assertTrue(office.getTesters().contains(d1));
         assertFalse(office.getTesters().contains(d2));
     }
}
