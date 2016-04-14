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

		int maxLen = this.getNumbers().size();
		if (stone.getNumbers().size() > maxLen)
			maxLen = stone.getNumbers().size();

		for (int i = 0; i < maxLen; i++)
		{
			int a = 0, b = 0;
			if (i < this.getNumbers().size())
				a = this.getNumbers().get(i);
			if (i < stone.getNumbers().size())
				b = stone.getNumbers().get(i);

			if (a != b)
				return false;
		}

		return true;
	}
}
