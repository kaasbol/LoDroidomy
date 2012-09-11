package net.kaasbol.lodroidomy.droid;

public class Protocol
{
	public static int VERSION = 1;

	public static class OUT
	{
		public static String JOIN = "join";
		public static String SPAWN = "spawn";
		public static String MOVE = "move";
		public static String SCAN = "scan";
		public static String FIRE = "fire";
	}
	
	public static class IN
	{
		public static String WELCOME = "welcome";
		public static String BEGIN = "begin";
		public static String END = "end";
		public static String DETECT = "detect";
		public static String HIT = "hit";
		public static String DEATH = "death";
		public static String ERROR= "error";
	}
}
