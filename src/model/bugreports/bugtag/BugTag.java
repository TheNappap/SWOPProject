package model.bugreports.bugtag;

import model.bugreports.BugReport;

public enum BugTag {
    ASSIGNED {
        @Override
        public BugTagState createState(BugReport bugReport) {
            return new Assigned(bugReport);
        }
        
        @Override
        public boolean hasToBeLeadToSet() {
        	return false;
        }
    },
    CLOSED {
        @Override
        public BugTagState createState(BugReport bugReport) {
            return new Closed(bugReport);
        }
        
        @Override
        public boolean hasToBeLeadToSet() {
        	return true;
        }
    },
    DUPLICATE {
        @Override
        public BugTagState createState(BugReport bugReport) {
            return new Duplicate(bugReport);
        }
        
        @Override
        public boolean hasToBeLeadToSet() {
        	return true;
        }
    },
    NEW {
        @Override
        public BugTagState createState(BugReport bugReport) {
            return new New(bugReport);
        }
        
        @Override
        public boolean hasToBeLeadToSet() {
        	return false;
        }
    },
    NOTABUG {
        @Override
        public BugTagState createState(BugReport bugReport) {
            return new NotABug(bugReport);
        }
        
        @Override
        public boolean hasToBeLeadToSet() {
        	return true;
        }
    },
    RESOLVED {
        @Override
        public BugTagState createState(BugReport bugReport) {
            return new Resolved(bugReport);
        }

        @Override
        public boolean hasToBeLeadToSet() {
        	return true;
        }
    },
    UNDERREVIEW {
        @Override
        public BugTagState createState(BugReport bugReport) {
            return new UnderReview(bugReport);
        }
        
        @Override
        public boolean hasToBeLeadToSet() {
        	return false;
        }
    };

    /**
     * Create a state object from this Enum.
     * THIS METHOD SHOULD NOT BE PUBLIC, however it is needed because of testing.
     */
    public abstract BugTagState createState(BugReport bugReport);
    public abstract boolean hasToBeLeadToSet();
}
