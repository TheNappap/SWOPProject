package model.projects.health;

public enum HealthIndicator implements Comparable<HealthIndicator> {
	HEALTHY, 
	SATISFACTORY, 
	STABLE, 
	SERIOUS, 
	CRITICAL;
}
