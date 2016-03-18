package model.bugreports.bugtag;

import model.bugreports.BugReport;

/**
 * Class representing a BugTag
 *
 */
public abstract class BugTag {

	private final BugTagEnum[] 	acceptedTransitions; //To what BugTagEnums can this BugTag change to?
	
	/**
	 * Constructor.
	 * @param acceptedTransitions The BugTagEnums to which this BugTag can change.
	 */
	public BugTag(BugTagEnum[] acceptedTransitions) {
		this.acceptedTransitions 	= acceptedTransitions;
	}
	
	/**
	 * Copy constructor.
	 * @param other BugTag to copy.
	 */
	protected BugTag(BugTag other) {
		this.acceptedTransitions = other.acceptedTransitions;
	}
	
	/**
	 * Checks if this BugTag can change to the given BugTagEnum
	 * @param bugTagEnum The BugTagEnum to which this BugTag would like to change.
	 * @return true if the transition is allowed. false if it isn't.
	 */
	public boolean isTransitionAllowed(BugTagEnum bugTagEnum) {
		for (BugTagEnum allowed : acceptedTransitions)
			if (allowed == bugTagEnum) return true;
		return false;
	}
	
	public BugTagEnum[] getAcceptedTransitions() {
		return acceptedTransitions;
	}

	public abstract BugTagEnum getBugTagEnum();
	public abstract BugReport getDuplicate();
}