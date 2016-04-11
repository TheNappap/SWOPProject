package model.bugreports;

public class Test {

	private final String test;
	private boolean accepted;
	
	public Test(String test) {
		this.test = test;
		this.accepted = false;
	}

	public String getTest() {
		return test;
	}

	public boolean isAccepted() {
		return accepted;
	}

	void accept() {
		this.accepted = true;
	}

}
