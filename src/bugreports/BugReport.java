package bugreports;

import java.util.ArrayList;
import java.util.Date;

import bugreports.forms.BugReportAssignForm;
import bugreports.forms.BugReportUpdateForm;
import bugreports.forms.CommentCreationForm;
import projects.*;
import users.*;

public class BugReport {

	private int id;
	private String title;
	private String description;
	private Date creationDate;
	private Subsystem subsystem;
	private Tag tag;
	private ArrayList<Developer> assignees;
	private ArrayList<BugReport> dependsOn;
	private BugReport duplicate;
	private Issuer issuedBy;
	private ArrayList<InitialComment> comments;

	/**
	 * 
	 * @param form
	 */
	public void createComment(CommentCreationForm form) {
		// TODO - implement BugReport.createComment
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param form
	 */
	public void assignDeveloper(BugReportAssignForm form) {
		// TODO - implement BugReport.assignDeveloper
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param form
	 */
	public void update(BugReportUpdateForm form) {
		// TODO - implement BugReport.update
		throw new UnsupportedOperationException();
	}

}