package ch.holo.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandDoc implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String name, String[] args) {
		if(sender instanceof Player) {
			String[] commands = {
					"print(e) - Sends a message to the player.",
					"broadcast(e) - Sends a message to every player.",
					"explode(x, y, z, intensity) - Creates an explosion. (big boom)",
					"spawn(entity_type, x, y, z) - Spawns and returns an entity.",
					"getPlayer(name) - Returns a player value.",
					"getPlayers() - Returns the list of players currently connected.",
					"setBlock(material, x, y, z) - Set block (ex of material: \"diamond_block\"/\"grass\").",
					"getBlock(x, y, z) - Returns the material of the block.",
					"getHighestBlock(x, z) - Returns the material of the highest block.",
					"getHighestBlockY(x, z) - Returns the Y-coordonates of the highest block.",
					"",
					"�6�lEntity context :",
					"getType() - Returns the type of the entity.",
					"getX(), x() - Returns the X-coordonates.",
					"getY(), y() - Returns the Y-coordonates.",
					"getZ(), z() - Returns the Z-coordonates.",
					
					"setX(x) - Set the X-coordonates.",
					"setY(y) - Set the Y-coordonates.",
					"setZ(z) - Set the Z-coordonates.",
					
					"teleport(x, y, z) - Teleports the entity.",
					"remove() - Removes the entity (very cruel).",
					
					"getName() - Returns the name of the entity.",
					"setCustomName(name) - Sets and displays a custom name. (You can rename a chicken \"cheeseburger\" if you want)",
					"getCustomName() - i'm tired of writing this doc",
					"setGravityOn(bool) - Enable/Disable gravity for the entity.",
					"getGravityOn() - Returns if the gravity is enable or not.",
					"launch(entity_type, speed) - Spawn and launch another entity.",
					
					"getHealth() - Returns the health.",
					"setHealth(value) - Set the health.",
					"getMaxHealth() - Returns the max-health.",
					"setMaxHealth(value) - Set the max-health.",
					"resetMaxHealth() - i litteraly can't say more, it's in the name",
					"damage(value) - Damage the entity.",
					"",
					"�6�lPlayer context :",
					"isSneaking() - come on you got it",
					"getName() - Returns the name of the player",
					"getExp() - Returns the xp value.",
					"getLevel() - Returns the level.",
					"setExp(value) - Sets the xp value.",
					"setLevel(value) - Sets the level.",
					"inv - Returns the inventory.",
					"",
					"�6�lInventory context :",
					"give(material, amount) - Ex: give(\"diamond_sword\", 3) and returns the item value.",
					"remove(material, amount) - Ex: remove(\"wood_sword\", 2)",
					"contains(material, amount) - Returns true if the inventory contains this.",
					"clear() - Clears the inventory.",
					"",
					"�6�lItem context :",
					"getAmount() - Returns the amount.",
					"setAmount(value) - Sets the amount.",
					"getName() - Returns the name.",
					"setName(value) - Sets the name.",
					"",
					"�6�lFunctions to trigger:",
					"e_move() - Triggers each time the player move.",
					"e_interact() - Triggers each time the player interacts.",
					"e_interactEntity(entity) - Triggers each time the player interact with an entity.",
					"e_break() - Triggers each time the player breaks a block.",
					"e_arrowHit(e), e_projHit(e) - Triggers each time a projectile from the player hit something.",
					"e_shoot() - Triggers each time the player shoot with an arrow.",
					"e_sneak() - Triggers each time the player toggles the sneak.",
					
					"�5�nThis was made by Holo : https://www.youtube.com/c/HoloZar",
					"�5Hope you enjoy it ! :)"
					
			};
			
			for(String s:commands)
				sender.sendMessage("�e"+s);
		}
		return false;
	}

}
