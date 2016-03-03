package tests;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import controllers.ProjectController;
import model.BugTrap;
import model.projects.Project;
import model.projects.Role;
import model.projects.Version;
import model.projects.forms.*;
import model.users.Developer;

public class ProjectTests {

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
		form.setStartDate(new Date(2016, 4, 1));
		
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
		form.setStartDate(new Date(2016, 8, 23));
		
		Date cd = new Date();
		controller.createProject(form);
		Project project = controller.getProjectList().get(1);
		
		Assert.assertTrue(project.getName().equals("Project X"));
		Assert.assertTrue(project.getDescription().equals("This is a very descriptive description!"));
		Assert.assertTrue(project.getBudgetEstimate() == 5000);
		Assert.assertTrue(project.getCreationDate().equals(cd));
		Assert.assertTrue(project.getParent() == null);
		Assert.assertTrue(project.getStartDate().equals(new Date(2016, 8, 23)));
		Assert.assertTrue(project.getTeam().getLeadDeveloper() == lead);
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
		Assert.assertTrue(project.getBudgetEstimate() == 10000);
		Assert.assertTrue(project.getParent() == null);
		Assert.assertTrue(project.getStartDate().equals(d));
		Assert.assertTrue(project.getTeam().getLeadDeveloper() == colleague);
		Assert.assertTrue(project.getVersion().equals(new Version(2, 1, 0)));
	}
	
	@Test
	public void testDeleteProject() {
		controller.deleteProject(project);
		Assert.assertTrue(controller.getProjectList().size() == 0);
	}
	
	@Test
	public void testAssignToProject() {
		ProjectAssignForm form = controller.getProjectAssignForm();
		form.setDeveloper(colleague);
		form.setRole(Role.TESTER);
		form.setProject(project);
		controller.assignToProject(form);
	}
}
