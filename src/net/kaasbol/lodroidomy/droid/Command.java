package net.kaasbol.lodroidomy.droid;

public class Command
{
	private String command;
	private String[] arguments;
	
	public Command(String command, String[] arguments)
	{
		this.command = command;
		this.arguments = arguments;
	}
	
	public String getCommand()
	{
		return command;
	}
	
	public String[] getArguments()
	{
		return arguments;
	}
}
