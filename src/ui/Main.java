package ui;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import controllers.BugReportController;
import controllers.ProjectController;
import controllers.UserController;
import controllers.exceptions.UnauthorizedAccessException;
import model.BugTrap;
import model.bugreports.BugReport;
import model.bugreports.BugTag;
import model.bugreports.comments.Comment;
import model.bugreports.comments.InitialComment;
import model.bugreports.comments.ReplyComment;
import model.bugreports.filters.FilterType;
import model.bugreports.forms.BugReportAssignForm;
import model.bugreports.forms.BugReportCreationForm;
import model.bugreports.forms.BugReportUpdateForm;
import model.bugreports.forms.CommentCreationForm;
import model.projects.Project;
import model.projects.Role;
import model.projects.Subsystem;
import model.projects.forms.ProjectAssignForm;
import model.projects.forms.ProjectCreationForm;
import model.projects.forms.ProjectDeleteForm;
import model.projects.forms.ProjectUpdateForm;
import model.projects.forms.SubsystemCreationForm;
import model.users.Administrator;
import model.users.Developer;
import model.users.Issuer;
import model.users.User;
import model.users.UserCategory;
import model.users.UserManager;

public class Main {

	private static boolean quit;
	private static BugTrap bugTrap;
	private static UserController userController;
	private static ProjectController projectController;
	private static BugReportController bugReportController; 
	private static Scanner input;
	
