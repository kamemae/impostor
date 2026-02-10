package me.kamemae.impostor.commands.UtilityCommands;
import me.kamemae.impostor.managers.GameManager;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.command.Command;

public class Wherami implements CommandExecutor {
    private final GameManager gameManager;
    public Wherami(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(gameManager.isGameRunning() && sender instanceof Player player && player.getGameMode() != GameMode.SPECTATOR) {
            Location location = player.getLocation();
            int x = location.getBlockX();
            int y = location.getBlockY();
            int z = location.getBlockZ();
            String world = location.getWorld().getName();
            Bukkit.broadcastMessage(sender.getName() + " is located on [" + x + ", " + y + ", " + z + "] in " + world + ".");
        }

        return true;
    }
    
}
