package bugreports.forms;

public interface Form {

	/**
	 * Checks if all variables in the Form are filled in.
	 * @return true if all variables are filled in i.e not null. false if there is at least one variable that is not filled in i.e. null.
	 */
	public boolean allVarsFilledIn();

}
