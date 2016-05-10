package tests;

import controllers.BugReportController;
import controllers.NotificationController;
import controllers.ProjectController;
import controllers.UserController;
import model.BugTrap;
import model.bugreports.IBugReport;
import model.bugreports.bugtag.BugTag;
import model.projects.IProject;
import model.projects.ISubsystem;
import model.projects.Version;
import model.users.Developer;
import model.users.IUser;
import model.users.Issuer;
import org.junit.Before;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

/**
 * Superclass for all tests for the BugTrap system.
 * This class implements the setUp method, so that tests always have a complete BugTrap system to be tested on.
 */
public class BugTrapTest {
    protected BugTrap bugTrap;
    protected BugReportController bugReportController;
    protected NotificationController notificationController;
    protected ProjectController projectController;
    protected UserController userController;

    protected IUser admin;
    protected Developer lead;
    protected Developer prog;
    protected Developer tester;
    protected Issuer issuer;

    protected IProject office;
    protected ISubsystem word;
    protected ISubsystem excel;
    protected ISubsystem powerpoint;

    protected ISubsystem wordArt;
    protected ISubsystem comicSans;
    protected ISubsystem clippy;

    protected IBugReport clippyBug;
    protected IBugReport wordBug;

    @Before
    public void setUp() {
        //Make System.
        bugTrap = new BugTrap();
        bugReportController = new BugReportController(bugTrap);
        notificationController = new NotificationController(bugTrap);
        projectController = new ProjectController(bugTrap);
        userController = new UserController(bugTrap);

        //Add Users.
        admin = bugTrap.getUserManager().createAdmin("Bill", "", "Gates", "ADMIN");
        lead = bugTrap.getUserManager().createDeveloper("Barack", "", "Obama", "LEAD");
        prog = bugTrap.getUserManager().createDeveloper("Edsger", "W.", "Dijkstra", "PROGRAMMER");
        tester = bugTrap.getUserManager().createDeveloper("Edsger", "W.", "Dijkstra", "TESTER");
        issuer = bugTrap.getUserManager().createIssuer("Geen", "", "Idee", "ISSUER");
        bugTrap.getUserManager().loginAs(admin);

        //Add Project, assign some people.
        bugTrap.getProjectManager().createProject("Office", "This project is huge. Lots of subsystems", new Date(1302), new Date(1302), 1234, lead, new Version(1, 0, 0));
        office = bugTrap.getProjectManager().getProjects().get(0);
        office.addProgrammer(prog);
        office.addTester(tester);
        //Add Subsystem.
        bugTrap.getProjectManager().createSubsystem("Word", "Word processor", office, office);
        word = bugTrap.getProjectManager().getSubsystemWithName("Word");
        bugTrap.getProjectManager().createSubsystem("Word Art", "Beautiful creations in text documents!", office, word);
        wordArt = bugTrap.getProjectManager().getSubsystemWithName("Word Art");
        bugTrap.getProjectManager().createSubsystem("Comic Sans", "We need an amazing font everybody loves!", office, word);
        comicSans = bugTrap.getProjectManager().getSubsystemWithName("Comic Sans");
        bugTrap.getProjectManager().createSubsystem("Clippy", "Annoying paperclip to make people use our software for longer periods of time", office, word);
        clippy = bugTrap.getProjectManager().getSubsystemWithName("Clippy");
        bugTrap.getProjectManager().createSubsystem("Excel", "Excellent software", office, office);
        excel = bugTrap.getProjectManager().getSubsystemWithName("Excel");
        bugTrap.getProjectManager().createSubsystem("PowerPoint", "Powerfully pointless", office, office);
        powerpoint = bugTrap.getProjectManager().getSubsystemWithName("PowerPoint");
        
        //Add BugReports.
        bugTrap.getUserManager().loginAs(lead);
        bugTrap.getBugReportManager().addBugReport("Clippy bug!", "Clippy only pops up once an hour. Should be more.", new Date(1303), clippy, lead, new ArrayList<>(), new ArrayList<>(), BugTag.NEW, 5);
        clippyBug = clippy.getAllBugReports().get(0);
        bugTrap.getBugReportManager().addComment(clippyBug, "Agreed! I propose once every 5 minutes!");
        bugTrap.getBugReportManager().addBugReport("Word crashes when Clippy pops up", "...", new Date(1305), word, lead, Arrays.asList(new IBugReport[] { clippyBug }), Arrays.asList(new IUser[] { prog }), BugTag.UNDERREVIEW, 7);
        wordBug = word.getAllBugReports().get(0);

        //Log off.
        bugTrap.getUserManager().logOff();
    }
}
