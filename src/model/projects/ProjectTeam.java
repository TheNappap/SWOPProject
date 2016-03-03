package model.projects;

import java.util.ArrayList;

import model.users.Developer;
import model.users.User;
import model.users.UserCategory;

public class ProjectTeam {
	private final ArrayList<UserRoleRelation> team;

	ProjectTeam() {
		team = new ArrayList<UserRoleRelation>();
	}
	
	void addMember(User user, Role role) {
		boolean alreadyExists = false;
		for (UserRoleRelation rel : team) {
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
}