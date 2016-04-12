package model.projects;

import java.util.Arrays;
import java.util.List;

import model.Milestone;

/**
 * This class represents an achieved milestone in BugTrap.
 */
public class AchievedMilestone extends Milestone {

	public AchievedMilestone() {
		super(Arrays.asList(new Integer[] {0}));
	}
	public AchievedMilestone(List<Integer> numbers) {
		super(numbers);
	}
}
