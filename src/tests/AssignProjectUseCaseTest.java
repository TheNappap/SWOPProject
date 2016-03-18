package tests;


import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import controllers.exceptions.UnauthorizedAccessException;
import model.BugTrap;
import model.FormFactory;
import model.projects.Project;
import model.projects.ProjectManager;
import model.projects.ProjectTeam;
import model.projects.Role;
import model.projects.Version;
import model.projects.forms.ProjectAssignForm;
import model.users.Developer;

public class AssignProjectUseCaseTest {

	private BugTrap bugTrap;
	private ProjectManager projectManager;
	private FormFactory formFactory;

	@Before
	public void setUp() throws Exception {
		bugTrap = new BugTrap();
		projectManager = bugTrap.getProjectManager();
		formFactory = new FormFactory(bugTrap);

		bugTrap.getUserManager().createAdmin();
	}

	@Test
	public void assignProgrammerToProjectTest() throws UnauthorizedAccessException {
		ProjectAssignForm form = formFactory.makeProjectAssignForm();
		Project project = projectManager.createProject("", "", new Date(), new Date(), 123, new ProjectTeam(), new Version(1, 0, 0));
		Developer dev = new Developer("", "", "", "");

		form.setRole(Role.PROGRAMMER);
		form.setDeveloper(dev);
		form.setProject(project);

		projectManager.assignToProject(form);

		Assert.assertEquals(project.getTeam().getProgrammers().get(0), dev);
	}

	@Test
	public void assignLeadToProjectTest() throws UnauthorizedAccessException {
		ProjectAssignForm form = formFactory.makeProjectAssignForm();
		Project project = projectManager.createProject("", "", new Date(), new Date(), 123, new ProjectTeam(), new Version(1, 0, 0));
		Developer dev = new Developer("", "", "", "");

		form.setRole(Role.LEAD);
		form.setDeveloper(dev);
		form.setProject(project);

		Assert.assertEquals(project.getTeam().getLeadDeveloper(), dev);
	}

	@Test
	public void assignTesterToProjectTest() throws UnauthorizedAccessException {
		ProjectAssignForm form = formFactory.makeProjectAssignForm();
		Project project = projectManager.createProject("", "", new Date(), new Date(), 123, new ProjectTeam(), new Version(1, 0, 0));
		Developer dev = new Developer("", "", "", "");

		form.setRole(Role.TESTER);
		form.setDeveloper(dev);
		form.setProject(project);

		projectManager.assignToProject(form);

		Assert.assertEquals(project.getTeam().getTesters().get(0), dev);
	}
}
