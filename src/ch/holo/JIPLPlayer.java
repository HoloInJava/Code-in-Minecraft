package ch.holo;

import ch.holo.jipl.Context;
import ch.holo.jipl.Interpreter.Function;
import ch.holo.jipl.Interpreter.Value;

public class JIPLPlayer {
	
	protected String playerName;
	protected Context context;
	
	public JIPLPlayer(String playerName, Context context) {
		this.playerName = playerName;
		this.context = context;
	}

	public String getPlayerName() { return playerName; }
	public void setPlayerName(String playerName) { this.playerName = playerName; }
	
	public Context getContext() { return context; }
	public void setContext(Context context) { this.context = context; }

	public Object execute(String function, Value... values) {
		if(context != null) {
			Function fun = context.function(function);
			fun.getContext().parent = getContext();
			fun.execute(values);
		}
		return null;
	}
	
}
