package ch.holo.commands;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

@SuppressWarnings("deprecation")
public class CommandStart implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String names, String[] args) {
		if(sender instanceof Player) {
			ItemStack book = new ItemStack(Material.LEGACY_BOOK_AND_QUILL);
			
			ItemMeta im = book.getItemMeta();
			im.setDisplayName("�dThe Codebook");
			im.setLore(Arrays.asList("You can code everything in this book !"));
			book.setItemMeta(im);
			
			((Player)sender).getInventory().addItem(book);
		}
		return false;
	}

}
