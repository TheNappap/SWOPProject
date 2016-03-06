package model.bugreports.filters;

/**
 * Enumeration of possible FilterTypes.
 *
 */
public enum FilterType {
	CONTAINS_STRING,	//Search for BugReports by Strings.
	FILED_BY_USER,		//Search for BugReports files by User.
	ASSIGNED_TO_USER	//Search for BugReports assigned by User.
}