package me.kamemae.impostor.commands.UtilityCommands;
import me.kamemae.impostor.managers.GameManager;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;

public class Lost implements CommandExecutor {
    private final GameManager gameManager;
    public Lost(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @Override 
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(gameManager.isGameRunning() && sender instanceof Player player && player.getGameMode() != GameMode.SPECTATOR) {
            Bukkit.broadcastMessage(sender.getName() + " is lost");
        }
        return true;
    }
}
