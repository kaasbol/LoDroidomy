package net.kaasbol.lodroidomy.droid;

public interface Strategy
{
	public MoveAction getMoveAction();
	public FireAction getFireAction();
	public ScanAction getScanAction();
}
