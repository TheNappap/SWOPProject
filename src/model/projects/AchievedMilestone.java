package model.projects;

import java.util.List;

import model.Milestone;

/**
 * This class represents an achieved milestone in BugTrap.
 */
public class AchievedMilestone extends Milestone {
	
	public AchievedMilestone(List<Integer> numbers) {
		super(numbers);
	}

	@Override
	public int compareTo(Milestone o) {
		throw new UnsupportedOperationException();
	}
}
