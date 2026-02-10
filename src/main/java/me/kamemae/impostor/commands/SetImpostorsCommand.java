package me.kamemae.impostor.commands;
import me.kamemae.impostor.managers.GameManager;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;

public class SetImpostorsCommand implements CommandExecutor {
    private final GameManager gameManager;
    public SetImpostorsCommand(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(gameManager.isGameRunning()) return true;

        if(args.length < 1) {
            sender.sendMessage("Usage: /setimpostors <num>");
            sender.sendMessage("Where <num> is greater than zero");
            return true;
        }

        int tmp = 1;
        try {
            tmp = Integer.parseInt(args[0]);
            if(tmp > Bukkit.getOnlinePlayers().size()) {
                sender.sendMessage("Number of impostors can not be greater than number of players online!");
                return true;
            }
            gameManager.setImpostorCount(tmp);
        } catch(NumberFormatException ex) {
            sender.sendMessage("Number of impostors is not a number!");
        } finally {
            sender.sendMessage("Number of impostors: " + tmp);
        }
        return true;
    }
    
}
