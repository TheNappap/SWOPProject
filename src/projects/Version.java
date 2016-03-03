package projects;

public class Version {

	private int major;
	private int minor;
	private int revision;
	
	Version(int major, int minor, int revision) {
		
	}
	
	public int getMajor() {
		return major;
	}

	void setMajor(int major) {
		this.major = major;
	}

	public int getMinor() {
		return minor;
	}

	void setMinor(int minor) {
		this.minor = minor;
	}

	public int getRevision() {
		return revision;
	}

	void setRevision(int revision) {
		this.revision = revision;
	}

	/**
	 * Method to compare two versions.
	 * @param version The version to compare this version object with.
	 * @return -1 if this < version
	 * 			0 if this == version
	 * 			1 if this > version
	 */
	public int compareTo(Version version) {
		if (this.major < version.major)
			return -1;
		if (this.major > version.major)
			return 1;
		
		if (this.minor < version.minor)
			return -1;
		if (this.minor > version.minor)
			return 1;
		
		if (this.revision < version.revision)
			return -1;
		if (this.revision > version.revision)
			return 1;
		
		return 0;
	}
}