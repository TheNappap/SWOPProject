package model.projects;

import model.users.IUser;

/**
 * This class describes a relation between users and their role
 * in a certain project. One user can have multiple roles, and thus
 * one user can occur in multiple UserRoleRelation objects.
 */
public class DeveloperRoleRelation {
	private IUser user;
	private Role role;
	
	DeveloperRoleRelation(IUser user, Role role) {
		if (!user.isDeveloper())
			throw new IllegalArgumentException("User should be a developer!");

		this.user = user;
		this.role = role;
	}

	/**
	 * Copy constructor.
	 * @param developerRoleRelation The DeveloperRoleRelation to copy.
     */
	DeveloperRoleRelation(DeveloperRoleRelation developerRoleRelation) {
		this.user = developerRoleRelation.getUser();
		this.role = developerRoleRelation.getRole();
	}
	
	/**
	 * Get the user involved in this relation.
	 * @return The user involved in this relation.
	 */
	public IUser getUser() {
		return user;
	}
	
	/**
	 * Get the role involved in this relation.
	 * @return The role involved in this relation.
	 */
	public Role getRole() {
		return role;
	}
	
	/**
	 * Set the role involved in this relation
	 * @param role The new role for this relation
	 */
	void setRole(Role role) {
		this.role = role; 
	}
}
