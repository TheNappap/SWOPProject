package model.bugreports;

import java.util.ArrayList;
import java.util.List;

public class TestSection {

	private List<Test> tests;
	
	public TestSection() {
		this.tests = new ArrayList<Test>();
	}
	
	/**
	 * Checks if there are no accepted tests.
	 * @return true if there are no (accepted) tests, false if there are. 
	 */
	public boolean noAcceptedTests() {
		for (Test test : tests)
			if (test.isAccepted()) return false;
		
		return true;
	}
	
	/**
	 * Checks if there are no tests.
	 * @return true if there are no tests, false if there are.
	 */
	public boolean noTestsSubmitted() {
		return tests.isEmpty();
	}
	
	/**
	 * Adds given test.
	 * @param test The test to add.
	 * @post The given test will be added.
	 * 		| contains(test)
	 */
	public void addTest(String test) {
		tests.add(new Test(test));
	}
	
	/**
	 * Accept the given tests.
	 * @param test The test to accept.
	 * @post The test will be accepted.
	 * 		| test.isAccepted()
	 */
	public void acceptTest(String test) {
		if (!contains(test)) throw new IllegalArgumentException("No such test.");
		
		getTestByString(test).accept();
	}
	
	/**
	 * Remove the given test.
	 * @param test The test to remove.
	 * @post The given test will be removed from this TestSection.
	 * 		| !contains(test)
	 */
	public void removeTest(String test) {
		if (!contains(test)) throw new IllegalArgumentException("No such test.");
		
		tests.remove(getTestByString(test));
	}
	
	/**
	 * Checks if the given test is in this TestSection
	 * @param containsTest The test to test for.
	 * @return true if the given test is in this TestSection, false if it is not.
	 */
	public boolean contains(String containsTest) {
		for (Test test : tests) 
			if (test.getTest().equals(containsTest))
				return true;
			
		return false;
	}
	
	public boolean contains(ITest containsTest) {
		for (Test test : tests)
			if (test == containsTest) return true;
		return false;
	}
	
	public List<ITest> getTests() {
		List<ITest> returnTests = new ArrayList<>();
		
		returnTests.addAll(tests);
			
		return returnTests;
	}
	
	public void clear() {
		tests.clear();
	}
	
	private Test getTestByString(String string) {
		for (Test test : tests) 
			if (test.getTest().equals(string))
				return test;
		
		throw new IllegalArgumentException("Given String doesn't represent a Test.");
	}
}
