package me.kamemae.impostor.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CommandsListener implements Listener {
    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        String msg = event.getMessage().toLowerCase();

        if(msg.startsWith("/msg") || msg.startsWith("/tell") || msg.startsWith("/w") || msg.startsWith("/tellraw") || msg.startsWith("/me")) {
            event.setCancelled(true);
            event.getPlayer().sendMessage("Direct messages are disabled during the game");
        }
    }
}
