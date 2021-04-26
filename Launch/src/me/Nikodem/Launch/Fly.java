package me.Nikodem.Launch;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class Fly implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (label.equalsIgnoreCase("launch") || label.equalsIgnoreCase("lch")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage("You can't fly, silly");
				return true;
			}
			
			Player player = (Player) sender;
			
			if (!(player.hasPermission("launch.use"))) {
				player.sendMessage(ChatColor.RED + "You cannot use this command!");
				return true;
			}
			
			if (args.length == 0) {
				player.sendMessage(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD +  "Weeeeeeeeeeeee!");
				player.setVelocity(player.getLocation().getDirection().multiply(2).setY(2));
				return true;
			}
			
			if (isNum(args[0])) {
				player.sendMessage(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD +  "Weeeeeeeeeeeee!");
				int mult = Integer.parseInt(args[0]);
				player.setVelocity(player.getLocation().getDirection().multiply(mult).setY(2));
				return true;
			}
			player.sendMessage(ChatColor.DARK_RED + "Usage: /launch [integer number]");
			return true;
		}
		return false;
	}
	
	public boolean isNum(String num) {
		try {
			Integer.parseInt(num);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
}