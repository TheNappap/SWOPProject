package model.projects;

import java.util.ArrayList;

import model.users.Developer;
import model.users.User;
import model.users.UserCategory;

public class ProjectTeam {
	private final ArrayList<DeveloperRoleRelation> team;

	public ProjectTeam() {
		team = new ArrayList<DeveloperRoleRelation>();
	}
	
	public void addMember(Developer user, Role role) {
		if (user == null) throw new NullPointerException("Given user is null.");
		
		boolean alreadyExists = false;
		for (DeveloperRoleRelation rel : team) {
			if (rel.getRole() == Role.LEAD && role == Role.LEAD && !rel.getUser().getUserName().equals(user.getUserName()))
				throw new UnsupportedOperationException("Can not add a lead developer, because another developer is lead!");
			
			if (rel.getRole() == role && rel.getUser() == user) {
				alreadyExists = true;
				break;
			}
		}
		
		if (!alreadyExists)
			team.add(new DeveloperRoleRelation(user, role));
	}
	
	public Developer getLeadDeveloper() {
		for (DeveloperRoleRelation rel : team) {
			if (rel.getRole() == Role.LEAD && rel.getUser().getCategory() == UserCategory.DEVELOPER) {
				return rel.getUser();
			}
		}
		return null;
	}
	
	public void setLeadDeveloper(Developer dev) {
		// Make previous lead a programmer
		for (DeveloperRoleRelation rel : team) {
			if (rel.getRole() == Role.LEAD && rel.getUser() == dev)
				return; // Current lead = new lead
			
			if (rel.getRole() == Role.LEAD)
				rel.setRole(Role.PROGRAMMER);
		}
		
		addMember(dev, Role.LEAD);
	}
	
	public ArrayList<Developer> getProgrammers() {
		ArrayList<Developer> programmers = new ArrayList<Developer>();
		for (DeveloperRoleRelation rel : team) {
			if (rel.getRole() == Role.PROGRAMMER)
				programmers.add(rel.getUser());
		}
		return programmers;
	}
	
	public ArrayList<Developer> getTesters() {
		ArrayList<Developer> testers = new ArrayList<Developer>();
		for (DeveloperRoleRelation rel : team) {
			if (rel.getRole() == Role.TESTER)
				testers.add(rel.getUser());
		}
		return testers;
	}
}