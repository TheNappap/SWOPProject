package tests;


import model.BugTrap;
import model.projects.*;
import model.users.Developer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

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

		Assert.assertEquals(project.getTeam().getProgrammers().get(0), dev);
	}

	@Test
	public void assignLeadToProjectTest() {
		Project project = projectManager.createProject("", "", new Date(), new Date(), 123, new ProjectTeam(), new Version(1, 0, 0));
		Developer dev = new Developer("", "", "", "");
		projectManager.assignToProject(project, dev, Role.LEAD);

		Assert.assertEquals(project.getTeam().getLeadDeveloper(), dev);
	}

	@Test
	public void assignTesterToProjectTest() {
		Project project = projectManager.createProject("", "", new Date(), new Date(), 123, new ProjectTeam(), new Version(1, 0, 0));
		Developer dev = new Developer("", "", "", "");
		projectManager.assignToProject(project, dev, Role.LEAD);

		Assert.assertEquals(project.getTeam().getLeadDeveloper(), dev);
	}
}
