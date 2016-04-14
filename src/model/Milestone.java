package model;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a milestone in BugTrap.
 */
public abstract class Milestone implements Comparable<Milestone> {

	private final List<Integer> numbers;
	
	public Milestone(List<Integer> numbers) {
		if (numbers == null || numbers.size() == 0)
			throw new IllegalArgumentException("Numbers should not be null and at least contain one number.");
		this.numbers = numbers;
	}
	
	public List<Integer> getNumbers() {
		List<Integer> numbers = new ArrayList<Integer>();
		numbers.addAll(this.numbers);
		return numbers;
	}
	
	@Override
	public String toString() {
		String result = "M";
		
		for (Integer number : this.numbers) result += number + ".";
		
		return result.substring(0, result.length()-1);
	}

	@Override
	public int compareTo(Milestone o) {
		int maxLen = this.getNumbers().size();
		if (o.getNumbers().size() > maxLen)
			maxLen = o.getNumbers().size();
		for (int i = 0; i < maxLen; i++) {
			int here = 0;
			int there = 0;
			if (i < this.getNumbers().size())
				here = this.getNumbers().get(i);
			if (i < o.getNumbers().size())
				there = o.getNumbers().get(i);

			if (here > there)
				return 1;
			if (here < there)
				return -1;
		}
		return 0;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Milestone))
			return false;

		Milestone stone = (Milestone)o;
		return this.compareTo(stone) == 0;
	}
}
