package me.kamemae.impostor.listeners;
import me.kamemae.impostor.Main;

import org.bukkit.event.Listener;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

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
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        if(plugin.isGameStarted()) {
            event.setQuitMessage(null);
            if(plugin.getImpostors().contains(event.getPlayer())) {
                plugin.getImpostors().remove(event.getPlayer());
                if(plugin.getImpostors().isEmpty()) {
                    for(Player player : Bukkit.getOnlinePlayers()) {
                        player.sendTitle("All impostors have left!", "", 10, 70, 10);
                        player.playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_DEATH, 1.0f, 1.0f);
                        plugin.endGame();
                    }
                } else {
                    for(Player player : Bukkit.getOnlinePlayers()) {
                        player.sendTitle("An impostor has left!", "", 10, 70, 10);
                        player.playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 1.0f, 1.0f);
                    }
                }
            }
        }

    }
}