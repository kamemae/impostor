package me.kamemae.impostor.listeners;
import me.kamemae.impostor.Main;


import net.md_5.bungee.api.ChatColor;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.entity.Player;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;

import java.util.List;

public class DeathListener implements Listener  {
    private final Main plugin;

    public DeathListener(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();

        if(plugin.getImpostors().contains(player)) {
            Bukkit.getScheduler().runTaskLater(plugin, () -> plugin.giveTrackingCompass(player), 1L);
        }
    }


    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        event.setDeathMessage(null);
        Player ded = event.getEntity();

        // if impostor compass skibidi
        event.getDrops().removeIf(item -> item != null && item.getType() == Material.COMPASS);

        ded.getServer().broadcastMessage(ChatColor.RED + "" + ChatColor.BOLD + "" + ded.getName() + " died");
        List<Player> impostors = plugin.getImpostors();

        if(plugin.getRunners().contains(ded)) {
            plugin.getRunners().remove(ded);
        }

        if(impostors.contains(ded)) {
            ded.setGameMode(GameMode.SURVIVAL);
        } else {
            ded.setGameMode(GameMode.SPECTATOR);
        }

        if(plugin.getRunners().isEmpty() && !plugin.getImpostors().isEmpty()) {
            for(Player player : Bukkit.getOnlinePlayers()) {
                player.sendTitle(ChatColor.RED + "IMPOSTORS WIN!", "0 runners left", 10, 100, 20);
            }
        }
    }
}