package model.bugreports.bugtag;

public enum BugTag {
    ASSIGNED {
        @Override
        public BugTagState createState() {
            return new Assigned();
        }
    },
    CLOSED {
        @Override
        public BugTagState createState() {
            return new Closed();
        }
    },
    DUPLICATE {
        @Override
        public BugTagState createState() {
            return new Duplicate();
        }
    },
    NEW {
        @Override
        public BugTagState createState() {
            return new New();
        }
    },
    NOTABUG {
        @Override
        public BugTagState createState() {
            return new NotABug();
        }
    },
    RESOLVED {
        @Override
        public BugTagState createState() {
            return new Resolved();
        }
    },
    UNDERREVIEW {
        @Override
        public BugTagState createState() {
            return new UnderReview();
        }
    };

    /**
     * Create a state object from this Enum.
     * THIS METHOD SHOULD NOT BE PUBLIC, however it is needed because of testing.
     */
    public abstract BugTagState createState();
}
