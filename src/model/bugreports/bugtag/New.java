package model.bugreports.bugtag;

import model.bugreports.BugReport;
import model.bugreports.IBugReport;

/**
 * This class represents the New bug tag.
 * This means that a BugReport has recently been
 * created and is not yet assigned a tag.
 */
public class New extends BugTagState {

	public New(BugReport bugReport) {
		super(bugReport);
	}

	@Override
	public BugTagState confirmBugTag(BugTagState bugTag) {
		for (IBugReport dependency : bugReport.getDependsOn())
			if (	dependency.getBugTag() != BugTag.CLOSED ||
					dependency.getBugTag() != BugTag.RESOLVED)
				throw new IllegalStateException();
		
		return bugTag;
	}

	@Override
	public boolean isNew() {
		return true;
	}

	@Override
	public BugTag getTag() {
		return BugTag.NEW;
	}

	@Override
	public double getMultiplier() {
		return 3;
	}
}