package ui;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import controllers.BugReportController;
import controllers.NotificationController;
import controllers.ProjectController;
import controllers.UserController;
import controllers.exceptions.UnauthorizedAccessException;
import model.BugTrap;
import model.bugreports.IBugReport;
import model.bugreports.TargetMilestone;
import model.bugreports.bugtag.BugTag;
import model.bugreports.comments.Comment;
import model.bugreports.comments.Commentable;
import model.bugreports.filters.FilterType;
import model.bugreports.forms.*;
import model.notifications.*;
import model.notifications.forms.RegisterNotificationForm;
import model.notifications.forms.ShowChronologicalNotificationForm;
import model.notifications.forms.UnregisterNotificationForm;
import model.projects.IProject;
import model.projects.ISubsystem;
import model.projects.ISystem;
import model.projects.Role;
import model.projects.Version;
import model.projects.forms.*;
import model.users.IUser;
import model.users.Issuer;

import static org.junit.Assert.fail;

public class Main {

	private static boolean quit;
	private static UserController userController;
	private static ProjectController projectController;
	private static BugReportController bugReportController;
	private static NotificationController notificationController;
	private static Scanner input;
	
	public static void main(String[] args) {
		// Initialize BugTrap
		init();
		
		// Initialize CLI
		quit = false;
		
		System.out.println("Welcome to BugTrap.");
		System.out.println("");
		
		input = new Scanner(System.in);
		
		while (!quit) {
			IUser user = userController.getLoggedInUser();
			if (user != null)
				System.out.print("[" + user.getUserName() + "] ");
			String line = input.nextLine();
			processCommand(line);
		}
		
		input.close();
		System.out.println("Goodbye.");
	}
	
	public static void init() {
		BugTrap bugTrap = new BugTrap();
		userController = new UserController(bugTrap);
		projectController = new ProjectController(bugTrap);
		bugReportController = new BugReportController(bugTrap);
		notificationController = new NotificationController(bugTrap);
		
		bugTrap.initialize();
	}

	public static void processCommand(String command) {
		String cmd = command.trim().toLowerCase();
		
		if (cmd.equals("help")) {
			help();
		} else if (cmd.equals("login")) {
			login();
		} else if (cmd.equals("exit")) {
			quit = true;
		} else if (cmd.equals("createproject")) {
			createProject();
		} else if (cmd.equals("updateproject")) {
			updateProject();
		} else if (cmd.equals("deleteproject")) {
			deleteProject();
		} else if (cmd.equals("showproject")) {
			showProject();
		} else if (cmd.equals("createsubsystem")) {
			createSubSystem();
		} else if (cmd.equals("createbugreport")) {
			createBugReport();
		} else if (cmd.equals("inspectbugreport")) {
			inspectBugReport();
		} else if (cmd.equals("createcomment")) {
			createComment();
		} else if (cmd.equals("assignproject")) {
			assignToProject();
		} else if (cmd.equals("assignbugreport")) {
			assignToBugReport();
		} else if (cmd.equals("updatebugreport")) {
			updateBugReport();
		} else if (cmd.equals("shownotifications")) {
			showNotifications();
		} else if (cmd.equals("registernotification")) {
			registerNotification();
		} else if (cmd.equals("unregisternotification")) {
			unregisterNotification();
		} else if (cmd.equals("proposetest")) {
			proposeTest();
		} else if (cmd.equals("proposepatch")) {
			proposePatch();
		} else if (cmd.equals("declareachievedmilestone")) {
			declareAchievedMilestone();
		}
		else
			System.out.println("Command not recognized.");
	}
	
	public static void help() {
		System.out.println("Available commands: ");
		
		System.out.println("exit : End the application.");
		System.out.println("login : Log into the system.");
		System.out.println("createproject : Create a Project.");
		System.out.println("deleteproject : Remove a Project from the system.");
		System.out.println("showproject : Show the details of a Project.");
		System.out.println("createsubsystem : Create a new Subsystem for a Project.");
		System.out.println("createbugreport : Create a new BugReport for a Subsystem.");
		System.out.println("inspectbugreport : Show the details of a BugReport.");
		System.out.println("createcomment : Write a comment about a BugReport or some other comment." );
		System.out.println("assignproject : Assign a Developer to a Project.");
		System.out.println("assignbugreport : Assign a Developer to a BugReport.");
		System.out.println("updatebugreport : Update the details of a BugReport.");
		System.out.println("shownotifications : Show a list of notifications.");
		System.out.println("registernotification : Register for a notification.");
		System.out.println("unregisternotification : Unregister from a notification.");
		System.out.println("proposetest : Propose a test.");
		System.out.println("proposepatch : Propose a patch.");
		System.out.println("declareachievedmilestone : Declare an achieved milestone.");
	}

