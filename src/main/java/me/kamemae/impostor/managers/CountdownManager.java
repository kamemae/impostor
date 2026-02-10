package me.kamemae.impostor.managers;
import me.kamemae.impostor.Main;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.ArrayList;


public class CountdownManager {
    private final Main plugin;
    public CountdownManager(Main plugin) {
        this.plugin = plugin;
    }

    public void startCountdown(int secs, Runnable onFinish) {
        ArrayList<Player> players = new ArrayList<Player>(Bukkit.getOnlinePlayers());
        for(int i = secs; i > 0; i--) {
            int time = i;
            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                Bukkit.broadcastMessage("Game starts in " + time);

                for(Player player : players) {
                    player.sendTitle("Game starts in " + time, "We will try to start the game with " + plugin.getImpostorCount() + " impostor" + (plugin.getImpostorCount() > 1 ? "s" : "") + "!", 10, 100, 10);
                    player.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5F, 1.0F);
                }
            }, 20L * (secs - i));
        }
        Bukkit.getScheduler().runTaskLater(plugin, onFinish, (20L * secs));
    }
}
