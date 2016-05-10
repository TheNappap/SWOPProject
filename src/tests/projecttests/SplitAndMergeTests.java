package tests.projecttests;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import model.bugreports.IBugReport;
import model.projects.ISubsystem;
import model.projects.Subsystem;
import tests.BugTrapTest;

public class SplitAndMergeTests extends BugTrapTest{

	@Test
	public void SplitTest() {
		//split word
		assertEquals(1, ((Subsystem) word).getBugReports().size());
		assertEquals(3, word.getSubsystems().size());
		word.split("Text", "Annoying Tools", "TextInWord", "Tools can annoying", Arrays.asList(new IBugReport[] { wordBug }), Arrays.asList(new ISubsystem[] { wordArt, comicSans }));
		
		List<ISubsystem> subsystems = office.getSubsystems();
		
		Subsystem subsystem = (Subsystem) subsystems.get(0);
		assertEquals("Excel", subsystem.getName());
		subsystem =  (Subsystem) subsystems.get(1);
		assertEquals("PowerPoint", subsystem.getName());
		
		subsystem =  (Subsystem) subsystems.get(2);
		assertEquals("Text", subsystem.getName());
		assertEquals("TextInWord", subsystem.getDescription());
		assertEquals(1, subsystem.getBugReports().size());
		assertEquals(2, subsystem.getSubsystems().size());
		
		subsystem =  (Subsystem) subsystems.get(3);
		assertEquals("Annoying Tools", subsystem.getName());
		assertEquals("Tools can annoying", subsystem.getDescription());
		assertEquals(0, subsystem.getBugReports().size());
		assertEquals(1, subsystem.getSubsystems().size());
	}
	
	@Test
	public void MergeSiblingsTest() {
		//TODO
	}
	
	@Test
	public void MergeParentAndChildTest() {
		//TODO
	}

}
