package me.Nikodem.Gamble;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import io.netty.util.internal.ThreadLocalRandom;
import net.md_5.bungee.api.ChatColor;

public class Main extends JavaPlugin implements Listener {
	
	List<Inventory> invs = new ArrayList<Inventory>();
	
	public static ItemStack[] contents;
	private int itemIndex = 0;
	
	@Override
	public void onEnable() {
		this.getServer().getPluginManager().registerEvents(this, this);
	}

	@Override
	public void onDisable() {

	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (label.equalsIgnoreCase("gamble")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage("Sorry console, you cannot gamble");
				return true;
			}
			Player player = (Player) sender;
			ItemStack fee = new ItemStack(Material.DIAMOND);
			fee.setAmount(2);
			
			if (player.getInventory().getItemInMainHand().getType().equals(Material.DIAMOND) && 
					player.getInventory().getItemInMainHand().getAmount() >= 2) {
				player.getInventory().removeItem(fee);
				// spin the wheel
				spin(player);
				return true;
			}
			player.sendMessage(ChatColor.DARK_RED + "You need 2 diamonds to gamble");
			return true;
		}

		return false;
	}
	
	public void shuffle(Inventory inv) {
		if (contents == null) {
			ItemStack[] items = new ItemStack[30];
			
			items[0] = new ItemStack(Material.COARSE_DIRT, 32);
			items[1] = new ItemStack(Material.DIAMOND, 2);
			items[2] = new ItemStack(Material.LAPIS_LAZULI, 16);
			items[3] = new ItemStack(Material.IRON_HORSE_ARMOR, 1);
			items[4] = new ItemStack(Material.PIG_SPAWN_EGG, 4);
			items[5] = new ItemStack(Material.IRON_INGOT, 32);
			items[6] = new ItemStack(Material.NETHER_STAR, 1);
			items[7] = new ItemStack(Material.QUARTZ_BLOCK, 64);
			items[8] = new ItemStack(Material.SLIME_BLOCK, 16);
			items[9] = new ItemStack(Material.EMERALD, 10);
			items[10] = new ItemStack(Material.COAL, 32);
			items[11] = new ItemStack(Material.DIAMOND, 4);
			items[12] = new ItemStack(Material.ARROW, 64);
			items[13] = new ItemStack(Material.DIAMOND_SWORD, 1);
			items[14] = new ItemStack(Material.CREEPER_SPAWN_EGG, 2);
			items[15] = new ItemStack(Material.CLAY_BALL, 64);
			items[16] = new ItemStack(Material.OAK_LOG, 32);
			items[17] = new ItemStack(Material.COOKIE, 16);
			items[18] = new ItemStack(Material.ANCIENT_DEBRIS, 1);
			items[19] = new ItemStack(Material.GOLD_INGOT, 32);
			items[20] = new ItemStack(Material.ENCHANTING_TABLE, 1);
			items[21] = new ItemStack(Material.DIAMOND_BLOCK, 1);
			items[22] = new ItemStack(Material.CROSSBOW, 1);
			items[23] = new ItemStack(Material.HAY_BLOCK, 16);
			items[24] = new ItemStack(Material.SADDLE, 4);
			items[25] = new ItemStack(Material.MUSIC_DISC_STAL, 1);
			items[26] = new ItemStack(Material.BOW, 1);
			items[27] = new ItemStack(Material.POPPY, 1);
			items[28] = new ItemStack(Material.WOLF_SPAWN_EGG, 2);
			items[29] = new ItemStack(Material.BEEF, 32);
			
			contents = items;
		}
		
		int startingIndex = ThreadLocalRandom.current().nextInt(contents.length);
		
		for (int index = 0; index < startingIndex; index++) {
			for (int itemstacks = 9; itemstacks < 18; itemstacks++) {
				inv.setItem(itemstacks, contents[(itemstacks + itemIndex) % contents.length]);
			}
			itemIndex++;
		}
		
		// customization
		ItemStack item = new ItemStack(Material.HOPPER);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.DARK_GRAY + "|");
		item.setItemMeta(meta);
		inv.setItem(4, item);
		
	}
	
	// the actual spinning
	public void spin(final Player player) {
		Inventory inv = Bukkit.createInventory(null, 27, ChatColor.GOLD + "" + ChatColor.BOLD + "Good luck!");
		shuffle(inv);
		invs.add(inv);
		player.openInventory(inv);
		
		// how long the wheel spins is random
		// between 5 and 15 seconds
		Random r = new Random();
		double seconds = 5.0 + (15.0 - 5) * r.nextDouble();
		
		new BukkitRunnable() {
			double delay = 0;
			int ticks = 0;
			boolean done = false;
			
			public void run() {
				if (done)
					return;
				ticks++;
				delay += 1/(20*seconds);
				if (ticks > delay * 10) {
					ticks = 0;
					
					for (int itemstacks = 9; itemstacks < 18; itemstacks++) 
						inv.setItem(itemstacks, contents[(itemstacks + itemIndex) % contents.length]);
					
					itemIndex++;
					
					if (delay >= 0.5) {
						done = true;
						new BukkitRunnable() {
							public void run() {
								if (player.getInventory().firstEmpty() == -1) {
									ItemStack item = inv.getItem(13);
									Location loc = player.getLocation();
									World world = player.getWorld();
									world.dropItemNaturally(loc, item);
									player.updateInventory();
									player.closeInventory();
									cancel();
								} else {
									ItemStack item = inv.getItem(13);
									player.getInventory().addItem(item);
									player.updateInventory();
									player.closeInventory();
									cancel();
								}
							}
						}.runTaskLater(Main.getPlugin(Main.class), 50);
						cancel();
					}
				}
			}
			
			
		}.runTaskTimer(this, 0, 1);
	}
	
	@EventHandler()
	public void onClick (InventoryClickEvent event) {
		if (!(invs.contains(event.getInventory()))) {
			return;
		}
		event.setCancelled(true);
		return;
	}
	
	
}






















