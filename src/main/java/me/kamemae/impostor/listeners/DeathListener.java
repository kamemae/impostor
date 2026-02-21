package me.kamemae.impostor.listeners;
import me.kamemae.impostor.managers.GameManager;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.entity.Player;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;

import java.util.List;


public class DeathListener implements Listener  {
    private final GameManager gameManager;

    public DeathListener(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();


        if(gameManager.getImpostorsList().contains(player)) {
            //Bukkit.getScheduler().runTaskLater(gameManager, () -> gameManager.giveTrackingCompass(player), 1L);
        }
    }


    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        event.setDeathMessage(null);
        Player ded = event.getEntity();
        ded.getServer().broadcastMessage(ChatColor.RED + "" + ChatColor.BOLD + "" + ded.getName() + " died");


        if(gameManager.getJesterStatus() && gameManager.getJester() != null) {
            if(gameManager.getJester().equals(ded)) {
                if(gameManager.getInnocentsList().contains(event.getDamageSource().getDirectEntity())) {
                    gameManager.clearJester();
                    gameManager.stopGame(4);
                }
            } else {
                if(gameManager.getInnocentsList().contains(ded)) {
                    if(gameManager.getJester().equals(event.getDamageSource().getDirectEntity())) {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "kill " + gameManager.getJester().getName());
                        gameManager.clearJester();
                    }
                }
            }
        }


        if(gameManager.getInnocentsList().contains(ded)) {
            gameManager.getInnocentsList().remove(ded);
        }


        if(gameManager.getImpostorsList().contains(ded)) {
            ded.setGameMode(GameMode.SURVIVAL);
        } else {
            ded.setGameMode(GameMode.SPECTATOR);
        }

        if(gameManager.getInnocentsList().isEmpty() && !gameManager.getImpostorsList().isEmpty() && gameManager.isGameRunning()) {
            gameManager.stopGame(0);
        }
    }
}