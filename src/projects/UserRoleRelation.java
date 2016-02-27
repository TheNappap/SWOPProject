package projects;

import users.User;

/**
 * This class describes a relation between users and their role
 * in a certain project. One user can have multiple roles, and thus
 * one user can occur in multiple UserRoleRelation objects.
 * @author Matthieu De Laere
 */
public class UserRoleRelation {
	private User user;
	private Role role;
	
	UserRoleRelation(User user, Role role) {
		this.user = user;
		this.role = role;
	}
	
	/**
	 * Get the user involved in this relation.
	 * @return The user involved in this relation.
	 */
	public User getUser() {
		return user;
	}
	
	/**
	 * Get the role involved in this relation.
	 * @return The role involved in this relation.
	 */
	public Role getRole() {
		return role;
	}
}
