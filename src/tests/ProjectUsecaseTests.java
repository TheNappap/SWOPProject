package tests;

import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import controllers.ProjectController;
import model.BugTrap;
import model.projects.Project;
import model.projects.Role;
import model.projects.Version;
import model.projects.forms.ProjectAssignForm;
import model.projects.forms.ProjectCreationForm;
import model.projects.forms.ProjectUpdateForm;
import model.users.Developer;

public class ProjectUsecaseTests {

	private ProjectController controller;
	private BugTrap bugTrap;
	private Developer lead;
	private Developer colleague;
	private Project project;
	
	@Before
	public void setUp() throws Exception {
		bugTrap = new BugTrap();
		controller = new ProjectController(bugTrap);
		lead = new Developer("John", "Johnny", "Johnson", "Boss");
		colleague = new Developer("Donald", "D", "Duck", "Quack!");
		
		ProjectCreationForm form = controller.getProjectCreationForm();
		form.setBudgetEstimate(5000);
		form.setDescription("Setup project");
		form.setLeadDeveloper(lead);
		form.setName("Project S");
		form.setStartDate(new Date(1302));
		
		controller.createProject(form);
		project = controller.getProjectList().get(0);
	}
	
	@Test
	public void testCreateProject() {
	 	ProjectCreationForm form = controller.getProjectCreationForm();
		form.setBudgetEstimate(5000);
		form.setDescription("This is a very descriptive description!");
		form.setLeadDeveloper(lead);
		form.setName("Project X");
		form.setStartDate(new Date(1302));
		
		Date cd = new Date();
		controller.createProject(form);
		Project project = controller.getProjectList().get(1);
		
		Assert.assertTrue(project.getName().equals("Project X"));
		Assert.assertTrue(project.getDescription().equals("This is a very descriptive description!"));
		Assert.assertEquals(5000, project.getBudgetEstimate(), 0.001);
		Assert.assertTrue(project.getCreationDate().equals(cd));
		Assert.assertNull(project.getParent());
		Assert.assertTrue(project.getStartDate().equals(new Date(1302)));
		Assert.assertEquals(lead,project.getTeam().getLeadDeveloper());
		Assert.assertTrue (project.getVersion().equals(new Version(1, 0, 0)));
	}
	
	@Test
	public void testUpdateProject() {
		Date d = new Date();
		
		ProjectUpdateForm form = controller.getProjectUpdateForm();
		form.setProject(project);
		form.setBudgetEstimate(10000);
		form.setDescription("Updated!");
		form.setLeadDeveloper(colleague);
		form.setName("Project Y");
		form.setStartDate(d);
		form.setVersion(new Version(2, 1, 0));
		
		controller.updateProject(form);
		
		Assert.assertTrue(project.getName().equals("Project Y"));
		Assert.assertTrue(project.getDescription().equals("Updated!"));
		Assert.assertEquals(10000, project.getBudgetEstimate(), 0.001);
		Assert.assertNull(project.getParent());
		Assert.assertTrue(project.getStartDate().equals(d));
		Assert.assertEquals(colleague, project.getTeam().getLeadDeveloper());
		Assert.assertTrue(project.getVersion().equals(new Version(2, 1, 0)));
	}
	
	@Test
	public void testDeleteProject() {
		controller.deleteProject(project);
		Assert.assertEquals(0, controller.getProjectList().size());
	}
	
	@Test
	public void testAssignTesterToProject() {
		ProjectAssignForm form = controller.getProjectAssignForm();
		form.setDeveloper(colleague);
		form.setRole(Role.TESTER);
		form.setProject(project);
		controller.assignToProject(form);
		
		Assert.assertTrue(project.getTeam().getTesters().contains(colleague));
	}
	
	@Test
	public void testAssignProgrammerToProject() {
		ProjectAssignForm form = controller.getProjectAssignForm();
		form.setDeveloper(colleague);
		form.setRole(Role.PROGRAMMER);
		form.setProject(project);
		controller.assignToProject(form);
		
		Assert.assertTrue(project.getTeam().getProgrammers().contains(colleague));
	}
	
	@Test
	public void testgetProjectsForDeveloper() {
		//lead
		for(Project project: controller.getProjectsForLeadDeveloper(lead)){
			Assert.assertEquals(lead, project.getTeam().getLeadDeveloper());
		}
		//not lead
		Assert.assertEquals(0, controller.getProjectsForLeadDeveloper(colleague).size());
	}
}
