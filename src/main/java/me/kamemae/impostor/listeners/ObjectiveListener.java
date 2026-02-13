package me.kamemae.impostor.listeners;
import me.kamemae.impostor.managers.GameManager;

import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.entity.EntityType;

public class ObjectiveListener implements Listener {
    private final GameManager gameManager;

    public ObjectiveListener(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @EventHandler
    public void onDragonDeath(EntityDeathEvent event) {
        if(event.getEntity().getType() == EntityType.ENDER_DRAGON && gameManager.isGameRunning()) {
            gameManager.stopGame(1);
        }
    }
}