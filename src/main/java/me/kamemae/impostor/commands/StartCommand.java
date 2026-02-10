package me.kamemae.impostor.commands;
import me.kamemae.impostor.managers.CountdownManager;
import me.kamemae.impostor.managers.GameManager;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.Command;


public class StartCommand implements CommandExecutor {
    private final GameManager gameManager;
    private final CountdownManager countdownManager;

    public StartCommand(GameManager gameManager, CountdownManager countdownManager) {
        this.countdownManager = countdownManager;
        this.gameManager = gameManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) { 
        if(gameManager.isGameRunning()) return true;

        countdownManager.startCountdown(10, () -> {
            gameManager.startGame();
        });

        return true;
    }
}
