package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import controllers.exceptions.UnauthorizedAccessException;
import model.BugTrap;
import model.bugreports.bugtag.BugTag;
import model.projects.IProject;
import model.projects.ISubsystem;
import model.projects.Version;
import model.projects.forms.DeclareAchievedMilestoneForm;
import model.users.IUser;

public class DeclaredAchievedMilestoneUseCaseTest {

	private BugTrap bugTrap;
	
	@Before
	public void setUp() throws Exception {
		//Make System.
		bugTrap = new BugTrap();
		
		//Make Users.
		bugTrap.getUserManager().createDeveloper("", "", "", "DEV");
		bugTrap.getUserManager().createAdmin("", "", "", "ADMIN");
		bugTrap.getUserManager().createIssuer("", "", "", "ISSUER");
		
		//Log in as Administrator, create Project/Subsystem, register for Notification and log off.
		bugTrap.getUserManager().loginAs(bugTrap.getUserManager().getUser("ADMIN"));
		bugTrap.getProjectManager().createProject("name", "description", new Date(1302), new Date(1302), 1234, null, new Version(1, 0, 0));
		bugTrap.getProjectManager().createSubsystem("name", "description", bugTrap.getProjectManager().getProjects().get(0), bugTrap.getProjectManager().getProjects().get(0));

		bugTrap.getUserManager().logOff();
	}

	@Test
	public void DeclareAchievedMilestoneToSubsystemTest() {
		//Log in.
		bugTrap.getUserManager().loginAs(bugTrap.getUserManager().getUser("DEV"));
		
		//Step 1. The developer indicates that he wants to declare an achieved milestone.
		DeclareAchievedMilestoneForm form = null;
		try {
			form = bugTrap.getFormFactory().makeDeclareAchievedMilestoneForm();
		} catch (UnauthorizedAccessException e) { fail("Not authorised."); }
		
		//Step 2. The system shows a list of projects.
		List<IProject> projects = bugTrap.getProjectManager().getProjects();
		
		//Step 3. The developer selects a project.
		IProject project = projects.get(0);
		
		//Step 4. system shows a list of subsystems of the selected project.
		List<ISubsystem> subsystems = project.getAllDirectOrIndirectSubsystems();
		
		//Step 5. The developer selects a subsystem.
		ISubsystem subsystem = subsystems.get(0);
		form.setSystem(subsystem);
		
		//Step 6. The system shows the currently achieved milestones and asks for a new one.
		subsystem.getAchievedMilestones();
		
		//Step 7. The developer proposes a new achieved milestone.
		List<Integer> numbers = Arrays.asList(new Integer[] {1,2,3});
		form.setNumbers(numbers);
		
		//Step 8. system updates the achieved milestone of the selected component. 
		//If necessary, the system first recursively updates the achieved milestone of all the subsystems that the component contains.
		bugTrap.getProjectManager().declareAchievedMilestone(form.getSystem(), form.getNumbers());
		
		//Confirm
		//Initially no notifications.
		assertEquals("M1.2.3",subsystem.getAchievedMilestones().get(subsystem.getAchievedMilestones().size()-1).toString());
				
	}
	
