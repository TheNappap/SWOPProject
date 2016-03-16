package model.bugtag;

public enum BugTagEnum {
	NEW,			//Fresh BugReport
	ASSIGNED,		//A Developer has been assigned and the BugReport is being worked on.
	CLOSED,			//The BugReport has been Closed.
	DUPLICATE,		//The BugReport is a duplicate of some other BugReport.
	NOT_A_BUG,		//The BugReport represents something that is not a bug.
	RESOLVED,		//The BugReport has been fixed and the fix has been accepted.
	UNDER_REVIEW;	//The BugReport has been fixed and is under review.
}
