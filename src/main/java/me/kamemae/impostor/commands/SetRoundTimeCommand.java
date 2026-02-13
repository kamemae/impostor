package me.kamemae.impostor.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import me.kamemae.impostor.managers.GameManager;

public class SetRoundTimeCommand implements CommandExecutor {
    private final GameManager gameManager;
    public SetRoundTimeCommand(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(gameManager.isGameRunning()) return true;
        
        if(args.length < 1) {
            sender.sendMessage("Usage: /setGameTimer <num>");
            sender.sendMessage("Where <num> is greater than zero and represents minutes");
            return true;
        }


        int tmp = 90;
        try {
            tmp = Integer.parseInt(args[0]);
            sender.sendMessage("Round time: " + tmp + " minutes.");
            gameManager.setGameTimer(tmp * 60);
        } catch(NumberFormatException ex) {
            sender.sendMessage("Round time: " + tmp + " minutes.");
            gameManager.setGameTimer(tmp * 60);
        }
        return true;
    }
    
}
