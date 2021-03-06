package me.Nikodem.GodTrident;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class TridentEvents implements Listener {
	
	static Main plugin;
	public TridentEvents(Main instance) {
		plugin = instance;
	}
	
	public List<String> list = new ArrayList<String>();
	
	@EventHandler()
	public void onClick(PlayerInteractEvent event) {
		if (event.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.TRIDENT))
			if (event.getPlayer().getInventory().getItemInMainHand().getItemMeta().hasLore()) {
				Player player = (Player) event.getPlayer();
				if (event.getAction() == Action.RIGHT_CLICK_AIR) {
					if (!(list.contains(player.getName())))
						list.add(player.getName());
					return;
				}
				if (event.getAction() == Action.LEFT_CLICK_AIR) {
					player.launchProjectile(Fireball.class);
				}
				if (list.contains(event.getPlayer().getName())) {
					list.remove(event.getPlayer().getName());
			}
		}
	}
	
	@EventHandler()
	public void onLand(ProjectileHitEvent event) {
		if (event.getEntityType() == EntityType.TRIDENT) {
			if (event.getEntity().getShooter() instanceof Player) {
				Player player = (Player) event.getEntity().getShooter();
				if (list.contains(player.getName())) {
					Location loc = event.getEntity().getLocation();
					loc.setY(loc.getY() + 1);
					
					for (int i = 1; i < 4; i++) {
						loc.getWorld().spawnEntity(loc, EntityType.DROWNED);
						loc.setX(loc.getX() + i);
					}
					}
					
				}
			}
		}
	}
