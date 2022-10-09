package ch.holo.modules;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import ch.holo.jipl.Context;
import ch.holo.jipl.Interpreter.BuildInFunction;
import ch.holo.jipl.Interpreter.List;
import ch.holo.jipl.Interpreter.ObjectValue;
import ch.holo.jipl.Interpreter.RTResult;
import ch.holo.jipl.Interpreter.StringValue;
import ch.holo.jipl.Interpreter.Value;
import ch.holo.jipl.Interpreter.Number;

@SuppressWarnings("deprecation")
public class JIPLMinecraft {
	
	public static Context getContext(Player player, Context gcontext) {
		gcontext.set("me", generateEntityObject(player, gcontext));
		
		gcontext.set("print", new BuildInFunction("print", "content") {
			private static final long serialVersionUID = 1L;
			protected Object executeFunction(Context context, RTResult res, Value... args) {
				player.sendMessage(args[0].toString());
				return res.success(Number.NULL);
			}
		});
		
		gcontext.set("broadcast", new BuildInFunction("print", "content") {
			private static final long serialVersionUID = 1L;
			protected Object executeFunction(Context context, RTResult res, Value... args) {
				Bukkit.broadcastMessage(args[0].toString());
				return res.success(Number.NULL);
			}
		});
		
		gcontext.set("explode", new BuildInFunction("explode", "x", "y", "z", "intensity") {
			private static final long serialVersionUID = 1L;
			protected Object executeFunction(Context context, RTResult res, Value... args) {
				res.register(checkArgumentTypes(res, args, Number.class, Number.class, Number.class, Number.class));
				if(res.shouldReturn()) return res;
				
				player.getWorld().createExplosion(new Location(player.getWorld(), context.number("x"), context.number("y"), context.number("z")), context.number("intensity"));
				return res.success(Number.NULL);
			}
		});
		
		gcontext.set("spawn", new BuildInFunction("spawn", "entity_type", "x", "y", "z") {
			private static final long serialVersionUID = 1L;
			protected Object executeFunction(Context context, RTResult res, Value... args) {
				res.register(checkArgumentTypes(res, args, StringValue.class, Number.class, Number.class, Number.class));
				if(res.shouldReturn()) return res;
				
				Entity ent = player.getWorld().spawnEntity(new Location(player.getWorld(), context.number("x"), context.number("y"), context.number("z")), EntityType.fromName(context.string("entity_type")));
				return res.success(generateEntityObject(ent, gcontext));
			}
		});
		
		gcontext.set("getPlayer", new BuildInFunction("getPlayer", "name") {
			private static final long serialVersionUID = 1L;
			protected Object executeFunction(Context context, RTResult res, Value... args) {
				res.register(checkArgumentTypes(res, args, StringValue.class));
				if(res.shouldReturn()) return res;
				
				Entity ent = Bukkit.getPlayer(context.string("name"));
				return res.success(generateEntityObject(ent, gcontext));
			}
		});
		
		gcontext.set("getPlayers", new BuildInFunction("getPlayers") {
			private static final long serialVersionUID = 1L;
			protected Object executeFunction(Context context, RTResult res, Value... args) {
				ArrayList<Object> array = new ArrayList<Object>();
				for(Player p:Bukkit.getOnlinePlayers())
					array.add(generateEntityObject(p, gcontext));
				List list = new List(array);
				return res.success(list);
			}
		});
		
		gcontext.set("setBlock", new BuildInFunction("setBlock", "material", "x", "y", "z") {
			private static final long serialVersionUID = 1L;
			protected Object executeFunction(Context context, RTResult res, Value... args) {
				res.register(checkArgumentTypes(res, args, StringValue.class, Number.class, Number.class, Number.class));
				if(res.shouldReturn()) return res;
				
				player.getWorld().getBlockAt((int)context.number("x"), (int)context.number("y"), (int)context.number("z")).setType(Material.valueOf(context.string("material").toUpperCase()));
				return res.success(Number.NULL);
			}
		});
		
		gcontext.set("getBlock", new BuildInFunction("getBlock", "x", "y", "z") {
			private static final long serialVersionUID = 1L;
			protected Object executeFunction(Context context, RTResult res, Value... args) {
				res.register(checkArgumentTypes(res, args, Number.class, Number.class, Number.class));
				if(res.shouldReturn()) return res;
				
				Material mat = player.getWorld().getBlockAt((int)context.number("x"), (int)context.number("y"), (int)context.number("z")).getType();
				return res.success(new StringValue(mat.name()));
			}
		});
		
		gcontext.set("getHighestBlock", new BuildInFunction("getHighestBlock", "x", "z") {
			private static final long serialVersionUID = 1L;
			protected Object executeFunction(Context context, RTResult res, Value... args) {
				res.register(checkArgumentTypes(res, args, Number.class, Number.class));
				if(res.shouldReturn()) return res;
				
				Material mat = player.getWorld().getHighestBlockAt((int)context.number("x"), (int)context.number("z")).getType();
				return res.success(new StringValue(mat.name()));
			}
		});
		
		gcontext.set("getHighestBlockY", new BuildInFunction("getHighestBlockY", "x", "z") {
			private static final long serialVersionUID = 1L;
			protected Object executeFunction(Context context, RTResult res, Value... args) {
				res.register(checkArgumentTypes(res, args, Number.class, Number.class));
				if(res.shouldReturn()) return res;
				
				int y = player.getWorld().getHighestBlockAt((int)context.number("x"), (int)context.number("z")).getY();
				return res.success(new Number(y));
			}
		});
		
		return gcontext;
	}
	
