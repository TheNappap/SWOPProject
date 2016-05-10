package model.projects.health;

import java.util.ArrayList;
import java.util.List;

import model.projects.ISubsystem;
import model.projects.System;

public abstract class HealthCalculator {

	/**
	 * Returns the health of a given system
	 * @param system the given system
	 * @return an indicator that indicates the health of the given system
	 */
	public HealthIndicator calculateHealth(System system) {
		List<HealthIndicator> healthIndicatorsOfSubsystems = getHealthIndicatorsOfSubsystems(system);
		double bugImpact = system.getBugImpact();
		
		if(isHealthy(bugImpact, healthIndicatorsOfSubsystems)){
			return HealthIndicator.HEALTHY;
		}
		else if(isSatisfactory(bugImpact, healthIndicatorsOfSubsystems)){
			return HealthIndicator.SATISFACTORY;
		}
		else if(isStable(bugImpact, healthIndicatorsOfSubsystems)){
			return HealthIndicator.STABLE;
		}
		else if(isSerious(bugImpact, healthIndicatorsOfSubsystems)){
			return HealthIndicator.SERIOUS;
		}
		else{
			return HealthIndicator.CRITICAL;
		}
	}

	/**
	 * Returns if the system is healthy
	 * @param bugImpact the bugImpact of the system
	 * @param healthIndicatorsOfSubsystems the health indicators of the subsystems of the system
	 * @return if the system is healthy
	 */
	protected abstract boolean isHealthy(double bugImpact, List<HealthIndicator> healthIndicatorsOfSubsystems);

	/**
	 * Returns if the system is healthy
	 * @param bugImpact the bugImpact of the system
	 * @param healthIndicatorsOfSubsystems the health indicators of the subsystems of the system
	 * @return if the system is healthy
	 */
	protected abstract boolean isSatisfactory(double bugImpact, List<HealthIndicator> healthIndicatorsOfSubsystems);

	/**
	 * Returns if the system is healthy
	 * @param bugImpact the bugImpact of the system
	 * @param healthIndicatorsOfSubsystems the health indicators of the subsystems of the system
	 * @return if the system is healthy
	 */
	protected abstract boolean isStable(double bugImpact, List<HealthIndicator> healthIndicatorsOfSubsystems);

	/**
	 * Returns if the system is healthy
	 * @param bugImpact the bugImpact of the system
	 * @param healthIndicatorsOfSubsystems the health indicators of the subsystems of the system
	 * @return if the system is healthy
	 */
	protected abstract boolean isSerious(double bugImpact, List<HealthIndicator> healthIndicatorsOfSubsystems);
	

	/**
	 * Returns the health indicators of the subsystems of a given system
	 * @param system the given system
	 * @return a list of the health indicators of the subsystems of the system
	 */
	protected List<HealthIndicator> getHealthIndicatorsOfSubsystems(System system) {
		List<ISubsystem> subsystems = system.getSubsystems();
		
		List<HealthIndicator> healthIndicatorsOfSubsystems = new ArrayList<HealthIndicator>();
		for (ISubsystem sub : subsystems) {
			HealthIndicator healthIndicator = ((System) sub).getHealthIndicator(this);
			healthIndicatorsOfSubsystems.add(healthIndicator);
		}
		
		return healthIndicatorsOfSubsystems;
	} 
	
	/**
	 * /**
	 * Template method the determine if the system is a certain health indicator
	 * @param bugImpact the bugImpact of the system
	 * @param healthIndicatorsOfSubsystems the health indicators of the subsystems of the system
	 * @param healthIndicator the specific healthIndicator to check
	 * @param maxBugImpact the maximum bug impact 
	 * @param minHealthIndicator the minimum health indicator of the subsystems
	 * @return if the system is healthy
	 */
	protected boolean isHealthIndicator(double bugImpact, List<HealthIndicator> healthIndicatorsOfSubsystems, HealthIndicator healthIndicator, double maxBugImpact, HealthIndicator minHealthIndicator){
		boolean isHealthIndicator = true;
		for (HealthIndicator healthInd : healthIndicatorsOfSubsystems) {
			if(healthInd.compareTo(healthIndicator) > 0) isHealthIndicator = false;
		}
		
		if(bugImpact >= maxBugImpact){
			isHealthIndicator = false;
		}
		
		return isHealthIndicator;
	}

}
