package model.projects;

import java.util.Date;
import java.util.List;

import model.users.IUser;

/**
 * Interface for the Project objects.
 */
public interface IProject extends ISystem {

	/**
	 * 
	 * @return creation Date of this Project.
	 */
    public Date getCreationDate();
    
    /**
     * 
     * @return start Date of this Project.
     */
    public Date getStartDate();
    
    /**
     * 
     * @return Budget estimate of this Project.
     */
    public double getBudgetEstimate();

    /**
     * 
     * @return Lead Developer of this Project.
     */
    public IUser getLeadDeveloper();
    
    /**
     * Set the Lead Developer of this Project.
     * @param user The Lead Developer of this Project.
     */
    public void setLeadDeveloper(IUser user);

    /**
     * 
     * @return Programmers of this Project.
     */
    public List<IUser> getProgrammers();
    
    /**
     * 
     * @return Testers of this Project.
     */
    public List<IUser> getTesters();

    /**
     * 
     * @return Developers of this Project.
     */
    public List<IUser> getAllDevelopers();
	
    /**
     * Determines whether the given User is Lead for this Project.
     * @param dev The User for whom to check if (s)he is Lead of this Project.
     * @return <tt>true</tt> if the given User is Lead for this Project.
     */
    public boolean isLead(IUser dev);
    
    /**
     * Determines whether the given User is Tester for this Project.
     * @param dev The User for whom to check if (s)he is Tester of this Project.
     * @return <tt>true</tt> if the given User is Tester for this Project.
     */
    public boolean isTester(IUser dev);
    
    /**
     * Determines whether the given User is Programmer for this Project.
     * @param dev The User for whom to check if (s)he is Programmer of this Project.
     * @return <tt>true</tt> if the given User is Programmer for this Project.
     */
    public boolean isProgrammer(IUser dev);
    
    /**
     * Assign a Programmer to work on this Project.
     * @param programmer The Programmer to assign.
     */
	public void addProgrammer(IUser programmer);
	
    /**
     * Assign a Tester to work on this Project.
     * @param programmer The Tester to assign.
     */
	public void addTester(IUser tester);
	
    /**
     * Get the Roles which are not assigned to the given Developer.
     * @param developer The Developer for which to return the Roles he doesn't have.
     * @return The Roles which are not assigned to the given Developer.
     */
	public List<Role> getRolesNotAssignedTo(IUser developer);
	
	/**
	 * 
	 * @return The Version of this Project.
	 */
    public Version getVersion();
}
