package net.kaasbol.lodroidomy.droid;

public class Enemy
{
	private String name;
	private float angle;
	private float distance;
	private float energy;
	
	public Enemy(String name, float angle, float distance, float energy)
	{
		this.name = name;
		this.angle = angle;
		this.distance = distance;
		this.energy = energy;
	}

	public String getName()
	{
		return name;
	}
	
	public float getAngle()
	{
		return angle;
	}
	
	public float getDistance()
	{
		return distance;
	}
	
	public float getEnergy()
	{
		return energy;
	}
}
