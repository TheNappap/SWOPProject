package tests;

import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import controllers.ProjectController;
import model.BugTrap;
import model.projects.Project;
import model.projects.Subsystem;
import model.projects.Version;
import model.projects.forms.ProjectCreationForm;
import model.projects.forms.SubsystemCreationForm;
import model.users.Developer;

public class SubsystemUsecaseTests {

	private ProjectController controller;
	private BugTrap bugTrap;
	private Developer lead;
	private Project emptyProject;
	private Project subbedProject;
	private Subsystem sub;
	
	@Before
	public void setUp() throws Exception {
		bugTrap = new BugTrap();
		controller = new ProjectController(bugTrap);
		lead = new Developer("John", "Johnny", "Johnson", "Boss");
		
		ProjectCreationForm form = controller.getProjectCreationForm();
		form.setBudgetEstimate(5000);
		form.setDescription("This is a very descriptive description!");
		form.setLeadDeveloper(lead);
		form.setName("Project X");
		form.setStartDate(new Date(1302));
		controller.createProject(form);
		emptyProject = controller.getProjectList().get(0);
		
		form = controller.getProjectCreationForm();
		form.setBudgetEstimate(500000);
		form.setDescription("This is a somewhat descriptive description!");
		form.setLeadDeveloper(lead);
		form.setName("Project Y");
		form.setStartDate(new Date(1302));
		controller.createProject(form);
		subbedProject = controller.getProjectList().get(1);
		
		SubsystemCreationForm sform = controller.getSubsystemCreationForm();
		sform.setName("Sub");
		sform.setDescription("A test subsystem");
		sform.setParent(subbedProject);
		controller.createSubsystem(sform);
		
		sub = subbedProject.getSubsystems().get(0);
	}
	
	@Test
	public void testCreateSubsystemInEmptyProject() {
		SubsystemCreationForm form = controller.getSubsystemCreationForm();
		form.setName("Sub");
		form.setDescription("A test subsystem");
		form.setParent(emptyProject);
		controller.createSubsystem(form);
		
		Assert.assertEquals(1, emptyProject.getSubsystems().size());
		Subsystem sub = emptyProject.getSubsystems().get(0);
		
		Assert.assertTrue(sub.getDescription().equals("A test subsystem"));
		Assert.assertTrue(sub.getName().equals("Sub"));
		Assert.assertEquals(emptyProject, sub.getParent());
		Assert.assertEquals(emptyProject, sub.getProject());
		Assert.assertEquals(0, sub.getSubsystems().size());
		Assert.assertTrue(sub.getVersion().equals(new Version(1, 0, 0)));
	}

	@Test
	public void testCreateSubsystemInNonEmptyProject() {
		Assert.assertEquals(0, sub.getSubsystems().size());
		
		SubsystemCreationForm form = controller.getSubsystemCreationForm();
		form.setName("Sub");
		form.setDescription("A test subsystem");
		form.setParent(sub);
		Assert.assertEquals(subbedProject, form.getProject());
		controller.createSubsystem(form);
		
		Assert.assertEquals(1, sub.getSubsystems().size());
		
		Subsystem created = sub.getSubsystems().get(0);
		Assert.assertEquals(sub, created.getParent());
		Assert.assertEquals(subbedProject, created.getProject());
	}
}
