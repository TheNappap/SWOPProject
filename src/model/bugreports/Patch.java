package model.bugreports;

/**
 * This class represents a Patch in BugTrap.
 */
public class Patch {

	private final String patch; //String representation of the Patch.
	private boolean accepted;	//Indicates if the Patch has been accepted or not.
	
	/**
	 * Constructor.
	 * @param patch String representation of the Patch.
	 */
	public Patch(String patch) {
		this.patch = patch;
		this.accepted = false;
	}
	
	/**
	 * @return The String representation of the Patch.
	 */
	public String getPatch() {
		return patch;
	}

	/**
	 * @return <tt>true</tt> if the Patch has been accepted.
	 */
	public boolean isAccepted() {
		return accepted;
	}

	/**
	 * Accept the Patch.
	 */
	public void accept() {
		this.accepted = true;
	}
}