	public static ObjectValue generateEntityObject(Entity entity, Context parent) {
		Context con = new Context("<entity>", parent);
		
		con.set("getType", new BuildInFunction("getType") {
			private static final long serialVersionUID = 1L;
			protected Object executeFunction(Context context, RTResult res, Value... args) {
				return res.success(new StringValue(entity.getType().name()));
			}
		});
		
		// Position getters and setters.
		con.set("x", entity.getLocation().getX(), (e) -> entity.getLocation().setX((double) e), () -> entity.getLocation().getX());
		con.set("y", entity.getLocation().getY(), (e) -> entity.getLocation().setY((double) e), () -> entity.getLocation().getY());
		con.set("z", entity.getLocation().getZ(), (e) -> entity.getLocation().setZ((double) e), () -> entity.getLocation().getZ());
		con.set("vx", entity.getVelocity().getX(), (e) -> entity.getVelocity().setX((double) e), () -> entity.getVelocity().getX());
		con.set("vy", entity.getVelocity().getY(), (e) -> entity.getVelocity().setY((double) e), () -> entity.getVelocity().getY());
		con.set("vz", entity.getVelocity().getZ(), (e) -> entity.getVelocity().setZ((double) e), () -> entity.getVelocity().getZ());
		
		con.set("teleport", new BuildInFunction("teleport", "x", "y", "z") { 
			private static final long serialVersionUID = 1L;
			protected Object executeFunction(Context context, RTResult res, Value... args) {
				res.register(checkArgumentTypes(res, args, Number.class, Number.class, Number.class));
				if(res.shouldReturn()) return res;
				
				entity.teleport(new Location(entity.getWorld(), context.number("x"), context.number("y"), context.number("z")));
				return res.success(Number.NULL);
			}
		});
		
		con.set("remove", new BuildInFunction("remove") { 
			private static final long serialVersionUID = 1L;
			protected Object executeFunction(Context context, RTResult res, Value... args) {
				entity.remove();
				return res.success(Number.NULL);
			}
		});
		
		con.set("getName", new BuildInFunction("getName") { 
			private static final long serialVersionUID = 1L;
			protected Object executeFunction(Context context, RTResult res, Value... args) {
				return res.success(new StringValue(entity.getName()));
			}
		});
		
		con.set("getCustomName", new BuildInFunction("getCustomName") {
			private static final long serialVersionUID = 1L;
			protected Object executeFunction(Context context, RTResult res, Value... args) {
				return res.success(new StringValue(entity.getCustomName()));
			}
		});
		
		con.set("setCustomName", new BuildInFunction("setCustomName", "customName") {
			private static final long serialVersionUID = 1L;
			protected Object executeFunction(Context context, RTResult res, Value... args) {
				entity.setCustomName(args[0].toString());
				entity.setCustomNameVisible(true);
				return res.success(args[0]);
			}
		});
		
		con.set("setGravityOn", new BuildInFunction("setGravityOn", "bool") { 
			private static final long serialVersionUID = 1L;
			protected Object executeFunction(Context context, RTResult res, Value... args) {
				entity.setGravity(args[0].isTrue());
				return res.success(args[0]);
			}
		});
		
		con.set("isGravityOn", new BuildInFunction("isGravityOn") { 
			private static final long serialVersionUID = 1L;
			protected Object executeFunction(Context context, RTResult res, Value... args) {
				return res.success(entity.hasGravity()?Number.TRUE:Number.FALSE);
			}
		});
		
		con.set("launch", new BuildInFunction("launch", "entity_type", "speed") {
			private static final long serialVersionUID = 1L;
			protected Object executeFunction(Context context, RTResult res, Value... args) {
				res.register(checkArgumentTypes(res, args, StringValue.class, Number.class));
				if(res.shouldReturn()) return res;
				
				Entity ent = entity.getWorld().spawnEntity(entity.getLocation(), EntityType.fromName(context.string("entity_type")));
				ent.setVelocity(entity.getLocation().getDirection().multiply(context.number("speed")).add(new Vector(0, context.number("speed"), 0)));
				return res.success(generateEntityObject(ent, parent));
			}
		});
		
		con.set("sendMessage", new BuildInFunction("sendMessage", "msg") {
			private static final long serialVersionUID = 1L;
			protected Object executeFunction(Context context, RTResult res, Value... args) {
				entity.sendMessage(args[0].toString());
				return res.success(args[0]);
			}
		});
		
		if(entity instanceof Damageable) {
			Damageable entityD = (Damageable) entity;
			con.set("health", entityD.getHealth(), (e) -> entityD.setHealth((double) e), () -> entityD.getHealth());
			con.set("maxHealth", entityD.getMaxHealth(), (e) -> entityD.setMaxHealth((double) e), () -> entityD.getMaxHealth());
			
			con.set("damage", new BuildInFunction("damage", "value") {
				private static final long serialVersionUID = 1L;
				protected Object executeFunction(Context context, RTResult res, Value... args) {
					res.register(checkArgumentTypes(res, args, Number.class));
					if(res.shouldReturn()) return res;
					
					entityD.damage(context.number("value"));
					return res.success(new Number((float)entityD.getHealth()));
				}
			});
			con.set("resetMaxHealth", new BuildInFunction("resetMaxHealth") {
				private static final long serialVersionUID = 1L;
				protected Object executeFunction(Context context, RTResult res, Value... args) {
					entityD.resetMaxHealth();
					return res.success(new Number((float)entityD.getMaxHealth()));
				}
			});
		}
		
		if(entity instanceof LivingEntity) {
			LivingEntity entityLE = (LivingEntity) entity;
			con.set("addPotionEffect", new BuildInFunction("addPotionEffect", "potion_type", "duration", "amplifier") {
				private static final long serialVersionUID = 1L;
				protected Object executeFunction(Context context, RTResult res, Value... args) {
					res.register(checkArgumentTypes(res, args, StringValue.class, Number.class, Number.class));
					if(res.shouldReturn()) return res;
					
					entityLE.addPotionEffect(new PotionEffect(PotionEffectType.getByName(context.string("potion_type")), (int)context.number("duration"), (int)context.number("amplifier")));
					return res.success(Number.NULL);
				}
			});
		}
		
		if(entity instanceof Player) {
			Player player = (Player) entity;
			
			con.set("isSneaking", new BuildInFunction("isSneaking") {
				private static final long serialVersionUID = 1L;
				protected Object executeFunction(Context context, RTResult res, Value... args) {
					return res.success(player.isSneaking()?Number.TRUE:Number.FALSE);
				}
			});
			
			con.set("getName", new BuildInFunction("getName") {
				private static final long serialVersionUID = 1L;
				protected Object executeFunction(Context context, RTResult res, Value... args) {
					return res.success(new StringValue(player.getName()));
				}
			});
			
			con.set("level", player.getLevel(), (e) -> player.setLevel((int)(double)e), () -> player.getLevel());
			con.set("exp", player.getExp(), (e) -> player.setExp((float)e), () -> player.getExp());
			
			con.set("inv", generateInventoryObject(player.getInventory(), parent));
		}
		
		return new ObjectValue(con);
	}
	
