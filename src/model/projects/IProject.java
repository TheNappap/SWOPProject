package model.projects;

import java.util.Date;
import java.util.List;

import model.users.IUser;

/**
 * Interface for the Project objects.
 */
public interface IProject extends ISystem {

    Date getCreationDate();
    Date getStartDate();
    double getBudgetEstimate();

    IUser getLeadDeveloper();
    void setLeadDeveloper(IUser user);

    List<IUser> getProgrammers();
    List<IUser> getTesters();

	List<IUser> getAllDevelopers();
	
	boolean isLead(IUser dev);
	boolean isTester(IUser dev);
	boolean isProgrammer(IUser dev);

    void addProgrammer(IUser programmer);
    void addTester(IUser tester);
	List<Role> getRolesNotAssignedTo(IUser developer);
}
