package model;

import java.util.ArrayList;
import java.util.List;

public abstract class Milestone implements Comparable<Milestone> {

	private final List<Integer> numbers;
	
	public Milestone(List<Integer> numbers) {
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
}
