package model.bugreports;

import java.util.ArrayList;
import java.util.List;

import model.Milestone;

/**
 * This class represents a target milestone in BugTrap.
 */
public class TargetMilestone extends Milestone {
	
	public TargetMilestone(List<Integer> numbers) {
		super(numbers);
	}
	
	public TargetMilestone() {
		super(makeInitialTargetMilestone());
	}
	
	private static List<Integer> makeInitialTargetMilestone() {
		List<Integer> numbers = new ArrayList<Integer>();
		numbers.add(0);
		return numbers;
	}

	@Override
	public int compareTo(Milestone o) {
		throw new UnsupportedOperationException();
	}
}
