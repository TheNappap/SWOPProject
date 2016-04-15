package model.projects;

import java.util.Arrays;
import java.util.List;

import model.Milestone;

/**
 * This class represents an achieved milestone in BugTrap.
 */
public class AchievedMilestone extends Milestone {

	/**
	 * Default constructor.
	 * Construct "M0" Achieved Milestone.
	 */
	public AchievedMilestone() {
		super(Arrays.asList(new Integer[] {0}));
	}
	
	/**
	 * Constructor. Create Achieved Milestone with given numbers.
	 */
	public AchievedMilestone(List<Integer> numbers) {
		super(numbers);
	}
}
