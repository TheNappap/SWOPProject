package model.projects;

import java.util.Date;
import java.util.List;

import model.users.IUser;

/**
 * Interface for the Project objects.
 */
public interface IProject extends ISystem {

    public Date getCreationDate();
    public Date getStartDate();
    public double getBudgetEstimate();

    public IUser getLeadDeveloper();
    public void setLeadDeveloper(IUser user);

    public List<IUser> getProgrammers();
    public List<IUser> getTesters();

    public List<IUser> getAllDevelopers();
	
    public boolean isLead(IUser dev);
    public boolean isTester(IUser dev);
    public boolean isProgrammer(IUser dev);

	public void addProgrammer(IUser programmer);
	public void addTester(IUser tester);
	public List<Role> getRolesNotAssignedTo(IUser developer);
	
    public Version getVersion();
}
