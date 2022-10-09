package ch.holo;

import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerEditBookEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import ch.holo.jipl.Context;
import ch.holo.jipl.JIPL;
import ch.holo.modules.JIPLMinecraft;

public class CodeListener implements Listener {
	
	@EventHandler
	public void playerEditBookEvent(PlayerEditBookEvent event) {
		if(event.getNewBookMeta().getDisplayName().equals("�dThe Codebook")) {
			String str = "";
			for(int i = 1; i <= event.getNewBookMeta().getPageCount(); i++)
				str+=event.getNewBookMeta().getPage(i);
			
			Context con = JIPLMinecraft.getContext(event.getPlayer(), JIPL.getGlobalContext());
			Main.put(event.getPlayer().getName(), JIPL.run(str, con, event.getPlayer().getName())).setContext(con);
		}
	}
	
	@EventHandler
	public void playerMoveEvent(PlayerMoveEvent event) {
		Main.execute(event.getPlayer().getName(), "e_move");
	}
	
	@EventHandler
	public void playerInteractEvent(PlayerInteractEvent event) {
		Main.execute(event.getPlayer().getName(), "e_interact");
	}
	
	@EventHandler
	public void playerInteractEntityEvent(PlayerInteractEntityEvent event) {
		JIPLPlayer cp = Main.getJIPLPlayer(event.getPlayer().getName());
		if(cp != null) {
			cp.execute("e_interactEntity", JIPLMinecraft.generateEntityObject(event.getRightClicked(), cp.context));
		}
	}
	
	@EventHandler
	public void playerBreakBlockEvent(BlockBreakEvent event) {
		JIPLPlayer cp = Main.getJIPLPlayer(event.getPlayer().getName());
		if(cp != null) {
			cp.execute("e_break");
		}
	}
	
	@EventHandler
	public void onProjectileHit(ProjectileHitEvent event) {
		Projectile projectile = event.getEntity();
		
	    if(!(projectile.getShooter() instanceof Player))
	        return;

	    Player player = (Player) projectile.getShooter();
	    JIPLPlayer cp = Main.getJIPLPlayer(player.getName());
		if(cp != null) {
			cp.execute("e_arrowHit", JIPLMinecraft.generateEntityObject(projectile, cp.context));
			cp.execute("e_projHit", JIPLMinecraft.generateEntityObject(projectile, cp.context));
		}
	    
	}
	
	@EventHandler
	public void onPlayerShoot(EntityShootBowEvent event) {
	    if(!(event.getEntity() instanceof Player))
	        return;

	    Player player = (Player) event.getEntity();
	    JIPLPlayer cp = Main.getJIPLPlayer(player.getName());
		if(cp != null) {
			cp.execute("e_shoot", JIPLMinecraft.generateEntityObject(event.getProjectile(), cp.context));
		}
	    
	}

	@EventHandler
	public void onPlayerSneaking(PlayerToggleSneakEvent event) {
		Player player = event.getPlayer();
	    JIPLPlayer cp = Main.getJIPLPlayer(player.getName());
		if(cp != null) {
			cp.execute("e_sneak");
		}
	}
	
//	@EventHandler
//	public void playerJumpEvent(Player)
	
}