	@Test
	public void DeclareAchievedMilestoneToProjectTest() {
		//Log in.
		bugTrap.getUserManager().loginAs(bugTrap.getUserManager().getUser("DEV"));
		
		//set subsystem to new milestone to prevent error
		List<IProject> ps = bugTrap.getProjectManager().getProjects();
		IProject p = ps.get(0);
		List<ISubsystem> ss = p.getAllDirectOrIndirectSubsystems();
		ISubsystem s = ss.get(0);
		bugTrap.getProjectManager().declareAchievedMilestone(s, Arrays.asList(new Integer[] {1,2,3}));
		
		
		//Step 1. The developer indicates that he wants to declare an achieved milestone.
		DeclareAchievedMilestoneForm form = null;
		try {
			form = bugTrap.getFormFactory().makeDeclareAchievedMilestoneForm();
		} catch (UnauthorizedAccessException e) { fail("Not authorised."); }
		
		//Step 2. The system shows a list of projects.
		List<IProject> projects = bugTrap.getProjectManager().getProjects();
		
		//Step 3. The developer selects a project.
		IProject project = projects.get(0);
		
		//Step 4. system shows a list of subsystems of the selected project.
		project.getAllDirectOrIndirectSubsystems();
		
		//Step 5a. The developer indicates he wants to change the achieved milestone of the entire project.
		form.setSystem(project);
		
		//Step 6. The system shows the currently achieved milestones and asks for a new one.
		project.getAchievedMilestones();
		
		//Step 7. The developer proposes a new achieved milestone.
		List<Integer> numbers = Arrays.asList(new Integer[] {1,2,3});
		form.setNumbers(numbers);
		
		//Step 8. system updates the achieved milestone of the selected component. 
		//If necessary, the system first recursively updates the achieved milestone of all the subsystems that the component contains.
		bugTrap.getProjectManager().declareAchievedMilestone(form.getSystem(), form.getNumbers());
		
		//Confirm
		//Initially no notifications.
		assertEquals("M1.2.3",project.getAchievedMilestones().get(project.getAchievedMilestones().size()-1).toString());
					
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void MilestoneTooHighTest() {
		//Log in.
		bugTrap.getUserManager().loginAs(bugTrap.getUserManager().getUser("DEV"));
		
		//Step 1. The developer indicates that he wants to declare an achieved milestone.
		DeclareAchievedMilestoneForm form = null;
		try {
			form = bugTrap.getFormFactory().makeDeclareAchievedMilestoneForm();
		} catch (UnauthorizedAccessException e) { fail("Not authorised."); }
		
		//Step 2. The system shows a list of projects.
		List<IProject> projects = bugTrap.getProjectManager().getProjects();
		
		//Step 3. The developer selects a project.
		IProject project = projects.get(0);
		
		//Step 4. system shows a list of subsystems of the selected project.
		project.getAllDirectOrIndirectSubsystems();
		
		//Step 5a. The developer indicates he wants to change the achieved milestone of the entire project.
		form.setSystem(project);
		
		//Step 6. The system shows the currently achieved milestones and asks for a new one.
		project.getAchievedMilestones();
		
		//Step 7. The developer proposes a new achieved milestone.
		List<Integer> numbers = Arrays.asList(new Integer[] {1,2,3});
		form.setNumbers(numbers);
		
		//Step 8. system updates the achieved milestone of the selected component. 
		//If necessary, the system first recursively updates the achieved milestone of all the subsystems that the component contains.
		bugTrap.getProjectManager().declareAchievedMilestone(form.getSystem(), form.getNumbers());

	}
	
	@Test (expected = IllegalArgumentException.class)
	public void EarlyBugReportNotClosedTest() {
		//Log in.
		bugTrap.getUserManager().loginAs(bugTrap.getUserManager().getUser("DEV"));
		IUser dev = bugTrap.getUserManager().getUser("DEV");
		
		//create bugreport with lower target milestone
		List<IProject> ps = bugTrap.getProjectManager().getProjects();
		IProject p = ps.get(0);
		List<ISubsystem> ss = p.getAllDirectOrIndirectSubsystems();
		ISubsystem s = ss.get(0);
		bugTrap.getBugReportManager().addBugReportWithTargetMilestone("title", "description", new Date(3), s, dev, new ArrayList<>(), new ArrayList<>(), BugTag.NEW, Arrays.asList(new Integer[] {0,2}));
		
		
		
		//Step 1. The developer indicates that he wants to declare an achieved milestone.
		DeclareAchievedMilestoneForm form = null;
		try {
			form = bugTrap.getFormFactory().makeDeclareAchievedMilestoneForm();
		} catch (UnauthorizedAccessException e) { fail("Not authorised."); }
		
		//Step 2. The system shows a list of projects.
		List<IProject> projects = bugTrap.getProjectManager().getProjects();
		
		//Step 3. The developer selects a project.
		IProject project = projects.get(0);
		
		//Step 4. system shows a list of subsystems of the selected project.
		project.getAllDirectOrIndirectSubsystems();
		
		//Step 5a. The developer indicates he wants to change the achieved milestone of the entire project.
		form.setSystem(project);
		
		//Step 6. The system shows the currently achieved milestones and asks for a new one.
		project.getAchievedMilestones();
		
		//Step 7. The developer proposes a new achieved milestone.
		List<Integer> numbers = Arrays.asList(new Integer[] {1,2,3});
		form.setNumbers(numbers);
		
		//Step 8. system updates the achieved milestone of the selected component. 
		//If necessary, the system first recursively updates the achieved milestone of all the subsystems that the component contains.
		bugTrap.getProjectManager().declareAchievedMilestone(form.getSystem(), form.getNumbers());

	}
	
	@Test
	public void authorisationTest() {
		//Can't declare milestone when not logged in.
		try {
			bugTrap.getFormFactory().makeDeclareAchievedMilestoneForm();
			fail("Can't declare milestone when not logged in.");
		} catch (UnauthorizedAccessException e) { }
	}
	
	@Test
	public void varsNotFilledTest() {
		//Log in as dev.
		bugTrap.getUserManager().loginAs(bugTrap.getUserManager().getUser("DEV"));
		
		try {
			bugTrap.getFormFactory().makeDeclareAchievedMilestoneForm().allVarsFilledIn();
			fail("should throw exception");
		} 
		catch (UnauthorizedAccessException e) 	{ fail("not authorized"); }
		catch (NullPointerException e) 			{ }
	}	

}
