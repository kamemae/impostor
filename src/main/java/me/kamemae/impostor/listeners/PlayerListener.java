package me.kamemae.impostor.listeners;
import me.kamemae.impostor.Main;

import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerListener implements Listener {
    private final Main plugin;

    public PlayerListener(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        // Handle player join event
        if(plugin.isGameStarted()) {
            event.getPlayer().sendMessage("A game is currently active. Please wait for the next round to join.");
            event.getPlayer().setGameMode(org.bukkit.GameMode.SPECTATOR);
        }
    }
}