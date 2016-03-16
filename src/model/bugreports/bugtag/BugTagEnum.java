package model.bugreports.bugtag;

public enum BugTagEnum {
	NEW 			{ @Override public BugTag createBugTag() { return new New(); } },			//Fresh BugReport
	ASSIGNED 		{ @Override public BugTag createBugTag() { return new Assigned(); } },		//A Developer has been assigned and the BugReport is being worked on.
	CLOSED 			{ @Override public BugTag createBugTag() { return new Closed(); } },		//The BugReport has been Closed.
	DUPLICATE 		{ @Override public BugTag createBugTag() { return new Duplicate(); } },		//The BugReport is a duplicate of some other BugReport.
	NOT_A_BUG 		{ @Override public BugTag createBugTag() { return new NotABug(); } },		//The BugReport represents something that is not a bug.
	RESOLVED 		{ @Override public BugTag createBugTag() { return new Resolved(); } },		//The BugReport has been fixed and the fix has been accepted.
	UNDER_REVIEW 	{ @Override public BugTag createBugTag() { return new UnderReview(); } };	//The BugReport has been fixed and is under review.
	
	public abstract BugTag createBugTag();
}