	public static ObjectValue generateInventoryObject(Inventory inventory, Context parent) {
		Context con = new Context("<inventory>", parent);
		
		con.set("give", new BuildInFunction("give", "item", "num") {
			private static final long serialVersionUID = 1L;
			protected Object executeFunction(Context context, RTResult res, Value... args) {
				res.register(checkArgumentTypes(res, args, StringValue.class, Number.class));
				if(res.shouldReturn()) return res;
				
				ItemStack is = new ItemStack(Material.valueOf(context.string("item").toUpperCase()), (int) context.number("num"));
				inventory.addItem(is);
				return res.success(generateItemObject(is, con));
			}
		});
		
		con.set("remove", new BuildInFunction("remove", "item", "num") {
			private static final long serialVersionUID = 1L;
			protected Object executeFunction(Context context, RTResult res, Value... args) {
				res.register(checkArgumentTypes(res, args, StringValue.class, Number.class));
				if(res.shouldReturn()) return res;
				
				ItemStack is = new ItemStack(Material.valueOf(context.string("item").toUpperCase()), (int) context.number("num"));
				inventory.remove(is);
				return res.success(Number.NULL);
			}
		});
		
		con.set("contains", new BuildInFunction("contains", "item", "num") {
			private static final long serialVersionUID = 1L;
			protected Object executeFunction(Context context, RTResult res, Value... args) {
				res.register(checkArgumentTypes(res, args, StringValue.class, Number.class));
				if(res.shouldReturn()) return res;
				
				ItemStack is = new ItemStack(Material.valueOf(context.string("item").toUpperCase()), (int) context.number("num"));
				return res.success(inventory.contains(is)?Number.TRUE:Number.FALSE);
			}
		});
		
		con.set("clear", new BuildInFunction("clear") {
			private static final long serialVersionUID = 1L;
			protected Object executeFunction(Context context, RTResult res, Value... args) {
				inventory.clear();
				return res.success(Number.NULL);
			}
		});
		
		return new ObjectValue(con);
	}
	
	public static ObjectValue generateItemObject(ItemStack itemStack, Context parent) {
		Context con = new Context("<item>", parent);
		
		con.set("amount", itemStack.getAmount(), (e) -> itemStack.setAmount((int)(float)e), () -> itemStack.getAmount());
		con.set("name", itemStack.getItemMeta().getDisplayName(), (e) -> {
			ItemMeta itemMeta = itemStack.getItemMeta();
			itemMeta.setDisplayName(e+"");
			itemStack.setItemMeta(itemMeta);
		}, () -> itemStack.getItemMeta().getDisplayName());
		
		con.set("addEnchant", new BuildInFunction("addEnchant", "enchant", "level") {
			private static final long serialVersionUID = 1L;
			protected Object executeFunction(Context context, RTResult res, Value... args) {
				res.register(checkArgumentTypes(res, args, StringValue.class, Number.class));
				if(res.shouldReturn()) return res;
				
				itemStack.addEnchantment(Enchantment.getByName(context.string("enchant")), (int) context.number("level"));
				return res.success(Number.NULL);
			}
		});
		
		return new ObjectValue(con);
	}
	
}
