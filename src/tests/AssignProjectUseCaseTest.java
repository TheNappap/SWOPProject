package tests;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import model.BugTrap;
import model.projects.Project;
import model.projects.ProjectManager;
import model.projects.ProjectTeam;
import model.projects.Role;
import model.projects.Version;
import model.users.Developer;

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
