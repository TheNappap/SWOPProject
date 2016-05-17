package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Stack;

import org.junit.Test;

import controllers.exceptions.UnauthorizedAccessException;
import model.bugreports.TargetMilestone;
import model.bugreports.bugtag.BugTag;
import model.projects.AchievedMilestone;
import model.projects.IProject;
import model.projects.ISubsystem;
import model.projects.System;
import model.projects.forms.DeclareAchievedMilestoneForm;

public class DeclaredAchievedMilestoneUseCaseTest extends BugTrapTest {

	@Test
	public void DeclareAchievedMilestoneToSubsystemTest() {
		//Log in.
		userController.loginAs(lead);
		
		//Step 1. The developer indicates that he wants to declare an achieved milestone.
		DeclareAchievedMilestoneForm form = null;
		try {
			form = projectController.getDeclareAchievedMilestoneForm();
		} catch (UnauthorizedAccessException e) { fail("Not authorised."); }
		
		//Step 2. The system shows a list of projects.
		List<IProject> projects = projectController.getProjectList();
		//Step 3. The developer selects a project.
		IProject project = projects.get(0);
		
		//Step 4. system shows a list of subsystems of the selected project.
		project.getAllDirectOrIndirectSubsystems();
		
		//Step 5. The developer selects a subsystem.
		ISubsystem subsystem = project.getSubsystems().get(2);
		form.setSystem(subsystem);
		
		//Step 6. The system shows the currently achieved milestones and asks for a new one.
		List<AchievedMilestone> stones = new ArrayList<>();
		stones.add(subsystem.getAchievedMilestone());
		for (ISubsystem s : subsystem.getAllDirectOrIndirectSubsystems())
			stones.add(s.getAchievedMilestone());
		
		//Step 7. The developer proposes a new achieved milestone.
		List<Integer> numbers = Arrays.asList(new Integer[] {1,2,3});
		form.setNumbers(numbers);
		
		//Step 8. system updates the achieved milestone of the selected component. 
		//If necessary, the system first recursively updates the achieved milestone of all the subsystems that the component contains.
		try {
			projectController.declareAchievedMilestone(form);
		} catch (UnauthorizedAccessException e) {
			fail(e.getMessage());
		}

		//Confirm
		assertEquals("M1.2.3",subsystem.getAchievedMilestone().toString());
	}
	
