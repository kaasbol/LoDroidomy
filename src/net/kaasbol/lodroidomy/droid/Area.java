package net.kaasbol.lodroidomy.droid;

import java.util.*;

public class Area
{
	private List<Enemy> previousDetections;
	private List<Enemy> currentDetections;

	public void Sweep()
	{
		previousDetections = currentDetections;
		currentDetections = new LinkedList<Enemy>();
	}
	
	public void updatePositions(MoveAction move)
	{
		// Update both sets of detections
		for (Enemy enemy : previousDetections)
		{
			
		}
		for (Enemy enemy : currentDetections)
		{

		}
	}
	
	public void AddDetection(Enemy detection)
	{
		currentDetections.add(detection);
	}
	
	public boolean EnemiesNear()
	{
		return !currentDetections.isEmpty();
	}
	
	public Enemy getNearestEnemy()
	{
		Enemy result = null;
		
		for (Enemy enemy : currentDetections)
		{
			if (result == null || result.getDistance() > enemy.getDistance())
				result = enemy;
		}
		
		return null;
	}
}
