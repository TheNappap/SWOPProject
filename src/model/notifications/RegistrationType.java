package model.notifications;

import model.bugreports.bugtag.BugTag;
import model.notifications.observers.BugReportSpecificTagObserver;
import model.notifications.observers.CreateBugReportObserver;
import model.notifications.observers.CreateCommentObserver;
import model.notifications.observers.Observer;

public enum RegistrationType {
	BUGREPORT_CHANGE {
		@Override
		public Observer createObserver(Mailbox box, Observable observable, BugTag tag) {
			return new CreateCommentObserver(box, observable);
		}
	},
	BUGREPORT_SPECIFIC_TAG {
		@Override
		public Observer createObserver(Mailbox box, Observable observable, BugTag tag) {
			return new BugReportSpecificTagObserver(box, observable, tag);
		}
	},
	CREATE_COMMENT {
		@Override
		public Observer createObserver(Mailbox box, Observable observable, BugTag tag) {
			return new CreateCommentObserver(box, observable);
		}
	},
	CREATE_BUGREPORT {
		@Override
		public Observer createObserver(Mailbox box, Observable observable, BugTag tag) {
			return new CreateBugReportObserver(box, observable);
		}
	};

	public abstract Observer createObserver(Mailbox box, Observable observable, BugTag tag);
}