	@Test
	public void DeclareAchievedMilestoneToProjectTest() {
		//Log in.
		userController.loginAs(lead);
		
		//set subsystem to new milestone to prevent error
		List<IProject> ps = projectController.getProjectList();
		IProject p = ps.get(0);
		Stack<ISubsystem> subs = new Stack<>();
		for (ISubsystem sub : p.getAllDirectOrIndirectSubsystems())
			subs.push(sub);
		while (!subs.isEmpty())
			((System) subs.pop()).declareAchievedMilestone(Arrays.asList(new Integer[] {1,2,3}));

		//Step 1. The developer indicates that he wants to declare an achieved milestone.
		DeclareAchievedMilestoneForm form = null;
		try {
			form = projectController.getDeclareAchievedMilestoneForm();
		} catch (UnauthorizedAccessException e) { fail("Not authorised."); }
		
		//Step 2. The system shows a list of projects.
		List<IProject> projects = projectController.getProjectList();
		
		//Step 3. The developer selects a project.
		IProject project = projects.get(0);
		
		//Step 4. system shows a list of subsystems of the selected project.
		project.getAllDirectOrIndirectSubsystems();
		
		//Step 5a. The developer indicates he wants to change the achieved milestone of the entire project.
		form.setSystem(project);
		
		//Step 6. The system shows the currently achieved milestones and asks for a new one.
		List<AchievedMilestone> stones = new ArrayList<>();
		stones.add(project.getAchievedMilestone());
		for (ISubsystem s : project.getAllDirectOrIndirectSubsystems())
			stones.add(s.getAchievedMilestone());

		//Step 7. The developer proposes a new achieved milestone.
		List<Integer> numbers = Arrays.asList(new Integer[] {1,2,3});
		form.setNumbers(numbers);
		
		//Step 8. system updates the achieved milestone of the selected component. 
		//If necessary, the system first recursively updates the achieved milestone of all the subsystems that the component contains.
		try {
			projectController.declareAchievedMilestone(form);
		} catch (UnauthorizedAccessException e) {
			fail(e.getMessage());
		}
		
		//Confirm
		assertEquals("M1.2.3",project.getAchievedMilestone().toString());
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void MilestoneTooHighTest() {
		//Log in.
		userController.loginAs(lead);
		
		//Step 1. The developer indicates that he wants to declare an achieved milestone.
		DeclareAchievedMilestoneForm form = null;
		try {
			form = projectController.getDeclareAchievedMilestoneForm();
		} catch (UnauthorizedAccessException e) { fail("Not authorised."); }
		
		//Step 2. The system shows a list of projects.
		List<IProject> projects = projectController.getProjectList();
		
		//Step 3. The developer selects a project.
		IProject project = projects.get(0);
		
		//Step 4. system shows a list of subsystems of the selected project.
		project.getAllDirectOrIndirectSubsystems();
		
		//Step 5a. The developer indicates he wants to change the achieved milestone of the entire project.
		form.setSystem(project);
		
		//Step 6. The system shows the currently achieved milestones and asks for a new one.
		List<AchievedMilestone> stones = new ArrayList<>();
		stones.add(project.getAchievedMilestone());
		for (ISubsystem s : project.getAllDirectOrIndirectSubsystems())
			stones.add(s.getAchievedMilestone());

		
		//Step 7. The developer proposes a new achieved milestone.
		List<Integer> numbers = Arrays.asList(new Integer[] {1,2,3});
		form.setNumbers(numbers);
		
		//Step 8. system updates the achieved milestone of the selected component. 
		//If necessary, the system first recursively updates the achieved milestone of all the subsystems that the component contains.
		try {
			projectController.declareAchievedMilestone(form);
		} catch (UnauthorizedAccessException e) {
			fail(e.getMessage());
		}
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void EarlyBugReportNotClosedTest() {
		//Log in.
		userController.loginAs(lead);

		//create bugreport with lower target milestone and set subsystem to new milestone to prevent error
		List<IProject> ps = projectController.getProjectList();
		IProject p = ps.get(0);
		List<ISubsystem> ss = p.getAllDirectOrIndirectSubsystems();
		ISubsystem sub = ss.get(0);
		((System) sub).declareAchievedMilestone(Arrays.asList(new Integer[] {0,1}));
		// shortcut this via BugTrap as it's just the setup
		bugTrap.getBugReportManager().addBugReport("title", "description", new Date(3), sub, lead, new ArrayList<>(), new ArrayList<>(), BugTag.NEW, new TargetMilestone(Arrays.asList(new Integer[] {0,2})), 3);
		
		
		//Step 1. The developer indicates that he wants to declare an achieved milestone.
		DeclareAchievedMilestoneForm form = null;
		try {
			form = projectController.getDeclareAchievedMilestoneForm();
		} catch (UnauthorizedAccessException e) { fail("Not authorised."); }
		
		//Step 2. The system shows a list of projects.
		List<IProject> projects = projectController.getProjectList();
		
		//Step 3. The developer selects a project.
		IProject project = projects.get(0);
		
		//Step 4. system shows a list of subsystems of the selected project.
		project.getAllDirectOrIndirectSubsystems();
		
		//Step 5a. The developer indicates he wants to change the achieved milestone of the entire project.
		form.setSystem(project);
		
		//Step 6. The system shows the currently achieved milestones and asks for a new one.
		List<AchievedMilestone> stones = new ArrayList<>();
		stones.add(project.getAchievedMilestone());
		for (ISubsystem s : project.getAllDirectOrIndirectSubsystems())
			stones.add(s.getAchievedMilestone());

		
		//Step 7. The developer proposes a new achieved milestone.
		List<Integer> numbers = Arrays.asList(new Integer[] {1,2,3});
		form.setNumbers(numbers);
		
		//Step 8. system updates the achieved milestone of the selected component. 
		//If necessary, the system first recursively updates the achieved milestone of all the subsystems that the component contains.
		try {
			projectController.declareAchievedMilestone(form);
		} catch (UnauthorizedAccessException e) {
			fail(e.getMessage());
		}
	}
	
	@Test (expected = UnauthorizedAccessException.class)
	public void authorisationTest() throws UnauthorizedAccessException {
		//Can't declare milestone when not logged in.
		projectController.getDeclareAchievedMilestoneForm();
	}
	
	@Test (expected = NullPointerException.class)
	public void varsNotFilledTest() throws UnauthorizedAccessException {
		//Log in as dev.
		userController.loginAs(lead);
		projectController.getDeclareAchievedMilestoneForm().allVarsFilledIn();
	}	

}
