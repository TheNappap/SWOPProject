package model.bugreports;

/**
 * This class represents a Test in BugTrap.
 */
public class Test {

	private final String test;	//String representation of the Test.
	private boolean accepted; //Indicates if the test has been accepted or not.
	
	/**
	 * Constructor
	 * @param test String representation of the Test.
	 */
	public Test(String test) {
		this.test = test;
		this.accepted = false;
	}

	/**
	 * 
	 * @return String representation of the Test.
	 */
	public String getTest() {
		return test;
	}

	/**
	 * 
	 * @return Indicates if the Test has been accepted or not.
	 */
	public boolean isAccepted() {
		return accepted;
	}

	/**
	 * Accept the Test.
	 */
	public void accept() {
		this.accepted = true;
	}

}
