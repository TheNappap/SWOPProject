package model.projects;

import java.util.ArrayList;

import model.users.Developer;
import model.users.User;
import model.users.UserCategory;

public class ProjectTeam {
	// TODO - Gebruikmaken van Developer objecten overal
	private final ArrayList<UserRoleRelation> team;

	ProjectTeam() {
		team = new ArrayList<UserRoleRelation>();
	}
	
	void addMember(User user, Role role) {
		if (user == null) throw new NullPointerException("Given user is null.");
		
		boolean alreadyExists = false;
		for (UserRoleRelation rel : team) {
			if (rel.getRole() == Role.LEAD && role == Role.LEAD && !rel.getUser().getUserName().equals(user.getUserName()))
				throw new UnsupportedOperationException("Can not add a lead developer, because another developer is lead!");
			
			if (rel.getRole() == role && rel.getUser() == user) {
				alreadyExists = true;
				break;
			}
		}
		
		if (!alreadyExists)
			team.add(new UserRoleRelation(user, role));
	}
	
	public Developer getLeadDeveloper() {
		for (UserRoleRelation rel : team) {
			if (rel.getRole() == Role.LEAD && rel.getUser().getCategory() == UserCategory.DEVELOPER) {
				return (Developer)rel.getUser();
			}
		}
		return null;
	}
	
	void setLeadDeveloper(Developer dev) {
		// Make previous lead a programmer
		for (UserRoleRelation rel : team) {
			if (rel.getRole() == Role.LEAD && rel.getUser() == dev)
				return; // Current lead = new lead
			
			if (rel.getRole() == Role.LEAD)
				rel.setRole(Role.PROGRAMMER);
		}
		
		addMember(dev, Role.LEAD);
	}
}