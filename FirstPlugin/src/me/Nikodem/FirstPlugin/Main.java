package me.Nikodem.FirstPlugin;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
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
		if (label.equalsIgnoreCase("hello")) {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				if (player.hasPermission("hello.use")) {
					player.sendMessage("What's up loser");
					return true;
				} else {
					player.sendMessage(ChatColor.RED + "You don't have permission >:(");
					return true;
				}
			} else {
				sender.sendMessage("Hey cutie ;)");
				return true;
			}
		}
		
		return false;
	}
	
	@EventHandler
	public void onPlayerJoinEvent(PlayerJoinEvent event) {
		if (!event.getPlayer().hasPlayedBefore()) {
			event.getPlayer().sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "Welcome to the server!");
			for (Player p : Bukkit.getOnlinePlayers()) {
				if (!p.equals(event.getPlayer())) {
					p.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + 
							"Please welcome " + event.getPlayer().getName() + " to the server!");
				}
			}
		} else {
			event.getPlayer().sendMessage(ChatColor.LIGHT_PURPLE + "Welcome back to the server!");
		}
	}
	
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		for (Player p : Bukkit.getOnlinePlayers()) {
			p.sendMessage(ChatColor.RED + "Haha look at this loser, he died!");
		}
	}
}
