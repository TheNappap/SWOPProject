package model.bugreports.bugtag;

import model.bugreports.BugReport;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing a BugTag.
 * BugTags are assigned to BugReports to indicate their
 * current status.
 */
public abstract class BugTag {
	
	public BugTag		confirmBugTag(BugTag bugTag) {
		throw new IllegalStateException();
	}

	public BugReport 	getLinkedBugReport() {
		throw new IllegalStateException();
	}

	public boolean isNew() {
		return false;
	}
	public boolean isInProgress() {
		return false;
	}
	public boolean isClosed() {
		return false;
	}

	public boolean isAssigned() { return false; }
	public boolean isDuplicate() { return false; }
	public boolean isNotABug() { return false; }
	public boolean isResolved() { return false; }
	public boolean isUnderReview() { return false; }

	protected abstract String getTagString();
	public boolean isSameTag(BugTag tag) {
		return this.getTagString().equals(tag.getTagString());
	}

	public static BugTag fromString(String tag, BugReport report) {
		return Assigned.fromString(tag, report);
	}

	public static List<String> allTagStrings() {
		List<String> tags = new ArrayList<>();
		tags.add(New.TAGSTRING);
		tags.add(Assigned.TAGSTRING);
		tags.add(UnderReview.TAGSTRING);
		tags.add(Resolved.TAGSTRING);
		tags.add(Closed.TAGSTRING);
		tags.add(NotABug.TAGSTRING);
		tags.add(Duplicate.TAGSTRING);

		return tags;
	}
}