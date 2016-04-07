package tests.projecttests;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import model.projects.Project;
import model.projects.Subsystem;
import model.projects.Version;

public class SystemTests {
    private Project sys;
    private Subsystem subsys;
    private Subsystem subsubsys;
    private Subsystem subsys2;

    @Before
    public void setUp() throws Exception {
        sys 		= new Project("", "", new ArrayList<Subsystem>(), Version.firstVersion(), null, null, 12345, null);
        subsys 		= new Subsystem("", "", sys, new ArrayList<Subsystem>(), sys);
        subsubsys	= new Subsystem("", "", subsys, new ArrayList<Subsystem>(), sys);
        subsys2 	= new Subsystem("", "", sys, new ArrayList<Subsystem>(), sys);
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
        Subsystem s = new Subsystem("sub", "descr", subsubsys, new ArrayList<Subsystem>(), sys);

        Assert.assertEquals(s.getParent(), subsubsys);
        Assert.assertEquals(s.getSubsystems().size(), 0);
        Assert.assertFalse(subsys.getSubsystems().contains(s));
        Assert.assertTrue(subsys.getAllDirectOrIndirectSubsystems().contains(s));
        Assert.assertTrue(subsubsys.getSubsystems().contains(s));
    }
}