	// -- Use cases -- //
	public static void login() {
		boolean valid = false;
		int category = 0;
		List<IUser> users = new ArrayList<IUser>();
		IUser selectedUser = null;
		while (!valid) {
			valid = true;
			System.out.println("Select a user cateogry by entering the number: ");
			System.out.println(" 1. Administrator");
			System.out.println(" 2. Issuer");
			System.out.println(" 3. Developer");
			category = input.nextInt();
			input.nextLine();
					
			switch (category) {
				case 1:
					users = userController.getAdmins();
					break;
				case 2:
					users = userController.getIssuers();
					break;
				case 3:
					users = userController.getDevelopers();
					break;
				default:
					valid = false;
					continue;
			}
		}
		
		selectedUser = selectUser(users);
		
		String greeting = userController.loginAs(selectedUser);
		System.out.println(greeting);
	}

	public static void createProject() {
		boolean valid = false;
		int selected = 0;
		while (!valid) {
			System.out.println("Please indicate wheter you would like to: ");
			System.out.println(" 1. Fork an existing project");
			System.out.println(" 2. Create a new project");
			selected = input.nextInt();
			input.nextLine();

			if (selected > 0 && selected < 3)
				valid = true;
		}

		if (selected == 1)
			createForkedProject();
		if (selected == 2)
			createNewProject();;
	}

	public static void createForkedProject() {
		ProjectForkForm form;
		IProject project;
		try {
			form = projectController.getProjectForkForm(); 
			project = selectProject(projectController.getProjectList());
			form.setProject(project);
		} catch (UnauthorizedAccessException e) {
			System.out.println(e.getMessage());
			return;
		}

		System.out.println("Enter the budget estimate for the project:");
		form.setBudgetEstimate(input.nextDouble());
		input.nextLine();

		boolean valid = false;
		while (!valid) {
			try {
				System.out.println("Enter the start date for the project (dd/mm/yyyy):");
				form.setStartDate((new SimpleDateFormat("dd/MM/yyyy")).parse(input.nextLine()));
				valid = true;
			} catch (Exception e) { }
		}

		valid = false;
		while (!valid) {
			try {
				System.out.println("Enter the version number for the project:");
				String vNumber = input.nextLine();
				int major = Integer.parseInt(vNumber.split(".")[0]);
				int minor = Integer.parseInt(vNumber.split(".")[1]);
				int revision = Integer.parseInt(vNumber.split(".")[2]);
				form.setVersion(new Version(major, minor, revision));
			} catch (IllegalArgumentException ie) { System.out.println(ie.getMessage()); }
			catch (Exception e) { }
		}

		try {
			projectController.forkProject(form);
			System.out.println("Project is forked.");			
		} catch (UnauthorizedAccessException e) {
			System.out.println(e.getMessage());			
		}
	}

	public static void createNewProject() {
		ProjectCreationForm form;
		try {
			 form = projectController.getProjectCreationForm();
		} catch (UnauthorizedAccessException e) {
			System.out.println(e.getMessage());
			return;
		}

		System.out.println("Enter the name of the project:");
		form.setName(input.nextLine());
		System.out.println("Enter the description of the project:");
		form.setDescription(input.nextLine());
		System.out.println("Enter the budget estimate for the project:");
		form.setBudgetEstimate(input.nextDouble());
		input.nextLine();
		
		boolean valid = false;
		while (!valid) {
			try {
				System.out.println("Enter the start date for the project (dd/mm/yyyy):");
				form.setStartDate((new SimpleDateFormat("dd/MM/yyyy")).parse(input.nextLine()));
				valid = true;
			} catch (Exception e) { }
		}

		valid = false;
		while (!valid) {
			try {
				IUser lead = selectUser(userController.getDevelopers());
				form.setLeadDeveloper(lead);
			} catch (IllegalArgumentException e) { }
		}
		
		try {
			projectController.createProject(form);
			System.out.println("Project is created.");
		} catch (UnauthorizedAccessException e) {
			System.out.println(e.getMessage());
		}	
	}
	
