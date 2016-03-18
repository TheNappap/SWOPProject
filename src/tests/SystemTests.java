package tests;

import org.junit.Before;
import org.junit.Test;

import model.projects.Project;
import model.projects.Subsystem;
import model.projects.System;
import model.projects.Version;

public class SystemTests {
    System sys;
    System subsys;
    System subsys2;

    @Before
    public void setUp() throws Exception {
        sys = new Project("", "", Version.firstVersion(), null, null, 12345, null);
        subsys = new Subsystem("", "", sys, Version.firstVersion(), (Project)sys);
        subsys2 = new Subsystem("", "", sys, Version.firstVersion(), (Project)sys);
    }

    @Test
    public void tst(){

    }
}