	public static void main(String[] args) {
		// Initialize BugTrap
		try {
			init();
		} catch (UnauthorizedAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Initialize CLI
		quit = false;
		
		System.out.println("Welcome to BugTrap.");
		System.out.println("");
		System.out.println("Type help to see a list of all possible commands.");
		
		input = new Scanner(System.in);
		
		while (!quit) {
			User user = userController.getLoggedInUser();
			if (user != null)
				System.out.print("[" + user.getUserName() + "] ");
			String line = input.nextLine();
			processCommand(line);
		}
		
		input.close();
		System.out.println("Goodbye.");
	}
	
	public static void init() throws UnauthorizedAccessException {
		bugTrap = new BugTrap();
		userController = new UserController(bugTrap);
		projectController = new ProjectController(bugTrap);
		bugReportController = new BugReportController(bugTrap);
				
		// Create users
		UserManager userManager = (UserManager)bugTrap.getUserDAO();
		userManager.createUser(UserCategory.ADMIN, "Frederick", "Sam", "Curtis", "curt");
		Administrator curt = (Administrator) userManager.getUserList().get(0);
		userManager.createUser(UserCategory.ISSUER, "John", "", "Doctor", "doc");
		Issuer doc = (Issuer) userManager.getUserList().get(1);
		userManager.createUser(UserCategory.ISSUER, "Charles", "Arnold", "Berg", "charlie");
		Issuer charlie = (Issuer) userManager.getUserList().get(2);
		userManager.createUser(UserCategory.DEVELOPER, "Joseph", "", "Mays", "major");
		Developer major = (Developer) userManager.getUserList().get(3);
		userManager.createUser(UserCategory.DEVELOPER, "Maria", "", "Carney", "maria");
		Developer maria = (Developer) userManager.getUserList().get(4);
		
		// Login as admin to do the initialisation
		userController.loginAs(curt);
		
		// ProjectA
		ProjectCreationForm form = projectController.getProjectCreationForm();
		form.setBudgetEstimate(10000);
		form.setDescription("This is Project A");
		form.setLeadDeveloper(major);
		form.setName("ProjectA");
		form.setStartDate(new Date());
		projectController.createProject(form);
		Project projectA = projectController.getProjectList().get(0);
		ProjectAssignForm assignForm = projectController.getProjectAssignForm();
		assignForm.setDeveloper(major);
		assignForm.setProject(projectA);
		assignForm.setRole(Role.PROGRAMMER);
		projectController.assignToProject(assignForm);
		assignForm = projectController.getProjectAssignForm();
		assignForm.setDeveloper(maria);
		assignForm.setProject(projectA);
		assignForm.setRole(Role.TESTER);
		projectController.assignToProject(assignForm);
		// Subsystem A1
		SubsystemCreationForm subForm = projectController.getSubsystemCreationForm();
		subForm.setDescription("Sub A1");
		subForm.setName("SubsystemA1");
		subForm.setParent(projectA);
		projectController.createSubsystem(subForm);
		// Subsystem A2
		subForm = projectController.getSubsystemCreationForm();
		subForm.setDescription("Sub A2");
		subForm.setName("SubsystemA2");
		subForm.setParent(projectA);
		projectController.createSubsystem(subForm);
		Subsystem subSystemA2 = projectA.getSubsystems().get(1);
		// Subsystem A3
		subForm = projectController.getSubsystemCreationForm();
		subForm.setDescription("Sub A3");
		subForm.setName("SubsystemA3");
		subForm.setParent(projectA);
		projectController.createSubsystem(subForm);
		Subsystem subSystemA3 = projectA.getSubsystems().get(2);
		// Subsystem A3.1
		subForm = projectController.getSubsystemCreationForm();
		subForm.setDescription("Sub A3.1");
		subForm.setName("SubsystemA3.1");
		subForm.setParent(subSystemA3);
		projectController.createSubsystem(subForm);
		Subsystem subSystemA31 = subSystemA3.getSubsystems().get(0);
		// Subsystem A3.2
		subForm = projectController.getSubsystemCreationForm();
		subForm.setDescription("Sub A3.2");
		subForm.setName("SubsystemA3.2");
		subForm.setParent(subSystemA3);
		projectController.createSubsystem(subForm);
		
		// ProjectB
		form = projectController.getProjectCreationForm();
		form.setBudgetEstimate(10000);
		form.setDescription("This is Project B");
		form.setLeadDeveloper(maria);
		form.setName("ProjectB");
		form.setStartDate(new Date());
		projectController.createProject(form);
		Project projectB = projectController.getProjectList().get(1);
		assignForm = projectController.getProjectAssignForm();
		assignForm.setDeveloper(major);
		assignForm.setProject(projectB);
		assignForm.setRole(Role.PROGRAMMER);
		projectController.assignToProject(assignForm);
		// Subsystem B1
		subForm = projectController.getSubsystemCreationForm();
		subForm.setDescription("Sub B1");
		subForm.setName("SubsystemB1");
		subForm.setParent(projectB);
		projectController.createSubsystem(subForm);
		Subsystem subSystemB1 = projectB.getSubsystems().get(0);
		// Subsystem B2
		subForm = projectController.getSubsystemCreationForm();
		subForm.setDescription("Sub B2");
		subForm.setName("SubsystemB2");
		subForm.setParent(projectB);
		projectController.createSubsystem(subForm);
		Subsystem subSystemB2 = projectB.getSubsystems().get(1);
		// Subsystem B2.1
		subForm = projectController.getSubsystemCreationForm();
		subForm.setDescription("Sub B2.1");
		subForm.setName("SubsystemB2.1");
		subForm.setParent(subSystemB2);
		projectController.createSubsystem(subForm);
		
		// Bugreport 1
		BugReportCreationForm bugForm = bugReportController.getBugReportCreationForm();
		bugForm.setDescription("If the function parse ewd is invoked while ...");
		bugForm.setTitle("The function parse ewd returns unexpected results");
		bugForm.setIssuer(maria);
		bugForm.setSubsystem(subSystemB1);
		bugForm.setDependsOn(new ArrayList<BugReport>());
		bugReportController.createBugReport(bugForm);
		BugReport report1 = bugReportController.getBugReportList().get(0);
		BugReportAssignForm bugAssign = bugReportController.getBugReportAssignForm();
		bugAssign.setBugReport(report1);
		bugAssign.setDeveloper(maria);
		bugReportController.assignToBugReport(bugAssign);
		BugReportUpdateForm bugUpdate = bugReportController.getBugReportUpdateForm();
		bugUpdate.setBugReport(report1);
		bugUpdate.setBugTag(BugTag.CLOSED);
		bugReportController.updateBugReport(bugUpdate);
				
		// Bugreport 2
		bugForm = bugReportController.getBugReportCreationForm();
		bugForm.setDescription("If incorrect user input is entered into the system ...");
		bugForm.setTitle("Crash while processing user input");
		bugForm.setIssuer(major);
		bugForm.setSubsystem(subSystemA31);
		bugForm.setDependsOn(new ArrayList<BugReport>());
		bugReportController.createBugReport(bugForm);
		BugReport report2 = bugReportController.getBugReportList().get(1);
		bugAssign = bugReportController.getBugReportAssignForm();
		bugAssign.setBugReport(report2);
		bugAssign.setDeveloper(major);
		bugReportController.assignToBugReport(bugAssign);
		bugAssign = bugReportController.getBugReportAssignForm();
		bugAssign.setBugReport(report2);
		bugAssign.setDeveloper(maria);
		bugReportController.assignToBugReport(bugAssign);
		bugUpdate = bugReportController.getBugReportUpdateForm();
		bugUpdate.setBugReport(report2);
		bugUpdate.setBugTag(BugTag.ASSIGNED);
		bugReportController.updateBugReport(bugUpdate);
		
		// Bugreport 3
		bugForm = bugReportController.getBugReportCreationForm();
		bugForm.setDescription("“If the function process dfe is invoked with ...");
		bugForm.setTitle("SubsystemA2 feezes");
		bugForm.setIssuer(major);
		bugForm.setSubsystem(subSystemA2);
		bugForm.setDependsOn(new ArrayList<BugReport>());
		bugReportController.createBugReport(bugForm);
		
		userManager.logOff();
	}

	public static void processCommand(String command) {
		String cmd = command.trim().toLowerCase();
		
		if (cmd.equals("login")) {
			login();
		} else if (cmd.equals("help")) {
			help();
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
		}
	}
	
	public static void login() {
		boolean valid = false;
		int category = 0;
		ArrayList<User> users = new ArrayList<User>();
		User selectedUser = null;
		while (!valid) {
			valid = true;
			System.out.println("Select a user cateogry by entering the number: ");
			System.out.println(" 1. Administrator");
			System.out.println(" 2. Issuer");
			System.out.println(" 3. Developer");
			category = input.nextInt();
					
			switch (category) {
				case 1:
					users = userController.getUserList(UserCategory.ADMIN);
					break;
				case 2:
					users = userController.getUserList(UserCategory.ISSUER);
					break;
				case 3:
					users = userController.getUserList(UserCategory.DEVELOPER);
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
	
	public static void help() {
		
	}
	
	public static void createProject() {
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
		
		boolean valid = false;
		while (!valid) {
			try {
				System.out.println("Enter the start date for the project (dd/mm/yyyy):");
				String date = input.nextLine();
				form.setStartDate((new SimpleDateFormat("dd/mm/yyyy")).parse(date));
				valid = true;
			} catch (Exception e) { }
		}
		
		User lead = selectUser(userController.getUserList(UserCategory.DEVELOPER));
		form.setLeadDeveloper((Developer)lead);
		
		projectController.createProject(form);
	}
	
	public static void updateProject() {
		ProjectUpdateForm form;
		try {
			 form = projectController.getProjectUpdateForm();
		} catch (UnauthorizedAccessException e) {
			System.out.println(e.getMessage());
			return;
		}
		
		Project project = selectProject(projectController.getProjectList());
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
				String date = input.nextLine();
				form.setStartDate((new SimpleDateFormat("dd/mm/yyyy")).parse(date));
				valid = true;
			} catch (Exception e) { }
		}
		
		System.out.println("Enter the lead developer for the project (dd/mm/yyyy):");
		User lead = selectUser(userController.getUserList(UserCategory.DEVELOPER));
		form.setLeadDeveloper((Developer)lead);
		
		projectController.updateProject(form);
	}
	
	public static void assignToProject() {
		ProjectAssignForm form = projectController.getProjectAssignForm();
		
		Project project = selectProject(projectController.getProjectList());
		form.setProject(project);
		Developer dev = (Developer) selectUser(userController.getUserList(UserCategory.DEVELOPER));
		form.setDeveloper(dev);
		Role role = selectRole();
		form.setRole(role);
		
		projectController.assignToProject(form);
	}

	public static void deleteProject() {
		ProjectDeleteForm form;
		try {
			form = projectController.getProjectDeleteForm();
		} catch (UnauthorizedAccessException e) {
			System.out.println(e.getMessage());
			return;
		}
		
		Project project = selectProject(projectController.getProjectList());
		form.setProject(project);
		
		projectController.deleteProject(form);
	}
	
	@SuppressWarnings("deprecation")
	public static void showProject() {
		Project project = selectProject(projectController.getProjectList());
		
		System.out.println(" -- " + project.getName() + " -- ");
		System.out.println(" Description: " + project.getDescription());
		System.out.println(" Budget estimate: " + project.getBudgetEstimate());
		System.out.println(" Creation date: " + project.getCreationDate().getDay() + "/" + project.getCreationDate().getMonth() + "/" + project.getCreationDate().getYear());
		System.out.println(" Start date: " + project.getStartDate().getDay() + "/" + project.getStartDate().getMonth() + "/" + project.getStartDate().getYear());
		System.out.println(" Version: " + project.getVersion());
		
		for (Subsystem system : project.getAllDirectOrIndirectSubsystems()) {
			System.out.println(" -- " + system.getName() + " -- ");
			System.out.println(" Description: " + system.getDescription());
			System.out.println(" Version: " + system.getVersion());
		}
	}
	
	public static void createSubSystem() {
		SubsystemCreationForm form;
		try {
			form = projectController.getSubsystemCreationForm();
		} catch (UnauthorizedAccessException e) {
			System.out.println(e.getMessage());
			return;
		}
		
		ArrayList<model.projects.System> allSystems = new ArrayList<model.projects.System>();
		for (Project project : projectController.getProjectList()) {
			allSystems.add(project);
			for (model.projects.System sys : project.getAllDirectOrIndirectSubsystems()) { 
				allSystems.add(sys);
			}
		}
		
		model.projects.System selectedSystem = selectSystem(allSystems);
		form.setParent(selectedSystem);
		
		System.out.println("Enter the name of the subsystem:");
		form.setName(input.nextLine());
		System.out.println("Enter the description of the subsystem:");
		form.setDescription(input.nextLine());
		
		projectController.createSubsystem(form);
	}
	
	public static void createBugReport() {
		BugReportCreationForm form = bugReportController.getBugReportCreationForm();
		
		Project chosenProject = selectProject(projectController.getProjectList());
		Subsystem chosenSubsystem = selectSubsystem(chosenProject.getSubsystems());
		
		form.setIssuer((Issuer) userController.getLoggedInUser());
		form.setSubsystem(chosenSubsystem);
		System.out.println("Enter the title of the BugReport:");
		form.setTitle(input.nextLine());
		System.out.println("Enter a description:");
		form.setDescription(input.next());
		
		//TO DO
		form.setDependsOn(new ArrayList<BugReport>());
		
		bugReportController.createBugReport(form);
	}
	
	public static void inspectBugReport() {
		BugReport bugReport = selectBugReport(bugReportController.getBugReportList());
		
		System.out.println(" -- " + bugReport.getTitle() + " -- ");
		System.out.println(" Description: " + bugReport.getDescription());
		System.out.println(" BugTag: " + bugReport.getBugTag().toString());
		System.out.println(" Creation Date: " + bugReport.getCreationDate());
		System.out.println(" Issued by: " + bugReport.getIssuedBy().getUserName());
		System.out.println(" Subsystem: " + bugReport.getSubsystem().getName());
		if (bugReport.getBugTag() == BugTag.DUPLICATE) System.out.println(" Duplicate: " + bugReport.getDuplicate().getTitle());
		System.out.println(" Assignees: ");
		for (Developer dev : bugReport.getAssignees())
			System.out.println(" -" + dev.getUserName());
		System.out.println(" Depends on: ");
		for (BugReport bug : bugReport.getDependsOn())
			System.out.println(" -" + bug.getTitle());
		System.out.println(" Comments: ");
		printComments(bugReport.getComments());
	}
	
	public static void createComment() {
		throw new UnsupportedOperationException();
//		CommentCreationForm form = bugReportController.getCommentCreationForm();
//		
//		BugReport chosenBugReport = selectBugReport(bugReportController.getBugReportList());
//	
//		System.out.println("Comment directly on this bugReport or on one of its comments?");
//		System.out.println("1. Directly");
//		System.out.println("2. Comments");
//		int selected = input.nextInt();
//		
//		if (selected == 1) {
//			form.setCommentable(chosenBugReport);
//		}
//		else { //selected == 2
//			ReplyComment chosenReplyComment;
//			boolean deeper = true;
			
//		}
	}
	
	public static void assignToBugReport() {
		
	}
	
	public static void updateBugReport() {
		
	}
	
	private static User selectUser(ArrayList<User> users) {
		while (true) {
			System.out.println("Select a user by entering the username: ");
			for (User user : users)
				System.out.println(user.getUserName() + " (" + user.getFirstName() + " " + user.getMiddleName() + " " + user.getLastName() + ")");
			
			String name = input.nextLine();
			for (User user : users) {
				if (user.getUserName().equals(name))
					return user;
			}
		}
	}
	
	private static void printComments(ArrayList<InitialComment> comments) {
		for (int index = 0; index < comments.size(); index++) {
			String level = " " + (index+1);
			System.out.println(level + ". " + comments.get(index).getText());
			printComments(comments.get(index).getComments(), level);
		}
	}

	private static void printComments(ArrayList<ReplyComment> comments, String string) {
		for (int index = 0; index < comments.size(); index++) {
			String level = string + (index+1);
			System.out.println(level + ". " + comments.get(index).getText());
			printComments(comments.get(index).getComments(), level);
		}
	}
	

	private static BugReport selectBugReport(ArrayList<BugReport> reports) {
		FilterType type = selectFilterType();
		System.out.println("Enter the search parameter: ");
		String parameter = input.nextLine();
		
		ArrayList<BugReport> filtered = bugReportController.getOrderedList(new FilterType[]{type}, new String[]{parameter});
		
		while (true) {
			System.out.println("Select a bugreport by entering its number: ");
			int number = 1;
			for (BugReport bugReport : filtered) {
				System.out.println(number + ". " + bugReport.getTitle());
				number++;
			}
			
			int selected = input.nextInt();
			if (selected <= filtered.size())
				return filtered.get(selected - 1);
		}
	}
	
	private static InitialComment selectInitialComment(ArrayList<InitialComment> comments) {
		while (true) {
			System.out.println("Select a comment by entering its number: ");
			int number = 1;
			for (InitialComment comment : comments) {
				System.out.println(number + ". " + comment.getText());
				number++;
			}
			
			int selected = input.nextInt();
			if (selected <= comments.size())
				return comments.get(selected - 1);		
		}
	}
	
	private static ReplyComment selectReplyComment(ArrayList<ReplyComment> comments) {
		while (true) {
			System.out.println("Select a comment by entering its number: ");
			int number = 1;
			for (ReplyComment comment : comments) {
				System.out.println(number + ". " + comment.getText());
				number++;
			}
			
			int selected = input.nextInt();
			if (selected <= comments.size())
				return comments.get(selected - 1);
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
			if (selected <= FilterType.values().length)
				return FilterType.values()[selected - 1];
		}
	}
	
	private static Project selectProject(ArrayList<Project> projects) {
		while (true) {
			System.out.println("Select a project by entering its number: ");
			int number = 1;
			for (Project project : projects) {
				System.out.println(number + ". " + project.getName());
				number++;
			}
			
			int selected = input.nextInt();
			if (selected <= projects.size())
				return projects.get(selected - 1);
		}
	}
	
	private static model.projects.System selectSystem(ArrayList<model.projects.System> systems) {
		while (true) {
			System.out.println("Select a project or subsystem by entering its number: ");
			int number = 1;
			for (model.projects.System sys : systems) {
				System.out.println(number + ". " + sys.getName());
				number++;
			}
			
			int selected = input.nextInt();
			if (selected <= systems.size())
				return systems.get(selected - 1);
		}
	}
	
	private static Subsystem selectSubsystem(ArrayList<Subsystem> subsystems) {
		while (true) {
			System.out.println("Select a subsystem by entering its number: ");
			int number = 1;
			for (Subsystem sub : subsystems) {
				System.out.println(number + ". " + sub.getName());
				number++;
			}
			
			int selected = input.nextInt();
			if (selected <= subsystems.size())
				return subsystems.get(selected - 1);
		}
	}
}
