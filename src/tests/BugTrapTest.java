package tests;

import java.util.ArrayList;
import java.util.Date;

import org.junit.Before;

import controllers.BugReportController;
import controllers.NotificationController;
import controllers.ProjectController;
import controllers.UserController;
import model.BugTrap;
import model.bugreports.IBugReport;
import model.bugreports.bugtag.BugTag;
import model.projects.IProject;
import model.projects.ISubsystem;
import model.projects.Project;
import model.projects.Subsystem;
import model.projects.Version;
import model.users.Developer;
import model.users.IUser;
import model.users.Issuer;

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
    protected ISubsystem excelTable;

    protected IBugReport clippyBug;
    protected IBugReport wordBug;
    protected IBugReport wordArtBug;
    protected IBugReport excelBug;

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
        ((Project) office).createSubsystem("Word", "Word processor");
        word = bugTrap.getProjectManager().getSubsystemWithName("Word");
        ((Subsystem) word).createSubsystem("Word Art", "Beautiful creations in text documents!");
        wordArt = bugTrap.getProjectManager().getSubsystemWithName("Word Art");
        ((Subsystem) word).createSubsystem("Comic Sans", "We need an amazing font everybody loves!");
        comicSans = bugTrap.getProjectManager().getSubsystemWithName("Comic Sans");
        ((Subsystem) word).createSubsystem("Clippy", "Annoying paperclip to make people use our software for longer periods of time");
        clippy = bugTrap.getProjectManager().getSubsystemWithName("Clippy");
        ((Project) office).createSubsystem("Excel", "Excellent software");
        excel = bugTrap.getProjectManager().getSubsystemWithName("Excel");
        ((Subsystem) excel).createSubsystem("ExcelTable", "Excellent Table");
        excelTable = bugTrap.getProjectManager().getSubsystemWithName("ExcelTable");
        ((Project) office).createSubsystem("PowerPoint", "Powerfully pointless");
        powerpoint = bugTrap.getProjectManager().getSubsystemWithName("PowerPoint");
        
        //Add BugReports.
        bugTrap.getUserManager().loginAs(lead);
        bugTrap.getBugReportManager().addBugReport("Clippy bug!", "Clippy only pops up once an hour. Should be more.", new Date(1303), clippy, lead, new ArrayList<>(), new ArrayList<>(), BugTag.NEW, null, 5);
        clippyBug = clippy.getAllBugReports().get(0);
        bugTrap.getBugReportManager().addComment(clippyBug, "Agreed! I propose once every 5 minutes!");
        ArrayList<IUser> assignees = new ArrayList<>();
        assignees.add(prog);
        ArrayList<IBugReport> dependencies = new ArrayList<>();
        dependencies.add(clippyBug);
        bugTrap.getBugReportManager().addBugReport("Word crashes", "As soon as Clippy pops up...", new Date(1305), word, lead, dependencies, assignees, BugTag.UNDERREVIEW, null, 7);
        wordBug = word.getAllBugReports().get(0);
        dependencies = new ArrayList<>();
        bugTrap.getBugReportManager().addBugReport("WordArt is not working", "When using Comic Sans, the Word Art does not work.", new Date(1310), wordArt, issuer, dependencies, assignees, BugTag.ASSIGNED, null, 3);
        wordArtBug = wordArt.getAllBugReports().get(0);
        bugTrap.getBugReportManager().addBugReport("Excel does weird stuff", "...", new Date(1305), excel, lead, new ArrayList<>(), assignees, BugTag.RESOLVED, null, 3);
        excelBug = excel.getAllBugReports().get(0);

        //Log off.
        bugTrap.getUserManager().logOff();
    }
}
