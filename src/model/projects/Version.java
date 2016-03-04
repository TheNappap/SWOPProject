package model.projects;

public class Version {

	private int major;
	private int minor;
	private int revision;
	
	public Version(int major, int minor, int revision) {
		
	}
	
	public int getMajor() {
		return major;
	}

	public int getMinor() {
		return minor;
	}

	public int getRevision() {
		return revision;
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
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Version))
			return false;
		
		Version v = (Version)obj;
		return (this.major == v.major && this.minor == v.minor && this.revision == v.revision);
	}
}