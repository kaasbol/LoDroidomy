package net.kaasbol.lodroidomy.droid;

public class FireAction
{
	private float angle;
	private float distance;
	private float radius;
	private float yield;
	
	public FireAction(float angle, float distance, float radius, float yield)
	{
		this.angle = angle;
		this.distance = distance;
		this.radius = radius;
		this.yield = yield;
	}
	
	public float getAngle()
	{
		return angle;
	}
	
	public float getDistance()
	{
		return distance;
	}
	
	public float getRadius()
	{
		return radius;
	}
	
	public float getYield()
	{
		return yield;
	}
}

