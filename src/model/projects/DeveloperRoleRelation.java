package model.projects;

import model.users.Developer;

/**
 * This class describes a relation between users and their role
 * in a certain project. One user can have multiple roles, and thus
 * one user can occur in multiple UserRoleRelation objects.
 */
public class DeveloperRoleRelation {
	private Developer user;
	private Role role;
	
	DeveloperRoleRelation(Developer user, Role role) {
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
	public Developer getUser() {
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