	public static void updateProject() {
		ProjectUpdateForm form;
		try {
			 form = projectController.getProjectUpdateForm();
		} catch (UnauthorizedAccessException e) {
			System.out.println(e.getMessage());
			return;
		}
		
		IProject project = null;
		project = selectProject(projectController.getProjectList());
		form.setProject(project);
		
		System.out.println("Enter the name of the project:");
		form.setName(input.nextLine());
		System.out.println("Enter the description of the project:");
		form.setDescription(input.nextLine());
		System.out.println("Enter the budget estimate for the project:");
		form.setBudgetEstimate(input.nextDouble());
		
		boolean valid = false;
		while (!valid) {
			try {
				System.out.println("Enter the start date for the project (dd/mm/yyyy):");
				form.setStartDate((new SimpleDateFormat("dd/MM/yyyy")).parse(input.nextLine()));
				valid = true;
			} catch (Exception e) { }
		}
		
		try {
			projectController.updateProject(form);
			System.out.println("Project is updated.");
		} catch (UnauthorizedAccessException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static void assignToProject() {
		ProjectAssignForm form = null;
		try {
			form = projectController.getProjectAssignForm();
		} catch (UnauthorizedAccessException e) {
			System.out.println(e.getMessage());
		}
		
		IProject project = null;
		try {
			project = selectProject(projectController.getProjectsForSignedInLeadDeveloper());
		} catch (UnauthorizedAccessException e) {
			System.out.println(e.getMessage());
			return;
		} catch (UnsupportedOperationException e) {
			System.out.println("You are not leading any projects!");
			return;
		}
		form.setProject(project);
		form.setDeveloper(selectUser(userController.getDevelopers()));
		Role role = selectRole();
		form.setRole(role);
		
		try {
			projectController.assignToProject(form);
			System.out.println("Developer is assigned.");
		} catch (UnauthorizedAccessException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void deleteProject() {
		ProjectDeleteForm form;
		try {
			form = projectController.getProjectDeleteForm();
		} catch (UnauthorizedAccessException e) {
			System.out.println(e.getMessage());
			return;
		}
		
		IProject project = null;
		project = selectProject(projectController.getProjectList());
		form.setProject(project);
		
		try {
			projectController.deleteProject(form);
			System.out.println("Project is deleted.");
		} catch (UnauthorizedAccessException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static void showProject() {
		IProject project = null;

		project = selectProject(projectController.getProjectList());
		System.out.println(" -- " + project.getName() + " -- ");
		System.out.println(" Description: " + project.getDescription());
		System.out.println(" Budget estimate: " + project.getBudgetEstimate());
		System.out.println(" Creation date: " + project.getCreationDate().toString());
		System.out.println(" Start date: " + project.getStartDate().toString());
		System.out.println(" Version: " + project.getVersion());

		for (ISubsystem system : project.getAllDirectOrIndirectSubsystems()) {
			System.out.println(" -- " + system.getName() + " -- ");
			System.out.println(" Description: " + system.getDescription());
		}
	}
	
	public static void createSubSystem() {
		SubsystemCreationForm form;  
		try {  
			form = projectController.getSubsystemCreationForm();  
			ArrayList<ISystem> allSystems = new ArrayList<ISystem>();
			for (IProject project : projectController.getProjectList()) {
				allSystems.add(project);  
				for (ISystem sys : project.getAllDirectOrIndirectSubsystems()) {
					allSystems.add(sys);  
				}  
			}  
		    		  
			ISystem selectedSystem = selectSystem(allSystems);
			form.setParent(selectedSystem);  
				  
			System.out.println("Enter the name of the subsystem:");  
		 	form.setName(input.nextLine());  
		 	System.out.println("Enter the description of the subsystem:");  
		 	form.setDescription(input.nextLine());  
		} catch (UnauthorizedAccessException e) {  
			System.out.println(e.getMessage());  
			return;  
		}  
		
		try {
			projectController.createSubsystem(form);
	 	System.out.print("Subsystem is created.");
		} catch (UnauthorizedAccessException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static void createBugReport() {
		BugReportCreationForm form = null;
		try {
			form = bugReportController.getBugReportCreationForm();
			IProject chosenProject = selectProject(projectController.getProjectList());
			ISubsystem chosenSubsystem = selectSubsystem(chosenProject.getSubsystems());
			
			form.setIssuer((Issuer) userController.getLoggedInUser());
			form.setSubsystem(chosenSubsystem);
			System.out.println("Enter the title of the BugReport:");
			form.setTitle(input.nextLine());
			System.out.println("Enter a description:");
			form.setDescription(input.nextLine());

			System.out.println("Enter a stack trace: (optional, press enter to skip)");
			form.setStackTrace(input.nextLine());
			System.out.println("Enter an error message: (optional, press enter to skip)");
			form.setErrorMessage(input.nextLine());
			System.out.println("Enter a procedure to reproduce the bug: (optional, press enter to skip");
			form.setReproduction(input.nextLine());
			System.out.println("Enter a target milestone: (optional, press enter to skip)");
			form.setTargetMilestone(selectTargetMilestone());

			List<IBugReport> selectedDependencies = selectBugReports(chosenProject.getBugReports());
			form.setDependsOn(selectedDependencies);
		} catch (UnauthorizedAccessException e) {
			System.out.println(e.getMessage());
			return;
		}
		
		try {
			bugReportController.createBugReport(form);
			System.out.println("The bug report is created.");
		} catch (UnauthorizedAccessException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static void inspectBugReport() {
		IBugReport bugReport;
		bugReport = selectBugReport();
		if (bugReport == null)
			return;
		System.out.println(" -- " + bugReport.getTitle() + " -- ");
		System.out.println(" Description: " + bugReport.getDescription());
		System.out.println(" BugTag: " + bugReport.getBugTag().toString());
		System.out.println(" Creation Date: " + bugReport.getCreationDate());
		System.out.println(" Issued by: " + bugReport.getIssuedBy().getUserName());
		System.out.println(" Subsystem: " + bugReport.getSubsystem().getName());
		System.out.println(" Assignees: ");
		for (IUser dev : bugReport.getAssignees())
			System.out.println(" - " + dev.getUserName());
		System.out.println(" Depends on: ");
		for (IBugReport bug : bugReport.getDependsOn())
			System.out.println(" - " + bug.getTitle());
		System.out.println(" Comments: ");
		
		printComments(bugReport.getComments());
	}
	
	public static void createComment() {
		try {
			CommentCreationForm form = bugReportController.getCommentCreationForm();
			
			IBugReport chosenBugReport = selectBugReport();
			boolean valid = false;
			while (!valid) {
				System.out.println("Comment directly on this bug report or on one of its comments?");
				System.out.println("1. Directly");
				System.out.println("2. Comments");
				int selected = input.nextInt();
				input.nextLine();
				if (selected == 1)
					form.setCommentable(chosenBugReport);
				else if (selected == 2) {
					form.setCommentable(selectComment(chosenBugReport, null));
				} else 
					continue; 
				
				System.out.println("Enter your comment:");
				form.setText(input.nextLine());
				
				bugReportController.createComment(form);
				System.out.println("Comment is created.");
				valid = true;
			}
			
		} catch (UnauthorizedAccessException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static void assignToBugReport() {
		BugReportAssignForm form = null;
		IBugReport report = null;
		IUser dev = null;
		try {
			form = bugReportController.getBugReportAssignForm();
			report = selectBugReport();
			dev = selectUser(userController.getDevelopers());
		} catch (UnauthorizedAccessException e) {
			System.out.println(e.getMessage());
			return;
		}
		
		form.setBugReport(report);
		form.setDeveloper(dev);
		
		try {
			bugReportController.assignToBugReport(form);
			System.out.println("Developer is assigned to bug report.");
		} catch (UnauthorizedAccessException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static void updateBugReport() {
		BugReportUpdateForm form = null;
		try {
			form = bugReportController.getBugReportUpdateForm();
		} catch (UnauthorizedAccessException e) {
			System.out.println(e.getMessage());
			return;
		}
		
		IBugReport selected = selectBugReport();
		form.setBugReport(selected);
		form.setBugTag(selectBugTag());
		
		try {
			bugReportController.updateBugReport(form);
			System.out.println("Bug report is updated.");
		} catch (UnauthorizedAccessException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void showNotifications() {
		ShowChronologicalNotificationForm form = null;
		try {
			form = notificationController.getShowChronologicalNotificationForm();
		} catch (UnauthorizedAccessException e) {
			System.out.println(e.getMessage());
			return;
		}

		System.out.println("Enter the number of notifications you want to see: ");
		int numberOfNotifications = input.nextInt();
		input.nextLine();
		form.setNbOfNotifications(numberOfNotifications);

		List<INotification> reqNotifications = null;
		try {
			reqNotifications = notificationController.showNotifications(form);
		} catch (UnauthorizedAccessException e) {
			System.out.println(e.getMessage());
			return;
		}

		for (INotification n : reqNotifications)
			System.out.println((n.isRead() ? "" : "[new] ") + n.getText());
	}

	public static void registerNotification() {
		RegisterNotificationForm form = null;
		try {
			form = notificationController.getRegisterNotificationForm();
		} catch (UnauthorizedAccessException e) {
			System.out.println(e.getMessage());
			return;
		}

		int selection = 0;
		while (selection == 0) {
			System.out.println("Do you want to register for: ");
			System.out.println(" 1. Project");
			System.out.println(" 2. Subsystem");
			System.out.println(" 3. Bug report");
			selection = input.nextInt();
			input.nextLine();
			if (selection < 1 || selection > 3)
				selection = 0;
		}

		Observable observable = null;
		switch (selection) {
			case 1:
				observable = selectProject(projectController.getProjectList());
				break;
			case 2:
				List<IProject> ps = projectController.getProjectList();
				IProject p = selectProject(ps);
				observable = selectSubsystem(p.getAllDirectOrIndirectSubsystems());
				break;
			case 3:
				observable = selectBugReport();
				break;
		}
		form.setObservable(observable);

		form.setNotificationType(selectNotificationType());
		if (form.getRegistrationType() == NotificationType.BUGREPORT_SPECIFIC_TAG)
			form.setTag(selectBugTag());

		try {
			notificationController.registerNotification(form);
		} catch (UnauthorizedAccessException e) {
			System.out.println(e.getMessage());
			return;
		}

		System.out.println("You are registered for this notification.");
	}

	public static void unregisterNotification() {
		UnregisterNotificationForm form = null;
		try {
			form = notificationController.getUnregisterNotificationForm();
		} catch(UnauthorizedAccessException e) {
			System.out.println(e.getMessage());
			return;
		}

		List<IRegistration> registrations = null;
		try {
			registrations = notificationController.getRegistrations();
		} catch (UnauthorizedAccessException e) {
			System.out.println(e.getMessage());
			return;
		}

		form.setRegistration(selectRegistration(registrations));

		try {
			notificationController.unregisterNotification(form);
		} catch (UnauthorizedAccessException e) {
			System.out.println(e.getMessage());
			return;
		}

		System.out.println("You are now unregistered from this notification.");
	}

	public static void proposeTest() {
		ProposeTestForm form = null;
		try {
			form = bugReportController.getProposeTestForm();
		} catch (UnauthorizedAccessException e) {
			System.out.println(e.getMessage());
			return;
		}

		IBugReport report = selectBugReport();
		form.setBugReport(report);

		System.out.println("Enter the test: ");
		form.setTest(input.nextLine());

		try {
			bugReportController.proposeTest(form);
		} catch (UnauthorizedAccessException e) {
			System.out.println(e.getMessage());
			return;
		}

		System.out.println("Test successfully proposed.");
	}

	public static void proposePatch() {
		ProposePatchForm form = null;
		try {
			form = bugReportController.getProposePatchForm();
		} catch (UnauthorizedAccessException e) {
			System.out.println(e.getMessage());
			return;
		}

		IBugReport report = selectBugReport();
		form.setBugReport(report);

		System.out.println("Enter the patch: ");
		form.setPatch(input.nextLine());

		try {
			bugReportController.proposePatch(form);
		} catch (UnauthorizedAccessException e) {
			System.out.println(e.getMessage());
			return;
		}

		System.out.println("Patch successfully proposed.");
	}

	public static void declareAchievedMilestone() {
		DeclareAchievedMilestoneForm form = null;
		try {
			form = projectController.getDeclareAchievedMilestoneForm();
		} catch (UnauthorizedAccessException e) {
			System.out.println(e.getMessage());
			return;
		}

		ISystem sys = null;
		IProject project = selectProject(projectController.getProjectList());

		boolean valid = false;
		while (!valid) {
			System.out.println("Do you want to declare a milestone for the entire project? (y/n)");
			String raw = input.nextLine();
			if (raw.equals("y")) {
				sys = project;
				valid = true;
			} else if (raw.equals("n")) {
				sys = selectSubsystem(project.getAllDirectOrIndirectSubsystems());
				valid = true;

				System.out.println("Current milestone: ");
				System.out.println(" " + sys.getAchievedMilestone());
			}
		}
		form.setSystem(sys);

		System.out.println("Enter the new milestone number:");
		form.setNumbers(enterMilestoneNumbers());

		try {
			projectController.declareAchievedMilestone(form);
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
			return;
		} catch (UnauthorizedAccessException e) {
			System.out.println(e.getMessage());
			return;
		}

		System.out.println("Milestone was declared.");
	}

	// -- Printing --
	private static void printComments(List<Comment> comments) {
		for (int index = 0; index < comments.size(); index++) {
			String level = " " + (index+1) + ".";
			System.out.println(level + comments.get(index).getText());
			printComments(comments.get(index).getComments(), level);
		}
	}

	private static void printComments(List<Comment> comments, String string) {
		for (int index = 0; index < comments.size(); index++) {
			String level = string + (index+1) + ".";
			System.out.println(level + comments.get(index).getText());
			printComments(comments.get(index).getComments(), level);
		}
	}

	// -- Selectors -- //
	private static IUser selectUser(List<IUser> users) {
		while (true) {
			System.out.println("Select a user by entering the username: ");
			for (IUser user : users)
				System.out.println(user.getUserName() + " (" + user.getFirstName() + " " + user.getMiddleName() + " " + user.getLastName() + ")");

			String name = input.nextLine();
			for (IUser user : users) {
				if (user.getUserName().equals(name))
					return user;
			}
		}
	}

	private static IBugReport selectBugReport() {
		FilterType type = selectFilterType();
		System.out.println("Enter the search parameter: ");
		String parameter = input.nextLine();
		List<IBugReport> filtered;
		try {
			filtered = bugReportController.getOrderedList(new FilterType[]{type}, new String[]{parameter});
			while (true) {
				System.out.println("Select a bugreport by entering its number: ");
				int number = 1;
				for (IBugReport bugReport : filtered) {
					System.out.println(number + ". " + bugReport.getTitle());
					number++;
				}
				
				int selected = input.nextInt();
				input.nextLine();
				if (selected <= filtered.size())
					return filtered.get(selected - 1);
			}
		} catch (UnauthorizedAccessException e) {
			System.out.println(e.getMessage());
		}
		
		return null;
	}
	
	private static List<IBugReport> selectBugReports(List<IBugReport> reports) {
		while (true) {
			ArrayList<IBugReport> selected = new ArrayList<IBugReport>();
			System.out.println("Select bugreports by entering its numbers separated by commas: ");
			int number = 1;
			for (IBugReport bugReport : reports) {
				System.out.println(number + ". " + bugReport.getTitle());
				number++;
			}
			
			try {
				String line = input.nextLine();
				if (line == null || line.length() == 0)
					return selected;

				String[] rawInput = line.split(",");
				for (String raw : rawInput)
					selected.add(reports.get(Integer.parseInt(raw.trim()) - 1));
				return selected;
				
			} catch (Exception e) {
			}
		}
	}
		
	private static Commentable selectComment(IBugReport report, Comment currentlySelected) {
		while (true) {			
			int number = 1;
			if (currentlySelected == null) {
				System.out.println("Select a comment by entering its number: ");
				for (Comment c : report.getComments()) {
					System.out.println(number + ". " + c.getText());
					number++;
				}
			} else {
				System.out.println("Select the current comment or continue on one of its replies: ");
				System.out.println("Current comment: " + currentlySelected.getText());
				System.out.println("0. Select current comment.");
				for (Comment c : currentlySelected.getComments()) {
					System.out.println(number + ". " + c.getText());
					number++;
				}
			}
			
			int selected = input.nextInt();
			input.nextLine();
			if (selected == 0 && currentlySelected != null)
				return currentlySelected;
			
			if (selected > 0 && currentlySelected != null && selected <= currentlySelected.getComments().size())
				currentlySelected = currentlySelected.getComments().get(selected - 1);
			
			else if (selected > 0 && report != null && selected <= report.getComments().size())
				currentlySelected = report.getComments().get(selected - 1);
		}
	}
	
	private static BugTag selectBugTag() {
		while (true) {
			System.out.println("Select a bug tag by entering its number");
			
			int number = 1;

			for (BugTag tag : BugTag.values()){
				System.out.println(number + ". " + tag);
				number++;
			}
			
			int selected = input.nextInt();
			input.nextLine();
			if (selected <= BugTag.values().length)
				return BugTag.values()[selected - 1];
		}
	}

	private static Role selectRole() {
		while (true) {
			System.out.println("Select a role by entering its numner");
			
			int number = 1;
			for (Role role : Role.values()) {
				System.out.println(number + ". " + role.toString());
				number++;
			}
			
			int selected = input.nextInt();
			input.nextLine();
			if (selected <= Role.values().length)
				return Role.values()[selected-1];
		}
	}

	private static FilterType selectFilterType() {
		while(true) {
			System.out.println("Select a search mode by entering its number: ");
			int number = 1;
			for (FilterType type : FilterType.values()) {
				System.out.println(number + ". " + type.toString());
				number++;
			}
			
			int selected = input.nextInt();
			input.nextLine();
			if (selected <= FilterType.values().length)
				return FilterType.values()[selected - 1];
		}
	}
	
	private static IProject selectProject(List<IProject> projects) {
		while (true) {
			try {
				System.out.println("Select a project by entering its number: ");
				int number = 1;
				for (IProject project : projects) {
					System.out.println(number + ". " + project.getName());
					number++;
				}

				int selected = input.nextInt();
				input.nextLine();
				if (selected <= projects.size())
					return projects.get(selected - 1);
			} catch (Exception e) {
				System.out.println("Invalid input.");
			}
		}
	}

	private static ISystem selectSystem(List<ISystem> systems) {
		while (true) {
			System.out.println("Select a project or subsystem by entering its number: ");
			int number = 1;
			for (ISystem sys : systems) {
				System.out.println(number + ". " + sys.getName());
				number++;
			}
			
			int selected = input.nextInt();
			input.nextLine();
			if (selected <= systems.size())
				return systems.get(selected - 1);
		}
	}
	
	private static ISubsystem selectSubsystem(List<ISubsystem> subsystems) {
		while (true) {
			System.out.println("Select a subsystem by entering its number: ");
			int number = 1;
			for (ISubsystem sub : subsystems) {
				System.out.println(number + ". " + sub.getName());
				number++;
			}
			
			int selected = input.nextInt();
			input.nextLine();
			if (selected <= subsystems.size())
				return subsystems.get(selected - 1);
		}
	}

	private static TargetMilestone selectTargetMilestone() {
		List<Integer> numbers = enterMilestoneNumbers();
		return new TargetMilestone(numbers);
	}

	private static List<Integer> enterMilestoneNumbers() {
		String inputLine = input.nextLine();

		if (inputLine == null || inputLine.equals("")) return new ArrayList<>();

		if (inputLine.startsWith("M"))
			inputLine = inputLine.substring(1);
		//Split input by "."
		String[] splitted = inputLine.split("\\.");

		List<Integer> numbers = new ArrayList<Integer>();
		for (String s : splitted) numbers.add(Integer.valueOf(s));

		return numbers;
	}

	private static NotificationType selectNotificationType() {
		while (true) {
			System.out.println("Select a registration type by entering its numner");

			int number = 1;
			for (NotificationType type : NotificationType.values()) {
				System.out.println(number + ". " + type.toString());
				number++;
			}

			int selected = input.nextInt();
			input.nextLine();
			if (selected <= NotificationType.values().length)
				return NotificationType.values()[selected-1];
		}
	}

	private static IRegistration selectRegistration(List<IRegistration> registrations) {
		while (true) {
			try {
				System.out.println("Select a registration by entering its number: ");
				int number = 1;
				for (IRegistration registration : registrations) {
					System.out.println(number + ". " + registration.getNotificationType() + " " + registration.getObserves());
					number++;
				}

				int selected = input.nextInt();
				input.nextLine();
				if (selected <= registrations.size())
					return registrations.get(selected - 1);
			} catch (Exception e) {
				System.out.println("Invalid input.");
			}
		}
	}
}
