package me.kamemae.impostor.listeners;
import me.kamemae.impostor.managers.GameManager;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.entity.Player;
import org.bukkit.GameMode;

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
            Player killer = event.getEntity().getKiller();
            if(killer != null) {
                if(gameManager.getJester().equals(killer)) {
                    if(gameManager.getInnocentsList().contains(ded)) {
                        killer.setHealth(0);
                        gameManager.clearJester();
                    }
                } else {
                    if(ded.equals(gameManager.getJester()) && gameManager.getInnocentsList().contains(killer)) {
                        gameManager.stopGame(69);
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