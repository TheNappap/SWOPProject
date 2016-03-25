package model.projects;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import model.users.IUser;

/**
 * This class represents a team for a project.
 */
public class ProjectTeam {
	private final ArrayList<DeveloperRoleRelation> team;

	/**
	 * Constructor.
	 * Method should not be public, but package. Due to need for testing, it is public.
	 */
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
	 * Method should not be public, but package. Due to need for testing, it is public.
	 * @param user The user for which to add a role.
	 * @param role The role to assign to the user.
     */
	public void addMember(IUser user, Role role) {
		if (user == null) throw new NullPointerException("Given user is null.");
		if (!user.isDeveloper()) throw new IllegalArgumentException("User should be a developer!");
		
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
	 * Method should not be public, but package. Due to need for testing, it is public.
     */
	public IUser getLeadDeveloper() {
		for (DeveloperRoleRelation rel : team) {
			if (rel.getRole() == Role.LEAD) {
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
	 * Method should not be public, but package. Due to need for testing, it is public.
     */
	public void setLeadDeveloper(IUser dev) {
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
	 * Method should not be public, but package. Due to need for testing, it is public.
     */
	public List<IUser> getProgrammers() {
		ArrayList<IUser> programmers = new ArrayList<IUser>();
		for (DeveloperRoleRelation rel : team) {
			if (rel.getRole() == Role.PROGRAMMER)
				programmers.add(rel.getUser());
		}
		return programmers;
	}

	/**
	 * Method to get all the testers in the team.
	 * @return List of all the testers in the team.
	 * Method should not be public, but package. Due to need for testing, it is public.
     */
	public List<IUser> getTesters() {
		ArrayList<IUser> testers = new ArrayList<IUser>();
		for (DeveloperRoleRelation rel : team) {
			if (rel.getRole() == Role.TESTER)
				testers.add(rel.getUser());
		}
		return testers;
	}
	
	/**
	 * Returns the roles that are not assigned to a given developer in a given project
	 * @param dev the given developer
	 * @return a list of roles not assigned to a developer
	 */
	public List<Role> getRolesNotAssignedTo(IUser dev){
		if (dev == null) throw new IllegalArgumentException("Developer can not be null!");
		if (!dev.isDeveloper()) throw new IllegalArgumentException("Developer should be a developer.");
		
		List<Role> roles = new ArrayList<Role>();
		for (Role role : Role.values()) {
			if(role != Role.LEAD)
				roles.add(role);
		}
		
		for (DeveloperRoleRelation rel : team) {
			if(rel.getUser() == dev){
				roles.remove(rel.getRole());
			}
		}
		
		return roles;
	}
	
	/**
	 * Returns if a given dev is lead
	 * @param dev the given developer
	 * @return if the given dev is lead
	 */
	public boolean isLead(IUser dev){
		if (dev == null) throw new IllegalArgumentException("Developer can not be null!");
		if (!dev.isDeveloper()) throw new IllegalArgumentException("Developer should be a developer.");
		
		IUser lead = getLeadDeveloper();
		
		return lead == dev;
	}
	
	/**
	 * Returns if a given dev is a tester
	 * @param dev the given developer
	 * @return if the given dev is tester
	 */
	public boolean isTester(IUser dev){
		if (dev == null) throw new IllegalArgumentException("Developer can not be null!");
		if (!dev.isDeveloper()) throw new IllegalArgumentException("Developer should be a developer.");
		
		for (DeveloperRoleRelation rel : team) {
			if(rel.getUser() == dev && rel.getRole() == Role.TESTER){
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Returns if a given dev is a programmer
	 * @param dev the given developer
	 * @return if the given dev is a programmer
	 */
	public boolean isProgrammer(IUser dev){
		if (dev == null) throw new IllegalArgumentException("Developer can not be null!");
		if (!dev.isDeveloper()) throw new IllegalArgumentException("Developer should be a developer.");
		
		for (DeveloperRoleRelation rel : team) {
			if(rel.getUser() == dev && rel.getRole() == Role.PROGRAMMER){
				return true;
			}
		}
		
		return false;
	}

	/**
	 * returns all developers in this team
	 * @return a list of all developers
	 */
	public List<IUser> getAllDevelopers() {
		Set<IUser> list = new HashSet<IUser>();
		
		for (DeveloperRoleRelation rel : team) {
			list.add(rel.getUser());
		}
		return new ArrayList<IUser>(list);
	}
}