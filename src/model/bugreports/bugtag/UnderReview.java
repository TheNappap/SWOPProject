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
	}
    
	@Override
	public BugTagState confirmBugTag(BugTagState bugTagState) {
		super.confirmBugTag(bugTagState);
		
		for (IBugReport dependency : bugReport.getDependsOn())
			if (	dependency.getBugTag() != BugTag.CLOSED ||
					dependency.getBugTag() != BugTag.RESOLVED)
				throw new IllegalStateException();
		
		return bugTagState;
	}

	@Override
	public boolean canAddPatches() {
		return true;
	}
	
	@Override
	public boolean canRevert() {
		return true;
	}
	
	@Override
    public BugTag getTag() {
        return BugTag.UNDERREVIEW;
    }

	@Override
	public double getMultiplier() {
		return 1;
	}
}