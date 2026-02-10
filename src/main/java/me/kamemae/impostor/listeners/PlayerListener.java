package me.kamemae.impostor.listeners;
import me.kamemae.impostor.managers.GameManager;

import org.bukkit.event.Listener;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {
    private final GameManager gameManager;
    public PlayerListener(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        // Handle player join event
        if(gameManager.isGameRunning()) {
            event.getPlayer().sendMessage("A game is currently active. Please wait for the next round to join.");
            event.getPlayer().setGameMode(org.bukkit.GameMode.SPECTATOR);
        }
    }
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        if(gameManager.isGameRunning()) {
            event.setQuitMessage(null);
            if(gameManager.getImpostorsList().contains(event.getPlayer())) {
                gameManager.getImpostorsList().remove(event.getPlayer());
                if(gameManager.getImpostorsList().isEmpty()) {
                    for(Player player : Bukkit.getOnlinePlayers()) {
                        player.sendTitle("All impostors have left!", "", 10, 70, 10);
                        player.playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_DEATH, 1.0f, 1.0f);
                        gameManager.stopGame();
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