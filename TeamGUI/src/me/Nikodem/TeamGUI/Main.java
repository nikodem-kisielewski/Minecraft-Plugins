package me.Nikodem.TeamGUI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.ChatColor;

public class Main extends JavaPlugin implements Listener {
	
	Map<String, Long> cooldowns = new HashMap<String, Long>();
	
	public Inventory inv;

		@Override
		public void onEnable() {
			this.getServer().getPluginManager().registerEvents(this, this);
			createInv();
		}

		@Override
		public void onDisable() {

		}

		public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
			if (label.equalsIgnoreCase("changeteam")) {
				if (!(sender instanceof Player)) {
					sender.sendMessage("Sorry console, you can't do that.");
					return true;
				}
			
				Player player = (Player) sender;
				
				// checking for the cooldown
				if (cooldowns.containsKey(player.getName())) {
					// player is inside the hashmap
					if (cooldowns.get(player.getName()) > System.currentTimeMillis()) {
						// they still have time left in the cooldown
						long timeLeft = (cooldowns.get(player.getName()) - System.currentTimeMillis()) / 60000;
						player.sendMessage("You must wait " + timeLeft + " minutes before changing teams again.");
						return true;
					}
				}
				
				// opening the GUI
				player.openInventory(inv);
				
				// creating a cooldown
				cooldowns.put(sender.getName(), System.currentTimeMillis() + (300*1000));
				
				return true;
			
			}
			return false;
		}
		
		@EventHandler()
		public void onClick(InventoryClickEvent event) {
			
			if (!(event.getInventory().equals(inv))) {
				return;
			}
			
			if (event.getCurrentItem() == null) {
				return;
			}
			
			if (event.getCurrentItem().getItemMeta() == null) {
				return;
			}
			
			if (event.getCurrentItem().getItemMeta().getDisplayName() == null) {
				return;
			}
			
			// in this case, the player will not grab the item, it will only be clicked
			event.setCancelled(true);
			
			Player player = (Player) event.getWhoClicked();
			
			if (event.getSlot() == 0 && event.getCurrentItem().getType() == Material.BLUE_CONCRETE) {
				// Blue team
				ItemStack[] armor = player.getEquipment().getArmorContents();
				armor = changeColor(armor, Color.BLUE);
				player.getEquipment().setArmorContents(armor);
				player.sendMessage(ChatColor.GOLD + "You changed your team!");
				player.closeInventory();
			}
			
			if (event.getSlot() == 1 && event.getCurrentItem().getType() == Material.RED_CONCRETE) {
				// Red team
				ItemStack[] armor = player.getEquipment().getArmorContents();
				armor = changeColor(armor, Color.RED);
				player.getEquipment().setArmorContents(armor);
				player.sendMessage(ChatColor.GOLD + "You changed your team!");
				player.closeInventory();
			}
			
			if (event.getSlot() == 2 && event.getCurrentItem().getType() == Material.LIME_CONCRETE) {
				// Green team
				ItemStack[] armor = player.getEquipment().getArmorContents();
				armor = changeColor(armor, Color.LIME);
				player.getEquipment().setArmorContents(armor);
				player.sendMessage(ChatColor.GOLD + "You changed your team!");
				player.closeInventory();
			}
			
			if (event.getSlot() == 3 && event.getCurrentItem().getType() == Material.ORANGE_CONCRETE) {
				// Orange team
				ItemStack[] armor = player.getEquipment().getArmorContents();
				armor = changeColor(armor, Color.ORANGE);
				player.getEquipment().setArmorContents(armor);
				player.sendMessage(ChatColor.GOLD + "You changed your team!");
				player.closeInventory();
			}
			
			if (event.getSlot() == 4 && event.getCurrentItem().getType() == Material.PURPLE_CONCRETE) {
				// Purple team
				ItemStack[] armor = player.getEquipment().getArmorContents();
				armor = changeColor(armor, Color.PURPLE);
				player.getEquipment().setArmorContents(armor);
				player.sendMessage(ChatColor.GOLD + "You changed your team!");
				player.closeInventory();
			}
			
			if (event.getSlot() == 5 && event.getCurrentItem().getType() == Material.CYAN_CONCRETE) {
				// Cyan team
				ItemStack[] armor = player.getEquipment().getArmorContents();
				armor = changeColor(armor, Color.AQUA);
				player.getEquipment().setArmorContents(armor);
				player.sendMessage(ChatColor.GOLD + "You changed your team!");
				player.closeInventory();
			}
			
			if (event.getSlot() == 6 && event.getCurrentItem().getType() == Material.BLACK_CONCRETE) {
				// Black team
				ItemStack[] armor = player.getEquipment().getArmorContents();
				armor = changeColor(armor, Color.BLACK);
				player.getEquipment().setArmorContents(armor);
				player.sendMessage(ChatColor.GOLD + "You changed your team!");
				player.closeInventory();
			}
			
			if (event.getSlot() == 8  && event.getCurrentItem().getType() == Material.BARRIER) {
				// Exit button
				player.closeInventory();
			}
			
			return;
			
		}
		
		public ItemStack[] changeColor(ItemStack[] armor, Color color) {
			
			for (ItemStack item : armor) {
				try {
					
					if (item.getType() == Material.LEATHER_BOOTS || item.getType() == Material.LEATHER_CHESTPLATE || 
							item.getType() == Material.LEATHER_HELMET || item.getType() == Material.LEATHER_LEGGINGS) {
						// if player is wearing leather armor, we are changing the color
						LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
						meta.setColor(color);
						item.setItemMeta(meta);
					}
				} catch (Exception e) {
					// do nothing if player is not wearing leather armor
				}
			}
			
			return armor;
		}
		
		public void createInv() {
			inv = Bukkit.createInventory(null, 9,ChatColor.GOLD + "" + ChatColor.BOLD + "Select Team");
			
			ItemStack item = new ItemStack(Material.BLUE_CONCRETE);
			ItemMeta meta = item.getItemMeta();
			
			// Blue Team
			meta.setDisplayName(ChatColor.DARK_BLUE + "Blue Team");
			List<String> lore = new ArrayList<String>();
			lore.add(ChatColor.GRAY + "Click to join team!");
			meta.setLore(lore);
			item.setItemMeta(meta);
			inv.setItem(0, item);
			
			// Red Team
			item.setType(Material.RED_CONCRETE);
			meta.setDisplayName(ChatColor.DARK_RED + "Red Team");
			item.setItemMeta(meta);
			inv.setItem(1, item);
			
			// Green Team
			item.setType(Material.LIME_CONCRETE);
			meta.setDisplayName(ChatColor.GREEN + "Green Team");
			item.setItemMeta(meta);
			inv.setItem(2, item);
			
			// Orange Team
			item.setType(Material.ORANGE_CONCRETE);
			meta.setDisplayName(ChatColor.GOLD + "Orange Team");
			item.setItemMeta(meta);
			inv.setItem(3, item);
			
			// Purple Team
			item.setType(Material.PURPLE_CONCRETE);
			meta.setDisplayName(ChatColor.DARK_PURPLE + "Purple Team");
			item.setItemMeta(meta);
			inv.setItem(4, item);
			
			// Cyan Team
			item.setType(Material.CYAN_CONCRETE);
			meta.setDisplayName(ChatColor.BLUE + "Cyan Team");
			item.setItemMeta(meta);
			inv.setItem(5, item);
			
			// Black Team
			item.setType(Material.BLACK_CONCRETE);
			meta.setDisplayName(ChatColor.DARK_GRAY + "Black Team");
			item.setItemMeta(meta);
			inv.setItem(6, item);
			
			// Exit Button
			item.setType(Material.BARRIER);
			meta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "Close menu");
			lore.clear();
			meta.setLore(lore);
			item.setItemMeta(meta);
			inv.setItem(8, item);
			
			}
		
}

















