package me.kamemae.impostor.commands;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.ChatColor;



public class AboutCommand implements CommandExecutor {
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        sender.sendMessage("========================================");
        sender.sendMessage(ChatColor.RED + "ඞ IMPOSTOR ඞ");
        sender.sendMessage("Plugin made by " + ChatColor.UNDERLINE + "kamemae");
        sender.sendMessage("ps. Mateusz to jest moj nick na github");
        sender.sendMessage("========================================");
        sender.sendMessage("");

        return true;
    }
    
}
