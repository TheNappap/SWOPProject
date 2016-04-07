package model.bugreports;

import java.util.ArrayList;
import java.util.List;

public class TargetMilestone {

	private final List<Integer> numbers;
	
	public TargetMilestone() {
		this.numbers = new ArrayList<Integer>();
		this.numbers.add(0);
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
