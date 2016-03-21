package tests;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import model.projects.Project;
import model.projects.Subsystem;
import model.projects.System;
import model.projects.Version;

public class SystemTests {
    System sys;
    System subsys;
    System subsubsys;
    System subsys2;

    @Before
    public void setUp() throws Exception {
        sys = new Project("", "", Version.firstVersion(), null, null, 12345, null);
        subsys = new Subsystem("", "", sys, Version.firstVersion(), (Project)sys);
        ((Project)sys).addSubsystem((Subsystem)subsys);
        subsubsys = new Subsystem("", "", subsys, Version.firstVersion(), (Project)sys);
        ((Subsystem)subsys).addSubsystem((Subsystem)subsubsys);
        subsys2 = new Subsystem("", "", sys, Version.firstVersion(), (Project)sys);
        ((Project)sys).addSubsystem((Subsystem)subsys2);
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
        Subsystem s = new Subsystem("sub", "descr", null, Version.firstVersion(), (Project)((Subsystem)subsubsys).getProject());
        subsubsys.addSubsystem(s);

        Assert.assertEquals(s.getParent(), subsubsys);
        Assert.assertEquals(s.getSubsystems().size(), 0);
        Assert.assertFalse(subsys.getSubsystems().contains(s));
        Assert.assertTrue(subsys.getAllDirectOrIndirectSubsystems().contains(s));
        Assert.assertTrue(subsubsys.getSubsystems().contains(s));
    }
}
