package me.kamemae.impostor.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import me.kamemae.impostor.managers.GameManager;
import net.md_5.bungee.api.ChatColor;

public class JesterCommand implements CommandExecutor {
    private final GameManager gameManager;
    public JesterCommand(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(gameManager.isGameRunning()) return true;

        gameManager.setJesterStatus(!gameManager.getJesterStatus());
        sender.sendMessage("Jester is: " + ChatColor.BOLD + (gameManager.getJesterStatus() ? "ENABLED" : "DISABLED"));

        return true;
    }
}
