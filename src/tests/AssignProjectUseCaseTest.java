package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.projects.*;
import org.junit.Before;
import org.junit.Test;

import controllers.ProjectController;
import controllers.UserController;
import controllers.exceptions.UnauthorizedAccessException;
import model.BugTrap;
import model.projects.forms.ProjectAssignForm;
import model.projects.forms.ProjectCreationForm;
import model.users.Administrator;
import model.users.Developer;
import model.users.User;
import model.users.UserCategory;
import model.users.UserManager;

public class AssignProjectUseCaseTest {

	private BugTrap bugTrap;
	private ProjectManager projectManager;

	@Before
	public void setUp() throws Exception {
		bugTrap = new BugTrap();
		projectManager = bugTrap.getProjectManager();
	}

	@Test
	public void assignProgrammerToProjectTest() {
		Project project = projectManager.createProject("", "", new Date(), new Date(), 123, new ProjectTeam(), new Version(1, 0, 0));
		Developer dev = new Developer("", "", "", "");
		projectManager.assignToProject(project, dev, Role.PROGRAMMER);

		assertEquals(project.getTeam().getProgrammers().get(0), dev);
	}

	@Test
	public void assignLeadToProjectTest() {
		Project project = projectManager.createProject("", "", new Date(), new Date(), 123, new ProjectTeam(), new Version(1, 0, 0));
		Developer dev = new Developer("", "", "", "");
		projectManager.assignToProject(project, dev, Role.LEAD);

		assertEquals(project.getTeam().getLeadDeveloper(), dev);
	}

	@Test
	public void assignTesterToProjectTest() {
		Project project = projectManager.createProject("", "", new Date(), new Date(), 123, new ProjectTeam(), new Version(1, 0, 0));
		Developer dev = new Developer("", "", "", "");
		projectManager.assignToProject(project, dev, Role.LEAD);

		assertEquals(project.getTeam().getLeadDeveloper(), dev);
	}
}
