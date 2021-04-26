package me.Nikodem.NewBoots;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.ChatColor;

public class Main extends JavaPlugin implements Listener{
	
	@Override
	public void onEnable() {
		this.getServer().getPluginManager().registerEvents(this, this);
	}

	@Override
	public void onDisable() {

	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (label.equalsIgnoreCase("newboots")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage("Sorry console I can't give you any items.");
				return true;
			}
			Player player = (Player) sender;
			if (!(player.hasPermission("newboots.use"))) {
				player.sendMessage(ChatColor.RED + "You cannot use this command!");
				return true;
			}
			if (player.getInventory().firstEmpty() == -1) {
				Location loc = player.getLocation();
				World world = player.getWorld();
				 world.dropItemNaturally(loc, getItem());
				 player.sendMessage(ChatColor.GOLD + "The gods have given you one of their gifts.");
				 return true;
			}
			player.getInventory().addItem(getItem());
			player.sendMessage(ChatColor.GOLD + "The gods have given you one of their gifts.");
			return true;
		}

		return false;
	}
	
	public ItemStack getItem() {
		
		ItemStack boots = new ItemStack(Material.DIAMOND_BOOTS);
		ItemMeta meta = boots.getItemMeta();
		
		meta.setDisplayName(ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "Boots of Leaping");
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.GOLD + "The gods used these boots to reach the heavens.");
		meta.setLore(lore);
		
		meta.addEnchant(Enchantment.PROTECTION_FALL, 1, true);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		meta.setUnbreakable(true);
		
		boots.setItemMeta(meta);
		
		return boots;
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		event.getPlayer().setAllowFlight(true);
	}
	
	@EventHandler
	public void onDoubleJump(PlayerToggleFlightEvent event) {
		Player player = (Player) event.getPlayer();
		if (player.getGameMode() != GameMode.CREATIVE) {
			event.setCancelled(true);
		}
		Block block = player.getWorld().getBlockAt(player.getLocation().subtract(0, 2, 0));
		if (player.getInventory().getBoots() != null) 
			if (player.getInventory().getBoots().getItemMeta().getDisplayName().contains("Boots of Leaping"))
				if (player.getInventory().getBoots().getItemMeta().hasLore())
					if (!block.getType().equals(Material.AIR)) {
						player.setVelocity(player.getLocation().getDirection().multiply(0.5).setY(0.5));
					}
	}
	
	@EventHandler
	public void onFall(EntityDamageEvent event) {
		if (event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			if (event.getCause() == DamageCause.FALL) {
				if (player.getInventory().getBoots() != null) 
					if (player.getInventory().getBoots().getItemMeta().getDisplayName().contains("Boots of Leaping"))
						if (player.getInventory().getBoots().getItemMeta().hasLore()) {
							event.setCancelled(true);
						}
			}
		}
	}
}


















