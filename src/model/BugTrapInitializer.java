package model;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import model.bugreports.bugtag.BugTagEnum;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import model.projects.Project;
import model.projects.ProjectTeam;
import model.projects.Role;
import model.projects.Subsystem;
import model.users.Developer;
import model.users.Issuer;
import model.users.UserCategory;

class BugTrapInitializer {
	private BugTrap bugTrap;
	
	BugTrapInitializer(BugTrap bugTrap) {
		this.bugTrap = bugTrap;
	}
	
	void init() {
		try {
			File stateFile = new File("BugTrapState.xml");
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = builder.parse(stateFile);
			
			// Create users
			NodeList users = doc.getElementsByTagName("user");
			for (int i = 0; i < users.getLength(); i++) {
				if (users.item(i).getNodeType() != Node.ELEMENT_NODE)
					continue;
				
				Element node = (Element)users.item(i);
				createUser(node);
			}
			
			// Create projects
			NodeList projects = doc.getElementsByTagName("project");
			for (int i = 0; i < projects.getLength(); i++) {
				if (projects.item(i).getNodeType() != Node.ELEMENT_NODE)
					continue;
				
				Element node = (Element)projects.item(i);
				createProject(node);
			}
			
			// Create bugreports
			NodeList bugs = doc.getElementsByTagName("bugreport");
			for (int i = 0; i < bugs.getLength(); i++) {
				if (bugs.item(i).getNodeType() != Node.ELEMENT_NODE)
					continue;
				
				Element node = (Element)bugs.item(i);
				createBugReport(node);
			}
			
		} catch (Exception e) {
			System.out.println("Initialization failed.");
		}
	}
	
	private void createUser(Element node) {
		String first = node.getAttribute("first");
		String middle = node.getAttribute("middle");
		String last = node.getAttribute("last");
		String username = node.getAttribute("username");
		UserCategory type = UserCategory.valueOf(node.getAttribute("type"));
		
		bugTrap.getUserManager().createUser(type, first, middle, last, username);
	}
	
	private void createProject(Element node) throws Exception {
		String name = node.getAttribute("name");
		double budgetEstimate = Double.parseDouble(node.getAttribute("budgetEstimate"));
		String descr = node.getAttribute("description");
		Date start = (new SimpleDateFormat("dd/MM/yyyy")).parse(node.getAttribute("startDate"));
		Date creation = (new SimpleDateFormat("dd/MM/yyyy")).parse(node.getAttribute("creationDate"));
		
		NodeList roles = node.getElementsByTagName("role");
		ProjectTeam team = new ProjectTeam();
		for (int i = 0; i < roles.getLength(); i++) {
			if (roles.item(i).getNodeType() != Node.ELEMENT_NODE)
				continue;
						
			Element role = (Element)roles.item(i);
			team.addMember((Developer)bugTrap.getUserManager().getUser(role.getAttribute("user")), Role.valueOf(role.getAttribute("role")));
		}
			
		Project project = bugTrap.getProjectManager().createProject(name, descr,creation, start, budgetEstimate, team, null);
		
		ArrayList<Node> subsystems = getDirectElementsWithTagName((Element)getFirstDirectElementWithTagName(node, "subsystems"), "subsystem");
		for (int i = 0; i < subsystems.size(); i++) {
			if (subsystems.get(i).getNodeType() != Node.ELEMENT_NODE) 
				continue;
			
			Element subsystem = (Element)subsystems.get(i);
			createSubsystem(subsystem, project, project);
		}
	}
	
	private void createSubsystem(Element node, Project project, model.projects.System parent) {
		String name = node.getAttribute("name");
		String descr = node.getAttribute("description");
		
		Subsystem sub = bugTrap.getProjectManager().createSubsystem(name, descr, project, parent, null);
		
		ArrayList<Node> subsystems = getDirectElementsWithTagName((Element)getFirstDirectElementWithTagName(node, "subsystems"), "subsystem");
		for (int i = 0; i < subsystems.size(); i++) {
			if (subsystems.get(i).getNodeType() != Node.ELEMENT_NODE) 
				continue;
			
			Element subsystem = (Element)subsystems.get(i);
			createSubsystem(subsystem, project, sub);
		}
	}
	
	private void createBugReport(Element node) throws Exception {
		String title = node.getAttribute("title");
		String descr = node.getAttribute("description");
		Date creation = (new SimpleDateFormat("dd/MM/yyyy")).parse(node.getAttribute("creationDate"));
		Subsystem sub = bugTrap.getProjectManager().getSubsystemWithName(node.getAttribute("subsystem"));
		BugTagEnum tag = BugTagEnum.valueOf(node.getAttribute("tag"));
		Issuer issuer = (Issuer)bugTrap.getUserManager().getUser(node.getAttribute("issuer"));
		
		bugTrap.bugReportDAO.addBugReport(title, descr, creation, sub, issuer, new ArrayList<>(), tag.createBugTag());
	}
	
	// -- XML Helpers --
	private static Node getFirstDirectElementWithTagName(Element el, String tag) {
		return getDirectElementsWithTagName(el, tag).get(0);
	}
	
	private static ArrayList<Node> getDirectElementsWithTagName(Element el, String tag) {
		ArrayList<Node> children = new ArrayList<Node>();
		
		NodeList list = el.getElementsByTagName(tag);
		for (int i = 0; i < list.getLength(); i++) {
			Node n = list.item(i);
			if (n.getParentNode() == el)
				children.add(n);
		}
				
		return children;
	}
}
