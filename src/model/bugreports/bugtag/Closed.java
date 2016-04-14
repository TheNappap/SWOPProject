package model.bugreports.bugtag;

import model.bugreports.BugReport;
import model.bugreports.IBugReport;

/**
 * This class represents the Closed bug tag.
 * This means that the BugReport has been closed
 * and is no longer considered.
 */
public class Closed extends BugTagState {

	public Closed(BugReport bugReport) {
		super(bugReport);
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
	public boolean isClosed() {
		return true;
	}

	@Override
	public BugTag getTag() {
		return BugTag.CLOSED;
	}
}