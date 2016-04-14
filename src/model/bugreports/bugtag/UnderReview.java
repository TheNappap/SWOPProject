package model.bugreports.bugtag;

import model.bugreports.BugReport;
import model.bugreports.IBugReport;

/**
 * This class represents the UnderReview bug tag.
 * This means that a BugReport is currently under review
 * and will be given feedback soon.
 */
public class UnderReview extends InProgress {

    public UnderReview(BugReport bugReport) {
		super(bugReport);
		// TODO Auto-generated constructor stub
	}
    
	@Override
	public BugTagState confirmBugTag(BugTagState bugTagState) {
		for (IBugReport dependency : bugReport.getDependsOn())
			if (	dependency.getBugTag() != BugTag.CLOSED ||
					dependency.getBugTag() != BugTag.RESOLVED)
				throw new IllegalStateException();
		
		return bugTagState;
	}


	@Override
    public BugTag getTag() {
        return BugTag.UNDERREVIEW;
    }
}