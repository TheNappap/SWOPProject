package model.bugreports;

public class Patch {

	private final String patch;
	private boolean accepted;
	
	public Patch(String patch) {
		this.patch = patch;
		this.accepted = false;
	}
	
	public String getPatch() {
		return patch;
	}

	public boolean isAccepted() {
		return accepted;
	}

	void accept() {
		this.accepted = true;
	}
}
