package me.kamemae.impostor.commands.CharacterCommands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.kamemae.impostor.managers.GameManager;
import net.md_5.bungee.api.ChatColor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Impersonate implements CommandExecutor {
    private final GameManager gameManager;
    public Impersonate(GameManager gameManager) {
        this.gameManager = gameManager;
    }
    private boolean wasUsed = false;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(gameManager.isGameRunning() && sender instanceof Player player && player == gameManager.getImpersonator()) {
            if(!wasUsed) {
                Bukkit.broadcastMessage( ChatColor.RED + "" + ChatColor.BOLD + args[0] + " died");
                wasUsed = true;
            } else {
                Bukkit.broadcastMessage( ChatColor.RED + "" + ChatColor.BOLD + "Impersonator tried to fake-kill player again.");
            }
        }
        return true;
    }

    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();
        if(args.length == 1) {
            completions = Bukkit.getOnlinePlayers().stream().map(Player::getName).filter(name -> name.toLowerCase().startsWith(args[0].toLowerCase())).collect(Collectors.toList());
        }
        return completions;
    }
}
