package model.projects;

public class AchievedMilestone {

	private final int major;
	private final int minor;

	public AchievedMilestone(int major, int minor) {
		this.major = major;
		this.minor = minor;
	}
	
	public int getMajor() {
		return major;
	}
	
	public int getMinor() {
		return minor;
	}
}
