package net.kaasbol.lodroidomy.droid;

public class Droid
{
	private String name;
	private float maxEnergy;
	private float charge;
	
	private float currentEnergy;
	private Area area;
	
	private Strategy strategy;
	
	public Droid(String name, float energy, float charge)
	{
		this.name = name;
		this.maxEnergy = energy;
		this.charge = charge;
		this.currentEnergy = energy;
		this.area = new Area();
	}
	
	public void setEnergy(float energy)
	{
		this.currentEnergy = energy;
	}
	
	public void decrementEnergy(float energy)
	{
		this.currentEnergy -= energy;
	}
	
	public void addDetection(Enemy detection)
	{
		area.AddDetection(detection);
	}
	
	public void prepare()
	{
		// This method selects the strategy chosen for this turn.
		// If there are no enemies, just scan and move.
		// If there is only one enemy, shoot it!
		// If there are more enemies, flee!
		
		// Order in which things happen:
		// 1. moving
		// 2. firing
		// 3. scanning
		
		strategy = new RandomStrategy(currentEnergy);
		
	}
	
	public MoveAction getMoveAction()
	{
		MoveAction move = strategy.getMoveAction();
		area.updatePositions(move);
		return move;
	}

	public FireAction getFireAction()
	{
		return strategy.getFireAction();
	}
	
	public ScanAction getScanAction()
	{
		ScanAction scan = strategy.getScanAction();
		area.Sweep();
		return scan;
	}
}
