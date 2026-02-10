package me.kamemae.impostor.managers;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CompassMeta;


public class CompassManager {
    private final GameManager gameManager;
    public CompassManager(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public void updateCompass(Player impostor) {
        Player nearest = getNearestRunner(impostor);
        
        if(nearest == null) return;


        for(ItemStack item : impostor.getInventory()) {
            if(item == null || item.getType() != Material.COMPASS) continue;


            CompassMeta meta = (CompassMeta)item.getItemMeta();
            meta.setLodestone(nearest.getLocation());
            meta.setLodestoneTracked(false);
            item.setItemMeta(meta);
        }
    }

    private Player getNearestRunner(Player impostor) {
        Player nearest = null;
        double best = Double.MAX_VALUE;

        for(Player innocent : gameManager.getInnocentsList()) {
            double distance = impostor.getLocation().distanceSquared(innocent.getLocation());
            if(distance < best) {
                best = distance;
                nearest = innocent;
            }
        }

        return nearest;
    }
}
