package model.notifications;

import model.bugreports.bugtag.BugTag;
import model.notifications.observers.*;
import model.projects.AchievedMilestone;

public enum NotificationType {
	BUGREPORT_CHANGE {	//A Change in a BugReport.
		@Override
		public ObserverWithMailbox createObserver(Mailbox box, Observable observable, BugTag tag, AchievedMilestone milestone) {
			return new BugReportChangeObserver(box, observable);
		}
	},
	BUGREPORT_SPECIFIC_TAG { //A change in a BugReport to specific Tag.
		@Override
		public ObserverWithMailbox createObserver(Mailbox box, Observable observable, BugTag tag, AchievedMilestone milestone) {
			return new BugReportSpecificTagObserver(box, observable, tag);
		}
	},
	CREATE_COMMENT { //A New comment
		@Override
		public ObserverWithMailbox createObserver(Mailbox box, Observable observable, BugTag tag, AchievedMilestone milestone) {
			return new CreateCommentObserver(box, observable);
		}
	},
	CREATE_BUGREPORT { // A new bug report was created
		@Override
		public ObserverWithMailbox createObserver(Mailbox box, Observable observable, BugTag tag, AchievedMilestone milestone) {
			return new CreateBugReportObserver(box, observable);
		}
	},
	ACHIEVED_MILESTONE {
		@Override
		public ObserverWithMailbox createObserver(Mailbox box, Observable observable, BugTag tag, AchievedMilestone milestone) {
			return new MilestoneObserver(box, observable);
		}
	},
	ACHIEVED_SPECIFIC_MILESTONE {
		@Override
		public ObserverWithMailbox createObserver(Mailbox box, Observable observable, BugTag tag, AchievedMilestone milestone) {
			return new SpecificMilestoneObserver(box, observable, milestone);
		}
	},
	PROJECT_VERSION_UPDATE {
		@Override
		public ObserverWithMailbox createObserver(Mailbox box, Observable observable, BugTag tag, AchievedMilestone milestone) {
			return new SystemVersionUpdateObserver(box, observable);
		}
	},
	PROJECT_FORK {
		@Override
		public ObserverWithMailbox createObserver(Mailbox box, Observable observable, BugTag tag, AchievedMilestone milestone) {
			return new ProjectForkObserver(box, observable);
		}
	};

	public abstract ObserverWithMailbox createObserver(Mailbox box, Observable observable, BugTag tag, AchievedMilestone milestone);
}