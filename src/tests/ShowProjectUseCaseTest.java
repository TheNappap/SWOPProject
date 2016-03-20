package tests;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import model.BugTrap;
import model.projects.Project;
import model.projects.ProjectTeam;
import model.projects.Subsystem;
import model.projects.Version;

public class ShowProjectUseCaseTest {
	
	private BugTrap bugTrap;

	@Before
	public void setUp() throws Exception {
		bugTrap = new BugTrap();
		
		//add user
		bugTrap.getUserManager().createDeveloper("", "", "", "DEV");
		bugTrap.getUserManager().createAdmin("", "", "", "ADMIN");
		
		bugTrap.getProjectManager().createProject("name", "description", new Date(1302), new Date(1302), 1234, new ProjectTeam(), new Version(1, 0, 0));
		
	}

	@Test
	public void showProjectTest() {
		//step 1
		//user indicates he wants to inspect a project
		//step 2
		List<Project> list = bugTrap.getProjectManager().getProjects();
		//step 3
		Project project = list.get(0);
		//step 4
		ArrayList<Subsystem> subsystems = project.getSubsystems();
		
		assertEquals(project.getName(), "name");
		assertEquals(project.getDescription(), "description");
		assertEquals(project.getStartDate(), new Date(1302));
		assertEquals(1234, project.getBudgetEstimate(), 0.01);
		assertEquals(project.getVersion(), new Version(1, 0, 0));
		Assert.assertEquals(0, subsystems.size());
	}

}
