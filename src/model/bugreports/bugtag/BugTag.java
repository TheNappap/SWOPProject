package model.bugreports.bugtag;

import model.bugreports.BugReport;

public enum BugTag {
    ASSIGNED {
        @Override
        public BugTagState createState(BugReport bugReport) {
            return new Assigned(bugReport);
        }
    },
    CLOSED {
        @Override
        public BugTagState createState(BugReport bugReport) {
            return new Closed(bugReport);
        }
    },
    DUPLICATE {
        @Override
        public BugTagState createState(BugReport bugReport) {
            return new Duplicate(bugReport);
        }
    },
    NEW {
        @Override
        public BugTagState createState(BugReport bugReport) {
            return new New(bugReport);
        }
    },
    NOTABUG {
        @Override
        public BugTagState createState(BugReport bugReport) {
            return new NotABug(bugReport);
        }
    },
    RESOLVED {
        @Override
        public BugTagState createState(BugReport bugReport) {
            return new Resolved(bugReport);
        }
    },
    UNDERREVIEW {
        @Override
        public BugTagState createState(BugReport bugReport) {
            return new UnderReview(bugReport);
        }
    };

    /**
     * Create a state object from this Enum.
     * THIS METHOD SHOULD NOT BE PUBLIC, however it is needed because of testing.
     */
    public abstract BugTagState createState(BugReport bugReport);
}
