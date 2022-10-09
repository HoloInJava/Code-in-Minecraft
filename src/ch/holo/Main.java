package ch.holo;

import java.util.ArrayList;

import org.bukkit.plugin.java.JavaPlugin;

import ch.holo.commands.CommandDoc;
import ch.holo.commands.CommandStart;
import ch.holo.jipl.Context;
import ch.holo.jipl.Interpreter.Value;

public class Main extends JavaPlugin {
	
	public static ArrayList<JIPLPlayer> jiplPlayers;
	public void onEnable() {
		jiplPlayers = new ArrayList<JIPLPlayer>();
		
		System.out.println("CodeInMinecraft is enable.");
		
		getCommand("codebook").setExecutor(new CommandStart());
		getCommand("codedoc").setExecutor(new CommandDoc());
		
		getServer().getPluginManager().registerEvents(new CodeListener(), this);
	}
	
	public void onDisable() {
		System.out.println("CodeInMinecraft is disable.");
	}
	
	public static JIPLPlayer getJIPLPlayer(String playerName) {
		for(JIPLPlayer cp:jiplPlayers)
			if(cp.getPlayerName().equals(playerName)) return cp;
		return null;
	}
	
	public static JIPLPlayer put(String playerName, Context context) {
		JIPLPlayer cp = getJIPLPlayer(playerName);
		if(cp == null) {
			JIPLPlayer cn = new JIPLPlayer(playerName, context);
			jiplPlayers.add(cn);
			return cn;
		} else cp.setContext(context);
		return cp;
	}
	
	public static void execute(String name, String function, Value... values) {
		JIPLPlayer cp = getJIPLPlayer(name);
		if(cp!=null) cp.execute(function, values);
	}
	
}
