package model.projects;

import java.util.ArrayList;
import java.util.List;

import model.users.Developer;
import model.users.UserCategory;

/**
 * This class represents a team for a project.
 */
public class ProjectTeam {
	private final ArrayList<DeveloperRoleRelation> team;

	public ProjectTeam() {
		team = new ArrayList<DeveloperRoleRelation>();
	}

	/**
	 * Copy constructor.
	 * @param pteam The team to copy.
     */
	ProjectTeam(ProjectTeam pteam) {
		this.team = new ArrayList<DeveloperRoleRelation>();
		for (DeveloperRoleRelation rel : pteam.team)
			this.team.add(new DeveloperRoleRelation(rel));
	}

	/**
	 * Method to add a member to the team. The member is assigned a certain role.
	 * If this member with this role is already in the team, nothing is added.
	 * @param user The user for which to add a role.
	 * @param role The role to assign to the user.
     */
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

	/**
	 * Method to get the lead developer from this team.
	 * @return The lead developer in the team.
     */
	public Developer getLeadDeveloper() {
		for (DeveloperRoleRelation rel : team) {
			if (rel.getRole() == Role.LEAD && rel.getUser().isDeveloper()) {
				return rel.getUser();
			}
		}
		return null;
	}

	/**
	 * Method to set the lead developer in this team. If another
	 * lead developer was assigned, his role is changed to
	 * being a programmer in the team.
	 * @param dev The developer
     */
	public void setLeadDeveloper(Developer dev) {
		// Make previous lead a programmer
		for (DeveloperRoleRelation rel : team) {
			if (rel.getRole() == Role.LEAD && rel.getUser() == dev)
				return; // Current lead = new lead
			
			if (rel.getRole() == Role.LEAD) {
				team.remove(rel);
				addMember(rel.getUser(), Role.PROGRAMMER);
			}
		}
		
		addMember(dev, Role.LEAD);
	}

	/**
	 * Method to get the programmers in the team.
	 * @return List containing all the programmers in the team.
     */
	public List<Developer> getProgrammers() {
		ArrayList<Developer> programmers = new ArrayList<Developer>();
		for (DeveloperRoleRelation rel : team) {
			if (rel.getRole() == Role.PROGRAMMER)
				programmers.add(rel.getUser());
		}
		return programmers;
	}

	/**
	 * Method to get all the testers in the team.
	 * @return List of all the testers in the team.
     */
	public List<Developer> getTesters() {
		ArrayList<Developer> testers = new ArrayList<Developer>();
		for (DeveloperRoleRelation rel : team) {
			if (rel.getRole() == Role.TESTER)
				testers.add(rel.getUser());
		}
		return testers;
	}
}