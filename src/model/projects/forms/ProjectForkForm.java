package model.projects.forms;

import model.Form;
import model.projects.Project;
import model.projects.Version;

import java.util.Date;

/**
 * Form used to store temporary data to fork project.
 */
public class ProjectForkForm implements Form {
    private Project project;

    private double budgetEstimate;
    private Date startDate;
    private Version version;

    public ProjectForkForm() {

    }

    public double getBudgetEstimate() {
        return budgetEstimate;
    }

    public void setBudgetEstimate(double budgetEstimate) {
        if (budgetEstimate <= 0) throw new IllegalArgumentException("The budget estimate should be strictly positive.");

        this.budgetEstimate = budgetEstimate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        if (startDate == null) throw new NullPointerException("Given starting date is null.");

        this.startDate = startDate;
    }

    public Version getVersion() {
        return version;
    }

    public void setVersion(Version version) {
        if (version == null) throw new NullPointerException("Given version is null.");
        if (this.project != null && this.getProject().getVersion().compareTo(version) == 1) throw new IllegalArgumentException("New version can not be less than previous version.");

        this.version = version;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        if (project == null) throw new NullPointerException("Given project is null.");

        this.project = project;
    }

    @Override
    public void allVarsFilledIn() {
        if (getBudgetEstimate() <= 0) throw new IllegalArgumentException("Budget estimate is null");
        if (getStartDate() == null) throw new NullPointerException("Start Date is null");
        if (getProject() == null) throw new NullPointerException("Project is null");
        if (getVersion() == null) throw new NullPointerException("Version is null");
    }
}
