package model.bugreports;

public class TargetMilestone {

	private final int major;
	private final int minor;
	
	public TargetMilestone(int major, int minor) {
		this.major = major;
		this.minor = minor;
	}

	public int getMinor() {
		return minor;
	}

	public int getMajor() {
		return major;
	}
}
