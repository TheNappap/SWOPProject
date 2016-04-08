package tests.projecttests;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import model.projects.Version;

public class VersionTests {
	
	Version version1;
	Version version2;
	Version version3;
	Version version4;

	@Before
	public void setUp() throws Exception {
		version1 = new Version(1, 0, 0);
		version2 = new Version(1, 0, 1);
		version3 = new Version(1, 1, 0);
		version4 = new Version(2, 0, 0);
	}
	
	@Test
	public void versionTest(){
		Assert.assertEquals(1, version1.getMajor());
		Assert.assertEquals(0, version1.getMinor());
		Assert.assertEquals(0, version1.getRevision());
	}

	@Test
	public void compareToTest() {
		Assert.assertEquals(-1, version1.compareTo(version2));
		Assert.assertEquals(1, version2.compareTo(version1));
		Assert.assertEquals(-1, version1.compareTo(version3));
		Assert.assertEquals(1, version3.compareTo(version1));
		Assert.assertEquals(-1, version1.compareTo(version4));
		Assert.assertEquals(1, version4.compareTo(version1));

		Assert.assertEquals(-1, version2.compareTo(version3));
		Assert.assertEquals(1, version3.compareTo(version2));
		Assert.assertEquals(-1, version2.compareTo(version4));
		Assert.assertEquals(1, version4.compareTo(version2));
		Assert.assertEquals(-1, version3.compareTo(version4));
		Assert.assertEquals(1, version4.compareTo(version3));
		
		Assert.assertEquals(0, version1.compareTo(new Version(1, 0, 0)));
	}
	
	@Test
	public void equalsTest() {
		Assert.assertTrue(version1.equals(new Version(1, 0, 0)));
		Assert.assertFalse(version1.equals(version2));
		Assert.assertFalse(version1.equals(version3));
		Assert.assertFalse(version1.equals(version4));
	}
	
	@Test
	public void equalsFailTest() {
		Assert.assertFalse(version1.equals(null));
	}

}
