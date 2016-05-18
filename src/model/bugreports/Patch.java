package model.bugreports;

/**
 * This class represents a Patch in BugTrap.
 */
public class Patch implements IPatch {

	private final String patch; //String representation of the Patch.
	
	/**
	 * Constructor.
	 * @param patch String representation of the Patch.
	 */
	public Patch(String patch) {
		this.patch = patch;
	}
	
	/**
	 * @return The String representation of the Patch.
	 */
	@Override
	public String getPatch() {
		return patch;
	}
	
}
