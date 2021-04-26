package me.Nikodem.InvisCreepers;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Main extends JavaPlugin implements Listener {

	@Override
	public void onEnable() {
		this.getServer().getPluginManager().registerEvents(this, this);
	}

	@Override
	public void onDisable() {

	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		return false;
	}
	
	@EventHandler
	public void onEntitySpawn(CreatureSpawnEvent event) {
		if (event.getEntity() instanceof Creeper) {
			Creeper creep = (Creeper) event.getEntity();
			creep.setPowered(true);
			creep.setInvisible(true);
		}
		if (event.getEntity() instanceof Skeleton) {
			event.setCancelled(true);
		}
	}
}

