package model.projects.forms;

import java.util.ArrayList;
import java.util.List;

import model.Form;
import model.projects.ISystem;

/**
 * Form used to store temporary data to declare an achieved milestone.
 */
public class DeclareAchievedMilestoneForm implements Form {

	private ISystem system;
	private List<Integer> numbers;

	public DeclareAchievedMilestoneForm() {
		system = null;
		numbers = new ArrayList<Integer>();
	}

	public ISystem getSystem() {
		return system;
	}

	public void setSystem(ISystem system) {
		if (system == null) throw new NullPointerException("Given system is null.");
		
		this.system = system;
	}

	@Override
	public void allVarsFilledIn() {
		if (getSystem() == null) 	throw new NullPointerException("System is null");
		if (numbers.isEmpty()) 		throw new NullPointerException("The milestone has no numbers");
	}
}