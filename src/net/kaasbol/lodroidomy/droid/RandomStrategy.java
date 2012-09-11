package net.kaasbol.lodroidomy.droid;

public class RandomStrategy implements Strategy
{
	private float energy;
	
	public RandomStrategy(float energy)
	{
		this.energy = energy;
	}

	public MoveAction getMoveAction()
	{
		float angle = (float)(Math.random() * 2 * Math.PI);
		float distance = (float)(Math.random() * energy / 3);
		return new MoveAction(angle, distance);
	}

	public FireAction getFireAction()
	{
		// Don't shoot in random mode
		return null;
	}

	public ScanAction getScanAction()
	{
		float distance = (float)(Math.random() * energy / 3);
		return new ScanAction(distance);
	}

}
