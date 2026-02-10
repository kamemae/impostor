package me.kamemae.impostor.listeners;
import me.kamemae.impostor.managers.GameManager;
import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class ObjectiveListener implements Listener {
    private final GameManager gameManager;

    public ObjectiveListener(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @EventHandler
    public void onDragonDeath(EntityDeathEvent event) {
        if(event.getEntity().getType() == EntityType.ENDER_DRAGON && gameManager.isGameRunning()) {
            Bukkit.broadcastMessage("Dragon has been slain");
            for(Player player : Bukkit.getOnlinePlayers()) {
                player.sendTitle(ChatColor.GREEN + "INNOCENTS WON", "Ender dragon has been slain", 10, 100, 10);
            }
            gameManager.stopGame();
        }
    }
}