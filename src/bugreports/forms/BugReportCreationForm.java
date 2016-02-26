package bugreports.forms;

import java.util.ArrayList;

import javax.swing.text.html.HTML.Tag;

import bugreports.BugReport;
import projects.*;
import users.*;

public class BugReportCreationForm {

	private Project project;
	private Subsystem title;
	private String description;
	private Subsystem subsystem;
	private Tag tag;
	private ArrayList<Developer> assigness;
	private ArrayList<BugReport> dependsOn;

}