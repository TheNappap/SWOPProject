package tests.projecttests;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import model.bugreports.bugtag.BugTag;
import model.projects.IProject;
import model.projects.ISubsystem;
import model.projects.System;
import model.projects.health.HealthIndicator;
import tests.BugTrapTest;

public class HealthIndicatorTests extends BugTrapTest{

	@Test
	public void HealthIndicatorTest() {
		IProject project = bugTrap.getProjectManager().getProjects().get(0);
		List<HealthIndicator> indicators = project.getHealthIndicators();
		
		assertEquals(HealthIndicator.HEALTHY,indicators.get(0));
		assertEquals(HealthIndicator.HEALTHY,indicators.get(1));
		assertEquals(HealthIndicator.SATISFACTORY,indicators.get(2));
		
		//subsystems
		List<ISubsystem> subsystems = project.getAllDirectOrIndirectSubsystems();
		
		ISubsystem subsystem = subsystems.get(0);
		indicators = subsystem.getHealthIndicators();
		
		assertEquals("Word", subsystem.getName());
		assertEquals(7, ((System) subsystem).getBugImpact(), 0.1);
		assertEquals(HealthIndicator.HEALTHY,indicators.get(0));
		assertEquals(HealthIndicator.HEALTHY,indicators.get(1));
		assertEquals(HealthIndicator.SATISFACTORY,indicators.get(2));
		
		subsystem = subsystems.get(1);
		indicators = subsystem.getHealthIndicators();
		
		assertEquals("Word Art", subsystem.getName());
		assertEquals(0, ((System) subsystem).getBugImpact(), 0.1);
		assertEquals(HealthIndicator.HEALTHY,indicators.get(0));
		assertEquals(HealthIndicator.HEALTHY,indicators.get(1));
		assertEquals(HealthIndicator.HEALTHY,indicators.get(2));
		
		subsystem = subsystems.get(2);
		indicators = subsystem.getHealthIndicators();
		
		assertEquals("Comic Sans", subsystem.getName());
		assertEquals(0, ((System) subsystem).getBugImpact(), 0.1);
		assertEquals(HealthIndicator.HEALTHY,indicators.get(0));
		assertEquals(HealthIndicator.HEALTHY,indicators.get(1));
		assertEquals(HealthIndicator.HEALTHY,indicators.get(2));
		
		subsystem = subsystems.get(3);
		indicators = subsystem.getHealthIndicators();
		
		assertEquals("Clippy", subsystem.getName());
		assertEquals(15, ((System) subsystem).getBugImpact(), 0.1);
		assertEquals(HealthIndicator.HEALTHY,indicators.get(0));
		assertEquals(HealthIndicator.HEALTHY,indicators.get(1));
		assertEquals(HealthIndicator.SATISFACTORY,indicators.get(2));
	}
	
	@Test
	public void HealthIndicatorTest2() {
		//Add BugReports.
	    bugTrap.getUserManager().loginAs(lead);
	    bugTrap.getBugReportManager().addBugReport("Bug1", "...", new Date(1303), word, lead, new ArrayList<>(), new ArrayList<>(), BugTag.NEW, 100);
	    bugTrap.getBugReportManager().addBugReport("Bug2", "...", new Date(1305), word, lead, new ArrayList<>(), new ArrayList<>(), BugTag.NEW, 100);
	    bugTrap.getBugReportManager().addBugReport("Bug3", "...", new Date(1305), clippy, lead, new ArrayList<>(), new ArrayList<>(), BugTag.NEW, 100);

	    //Log off.
	    bugTrap.getUserManager().logOff();
		
	    
		IProject project = bugTrap.getProjectManager().getProjects().get(0);
		List<HealthIndicator> indicators = project.getHealthIndicators();
		
		assertEquals(HealthIndicator.SERIOUS,indicators.get(0));
		assertEquals(HealthIndicator.SERIOUS,indicators.get(1));
		assertEquals(HealthIndicator.CRITICAL,indicators.get(2));
		
		//subsystems
		List<ISubsystem> subsystems = project.getAllDirectOrIndirectSubsystems();
		
		ISubsystem subsystem = subsystems.get(0);
		indicators = subsystem.getHealthIndicators();
		
		assertEquals("Word", subsystem.getName());
		assertEquals(607, ((System) subsystem).getBugImpact(), 0.1);
		assertEquals(HealthIndicator.SERIOUS,indicators.get(0));
		assertEquals(HealthIndicator.SERIOUS,indicators.get(1));
		assertEquals(HealthIndicator.CRITICAL,indicators.get(2));
		
		subsystem = subsystems.get(3);
		indicators = subsystem.getHealthIndicators();
		
		assertEquals("Clippy", subsystem.getName());
		assertEquals(315, ((System) subsystem).getBugImpact(), 0.1);
		assertEquals(HealthIndicator.STABLE,indicators.get(0));
		assertEquals(HealthIndicator.STABLE,indicators.get(1));
		assertEquals(HealthIndicator.SERIOUS,indicators.get(2));
	}

}
