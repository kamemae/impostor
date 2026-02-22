package me.kamemae.impostor.listeners;
import me.kamemae.impostor.managers.GameManager;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CommandsListener implements Listener {
    private final GameManager gameManager;
    public CommandsListener(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        if(gameManager.isGameRunning()) {
            String msg = event.getMessage().toLowerCase().split(" ")[0];

            if( msg.equals("/msg") || 
                msg.equals("/tell") || 
                msg.equals("/w") || 
                msg.equals("/me")) {
            
                    event.setCancelled(true);
                    event.getPlayer().sendMessage("Chat is disabled during the game");
            }
        }
    }
}
