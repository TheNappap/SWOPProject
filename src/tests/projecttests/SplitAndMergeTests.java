package tests.projecttests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
		assertEquals(4, subsystems.size());
		assertEquals(8, office.getAllDirectOrIndirectSubsystems().size());
		
		Subsystem subsystem = (Subsystem) subsystems.get(0);
		assertEquals("Excel", subsystem.getName());
		subsystem =  (Subsystem) subsystems.get(1);
		assertEquals("PowerPoint", subsystem.getName());
		
		//first part of the split subsystem
		subsystem =  (Subsystem) subsystems.get(2);
		assertEquals("Text", subsystem.getName());
		assertEquals("TextInWord", subsystem.getDescription());
		assertEquals(1, subsystem.getBugReports().size());
		assertEquals(wordBug, subsystem.getBugReports().get(0));
		assertEquals(2, subsystem.getSubsystems().size());
		assertEquals(wordArt, subsystem.getSubsystems().get(0));
		assertEquals(comicSans, subsystem.getSubsystems().get(1));
		
		assertEquals("Text", subsystem.getBugReports().get(0).getSubsystem().getName());
		
		//second part of the split subsystem
		subsystem =  (Subsystem) subsystems.get(3);
		assertEquals("Annoying Tools", subsystem.getName());
		assertEquals("Tools can annoying", subsystem.getDescription());
		assertEquals(0, subsystem.getBugReports().size());
		assertEquals(1, subsystem.getSubsystems().size());
		assertEquals(clippy, subsystem.getSubsystems().get(0));
		
	}
	
	@Test
	public void MergeSiblingsTest() {
		//merge word and excel (siblings)
		assertEquals(1, ((Subsystem) word).getBugReports().size());
		assertEquals(3, word.getSubsystems().size());
		assertEquals(1, ((Subsystem) excel).getBugReports().size());
		assertEquals(1, excel.getSubsystems().size());
		word.merge("OfficeParty", "A combination of word and excel", excel);
		
		List<ISubsystem> subsystems = office.getSubsystems();
		assertEquals(2, subsystems.size());
		assertEquals(6, office.getAllDirectOrIndirectSubsystems().size());
		
		Subsystem subsystem = (Subsystem) subsystems.get(0);
		assertEquals("PowerPoint", subsystem.getName());
		
		//merged subsystem
		subsystem =  (Subsystem) subsystems.get(1);
		assertEquals("OfficeParty", subsystem.getName());
		assertEquals("A combination of word and excel", subsystem.getDescription());
		assertEquals(2, subsystem.getBugReports().size());
		assertEquals(wordBug, subsystem.getBugReports().get(0));
		assertEquals(excelBug, subsystem.getBugReports().get(1));
		assertEquals(4, subsystem.getSubsystems().size());
		assertEquals(wordArt, subsystem.getSubsystems().get(0));
		assertEquals(comicSans, subsystem.getSubsystems().get(1));
		assertEquals(clippy, subsystem.getSubsystems().get(2));
		assertEquals(excelTable, subsystem.getSubsystems().get(3));
		
		assertEquals("OfficeParty", subsystem.getBugReports().get(0).getSubsystem().getName());
		assertEquals("OfficeParty", subsystem.getBugReports().get(1).getSubsystem().getName());
	}
	
	@Test
	public void MergeParentAndChildTest() {
		//merge word and clippy (parent and child)
		assertEquals(1, ((Subsystem) word).getBugReports().size());
		assertEquals(3, word.getSubsystems().size());
		assertEquals(1, ((Subsystem) excel).getBugReports().size());
		assertEquals(0, clippy.getSubsystems().size());
		word.merge("Word+", "Word with embedded clippy", clippy);
		
		List<ISubsystem> subsystems = office.getSubsystems();
		assertEquals(3, subsystems.size());
		assertEquals(6, office.getAllDirectOrIndirectSubsystems().size());
		
		Subsystem subsystem = (Subsystem) subsystems.get(0);
		assertEquals("Excel", subsystem.getName());
		subsystem =  (Subsystem) subsystems.get(1);
		assertEquals("PowerPoint", subsystem.getName());
		
		//merged subsystem
		subsystem =  (Subsystem) subsystems.get(2);
		assertEquals("Word+", subsystem.getName());
		assertEquals("Word with embedded clippy", subsystem.getDescription());
		assertEquals(2, subsystem.getBugReports().size());
		assertEquals(wordBug, subsystem.getBugReports().get(0));
		assertEquals(clippyBug, subsystem.getBugReports().get(1));
		assertEquals(2, subsystem.getSubsystems().size());
		assertEquals(wordArt, subsystem.getSubsystems().get(0));
		assertEquals(comicSans, subsystem.getSubsystems().get(1));
		
		assertEquals("Word+", subsystem.getBugReports().get(0).getSubsystem().getName());
		assertEquals("Word+", subsystem.getBugReports().get(1).getSubsystem().getName());
	}

	@Test
	public void getMergeableWithTest() {
		List<ISubsystem> excelMergesWith = excel.getMergeableWith();
		assertFalse(excelMergesWith.contains(office));
		assertTrue(excelMergesWith.contains(word));
		assertFalse(excelMergesWith.contains(excel));
		assertTrue(excelMergesWith.contains(powerpoint));
		assertTrue(excelMergesWith.contains(excelTable));
		assertFalse(excelMergesWith.contains(clippy));
		assertFalse(excelMergesWith.contains(comicSans));
		assertFalse(excelMergesWith.contains(wordArt));

		List<ISubsystem> clippyMergesWith = clippy.getMergeableWith();
		assertFalse(clippyMergesWith.contains(office));
		assertTrue(clippyMergesWith.contains(word));
		assertFalse(clippyMergesWith.contains(excel));
		assertFalse(clippyMergesWith.contains(powerpoint));
		assertFalse(clippyMergesWith.contains(excelTable));
		assertFalse(clippyMergesWith.contains(clippy));
		assertTrue(clippyMergesWith.contains(comicSans));
		assertTrue(clippyMergesWith.contains(wordArt));
	}
}
