package me.kamemae.impostor.listeners;
import me.kamemae.impostor.Main;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class ObjectiveListener implements Listener {
    private final Main plugin;

    public ObjectiveListener(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onDragonDeath(EntityDeathEvent event) {
        if(event.getEntity().getType() == EntityType.ENDER_DRAGON) {
            if(plugin.isGameStarted()) {
                Bukkit.broadcastMessage("Dragon has been slain");
                for(Player player : Bukkit.getOnlinePlayers()) {
                    player.sendTitle("Runners won", "Ender dragon has been slain", 10, 100, 10);
                    plugin.endGame();
                }
            }

        }
    }
}
