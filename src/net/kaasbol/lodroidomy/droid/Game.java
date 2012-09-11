package net.kaasbol.lodroidomy.droid;

import android.os.*;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.logging.*;

public class Game extends AsyncTask<Void, GameStatus, Void>
{
	private String name;
	private String hostname;
	private int port;
	
	private Droid droid;
	private int roundsDead = 0;
	
	private Socket socket;
	private BufferedWriter writer;
	private BufferedReader reader;
	private boolean end = false;
	
	private StatusCallback callback;
	
	public Game(String name, String hostname, int port)
	{
		this.name = name;
		this.hostname = hostname;
		this.port = port;
	}
	
	public void setStatusCallback(StatusCallback callback)
	{
		this.callback = callback;
	}
	
	protected Void doInBackground(Void... voids)
	{
		try
		{
			socket = new Socket(hostname, port);
			writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		}
		catch (IOException e)
		{
			publishProgress(new GameStatus("error: "+e.getMessage()));
			end = true;
		}
		
		try
		{
			if (!end)
				sendCommand(new Command(Protocol.OUT.JOIN, new String[]{ name }));
			while (!end)
			{
				Command command = receiveCommand();
				handleCommand(command);
			}
		}
		catch (IOException e)
		{
			publishProgress(new GameStatus("error: "+e.getMessage()));
		}
		
		return null;
	}
	
	protected void onProgressUpdate(GameStatus... progress)
	{
		for (GameStatus status : progress)
		{
			callback.newStatus(status);
		}
	}
	
	private Command receiveCommand() throws IOException
	{
		String line = reader.readLine();
		
		String[] parts = line.split(" ");
		Command result = new Command(parts[0], Arrays.copyOfRange(parts, 1, parts.length));
		
		publishProgress(new GameStatus("in: "+line));
		
		return result;
	}
	
	private void sendCommand(Command command) throws IOException
	{
		StringBuilder builder = new StringBuilder();
		
		builder.append(command.getCommand()).append(" ");
		for (String argument : command.getArguments())
		{
			builder.append(argument).append(" ");
		}
		
		publishProgress(new GameStatus("out: "+builder.toString()));
		
		writer.write(builder.toString());
		writer.flush();
	}
	
	private void handleCommand(Command command) throws IOException
	{
		if (Protocol.IN.WELCOME.equals(command.getCommand()))
		{
			handleWelcome(command.getArguments());
		}
		else if (Protocol.IN.BEGIN.equals(command.getCommand()))
		{
			handleBegin(command.getArguments());
		}
		else if (Protocol.IN.END.equals(command.getCommand()))
		{
			handleEnd(command.getArguments());
		}
		else if (Protocol.IN.DETECT.equals(command.getCommand()))
		{
			handleDetect(command.getArguments());
		}
		else if (Protocol.IN.HIT.equals(command.getCommand()))
		{
			handleHit(command.getArguments());
		}
		else if (Protocol.IN.DEATH.equals(command.getCommand()))
		{
			handleDeath(command.getArguments());
		}
		else if (Protocol.IN.ERROR.equals(command.getCommand()))
		{
			handleError(command.getArguments());
		}
	}
	
	private void handleWelcome(String[] arguments) throws IOException
	{
		int version = Integer.parseInt(arguments[0]);
		float energy = Float.parseFloat(arguments[1]);
		float charge = Float.parseFloat(arguments[2]);
		int turnDuration = Integer.parseInt(arguments[3]);
		int turnsLeft = Integer.parseInt(arguments[4]);
		
		if (Protocol.VERSION != version)
		{
			end = true;
		}
		else
		{
			droid = new Droid(name, energy, charge);
			sendCommand(new Command(Protocol.OUT.SPAWN, null));
		}
	}
	
	private void handleBegin(String[] arguments) throws IOException
	{
		int turnNumber = Integer.parseInt(arguments[0]);
		float energy = Float.parseFloat(arguments[1]);
		
		droid.setEnergy(energy);
		
		if (roundsDead == 0)
		{
			droid.prepare();
		
			// First we move our asses
        	MoveAction move = droid.getMoveAction();
        	if (move != null)
        	{
            	String[] args = new String[] { Float.toString(move.getAngle()), Float.toString(move.getDistance()) };
            	Command command = new Command(Protocol.OUT.MOVE, args);
            	sendCommand(command);
        	}
        
			// Secondly we fire the shit out of the enemies
        	FireAction fire = droid.getFireAction();
        	if (fire != null)
        	{
            	String[] args = new String[] {Float.toString(fire.getAngle()),
                	                          Float.toString(fire.getDistance()),
                    	                      Float.toString(fire.getRadius()),
                        	                  Float.toString(fire.getYield())};
            	Command command = new Command(Protocol.OUT.FIRE, args);
            	sendCommand(command);
        	}
        
			// Thirdly we scan the shit out of the area
        	ScanAction scan = droid.getScanAction();
        	if (scan != null)
        	{
            	String[] args = new String[] { Float.toString(scan.getRange())};
            	Command command = new Command(Protocol.OUT.SCAN, args);
            	sendCommand(command);
        	}
		}
		else
		{
			roundsDead--;
		}
	}
	
	private void handleEnd(String[] arguments) throws IOException
	{
		// Nothing to parse
	}
	
	private void handleDetect(String[] arguments) throws IOException
	{
		String name = arguments[0];
		float angle = Float.parseFloat(arguments[1]);
		float distance = Float.parseFloat(arguments[2]);
		float energy = Float.parseFloat(arguments[4]);
		droid.addDetection(new Enemy(name, angle, distance, energy));
	}
	
	private void handleHit(String[] arguments) throws IOException
	{
		String name = arguments[0];
		float angle = Float.parseFloat(arguments[1]);
		float yield = Float.parseFloat(arguments[2]);
		
		droid.decrementEnergy(yield);
	}
	
	private void handleDeath(String[] arguments) throws IOException
	{
		roundsDead = Integer.parseInt(arguments[0]);
	}
	
	private void handleError(String[] arguments) throws IOException
	{
		int errorCode = Integer.parseInt(arguments[0]);
		String errorMessage = arguments[1];
	}
}
