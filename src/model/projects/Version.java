package model.projects;

/**
 * This class represents version numbers in BugTrap.
 */
public class Version implements Comparable<Version> {

	private final int major;	//Major version.
	private final int minor;	//Minor version.
	private final int revision;	//Revision version.
	
	/**
	 * Constructor.
	 * @param major Major version.
	 * @param minor Minor version.
	 * @param revision Revision version.
	 */
	public Version(int major, int minor, int revision) {
		this.major 		= major;
		this.minor 		= minor;
		this.revision 	=  revision;
	}
	
	/**
	 * 
	 * @return The major version.
	 */
	public int getMajor() {
		return major;
	}

	/**
	 * 
	 * @return The minor version.
	 */
	public int getMinor() {
		return minor;
	}

	/**
	 * 
	 * @return The revision number.
	 */
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
	@Override
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
	
	@Override
	public String toString() {
		return getMajor() + "." + getMinor() + "." + getRevision();
	}

	/**
	 * Method to get a first version object.
	 * Version 1.0.0
	 * @return Version object with number 1.0.0
     */
	public static Version firstVersion() {
		return new Version(1, 0, 0);
	}
}