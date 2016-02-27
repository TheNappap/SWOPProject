package bugreports;

public enum BugTag {
	RESOLVED,		//Issuer is satisfied by bug fix.
	CLOSED,			//Lead developer has closed the bug report.
	ASSIGNED,		//The bug has been assigned to an user.
	NEW,			//Freshly created bug report.
	NOT_A_BUG,		//Actually not a bug.
	UNDER_REVIEW,	//Bug fix has been made and is under review.
	DUPLICATE		//Bug report is a duplicate of another.
}