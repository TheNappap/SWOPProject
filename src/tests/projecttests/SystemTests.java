package tests.projecttests;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import model.projects.Project;
import model.projects.ProjectTeam;
import model.projects.Subsystem;
import model.projects.Version;

public class SystemTests {
    private Project sys;
    private Subsystem subsys;
    private Subsystem subsubsys;
    private Subsystem subsys2;

    @Before
    public void setUp() throws Exception {
        sys 		= new Project("", "", new ArrayList<Subsystem>(), Version.firstVersion(), null, null, 12345, new ProjectTeam(), null);
        subsys 		= new Subsystem("", "", sys, new ArrayList<Subsystem>(), sys, null);
        subsubsys	= new Subsystem("", "", subsys, new ArrayList<Subsystem>(), sys, null);
        subsys2 	= new Subsystem("", "", sys, new ArrayList<Subsystem>(), sys, null);
    }

    @Test
    public void testSetUp(){
        Assert.assertEquals(subsys.getParent(), sys);
        Assert.assertEquals(subsys2.getParent(), sys);
        Assert.assertEquals(subsys.getSubsystems().size(), 1);
        Assert.assertEquals(sys.getSubsystems().size(), 2);
        Assert.assertEquals(sys.getVersion(), Version.firstVersion());
    }

    @Test
    public void testAddSubsystem() {
        Subsystem s = new Subsystem("sub", "descr", subsubsys, new ArrayList<Subsystem>(), sys, null	);

        Assert.assertEquals(s.getParent(), subsubsys);
        Assert.assertEquals(s.getSubsystems().size(), 0);
        Assert.assertFalse(subsys.getSubsystems().contains(s));
        Assert.assertTrue(subsys.getAllDirectOrIndirectSubsystems().contains(s));
        Assert.assertTrue(subsubsys.getSubsystems().contains(s));
    }

    @Test
    public void testEquals() {
       // Assert.assertFalse(subsys.equals(subsys2)); // Different amount of subsystems
        Assert.assertTrue(subsys2.equals(new Subsystem("", "", sys, new ArrayList<Subsystem>(), sys, null)));
        // Compare "identical" Projects but with different subs
        Project p2 = new Project("", "", new ArrayList<Subsystem>(), Version.firstVersion(), null, null, 12345, null, null);
        Assert.assertFalse(sys.equals(p2));

        Project projA = new Project("n", "d", new ArrayList<Subsystem>(), Version.firstVersion(), null, null, 12345, null, null);
        Project projB = new Project("n", "d", new ArrayList<Subsystem>(), Version.firstVersion(), null, null, 12345, null, null);
        Subsystem subA = new Subsystem("", "", projA, new ArrayList<Subsystem>(), projA, null);
        Subsystem subB = new Subsystem("", "", projB, new ArrayList<Subsystem>(), projB, null);
        Assert.assertTrue(projA.equals(projB));
        Assert.assertEquals(projA, projB);
    }
}
