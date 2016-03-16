package tests;

import model.BugTrap;
import model.projects.Project;
import model.projects.ProjectManager;
import model.projects.ProjectTeam;
import model.projects.Version;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class CreateProjectUseCaseTest {

	private BugTrap bugTrap;
	private ProjectManager projectManager;

	@Before
	public void setUp() throws Exception {
		bugTrap = new BugTrap();
		projectManager = bugTrap.getProjectManager();
	}

	@SuppressWarnings("deprecation")
	@Test
	public void createNewProjectTest() {
		projectManager.createProject("name", "descr", new Date(2003, 4, 2), new Date(2005, 2, 12), 1234, new ProjectTeam(), new Version(1, 0, 0));

		Project p = projectManager.getProjects().get(0);
		assertEquals(p.getName(), "name");
		assertEquals(p.getDescription(), "descr");
		assertEquals(p.getCreationDate(), new Date(2003, 4, 2));
		assertEquals(p.getStartDate(), new Date(2005, 2, 12));
		assertEquals(p.getTeam(), new ProjectTeam());
		assertEquals(p.getVersion(), new Version(1, 0, 0));
	}

	@SuppressWarnings("deprecation")
	@Test
	public void createForkProjectTest() {
		Project project = projectManager.createProject("name", "descr", new Date(2003, 4, 2), new Date(2005, 2, 12), 1234, new ProjectTeam(), new Version(1, 0, 0));

		Project fork = projectManager.createFork(project, 346, new Version(2, 0, 1), new Date(2010, 3, 21));
		assertEquals(fork.getName(), project.getName());
		assertEquals(fork.getDescription(), project.getDescription());
		assertEquals(fork.getTeam(), project.getTeam());
		assertEquals(fork.getVersion(), new Version(2, 0, 1));
		assertEquals(fork.getCreationDate(), project.getCreationDate());
		assertEquals(fork.getStartDate(), new Date(2010, 3, 21));
		assertEquals(fork.getBudgetEstimate(), 346, 0.01);
	}
}
