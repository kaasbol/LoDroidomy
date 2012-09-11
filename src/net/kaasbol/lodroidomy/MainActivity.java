package net.kaasbol.lodroidomy;

import android.app.*;
import android.os.*;
import android.widget.*;
import java.util.*;
import net.kaasbol.lodroidomy.droid.*;

public class MainActivity extends Activity
{
	private List<Game> games = new LinkedList<Game>();
	
	private TextView text;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		
		text = (TextView)findViewById(R.id.text);
		
		games.add(new Game("Henk", "kaasbol.net", 1452));
		
		for(Game game : games)
		{
			game.setStatusCallback(new StatusCallback()
				{

					public void newStatus(GameStatus status)
					{
						String statusLine = status.getStatus()+System.getProperty("line.separator");
						text.append(statusLine);
						System.out.print(statusLine);
					}
					
				});
			game.execute();
		}
    }
}
