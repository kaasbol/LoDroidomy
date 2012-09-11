package net.kaasbol.lodroidomy.droid;

public class MoveAction
{
	private float angle;
	private float distance;
	
	public MoveAction(float angle, float distance)
	{
		this.angle = angle;
		this.distance = distance;
	}
	
	public float getAngle()
	{
		return angle;
	}
	
	public float getDistance()
	{
		return distance;
	}
}
