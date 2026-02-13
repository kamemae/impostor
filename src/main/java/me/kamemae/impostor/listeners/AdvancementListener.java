package me.kamemae.impostor.listeners;

import org.bukkit.event.Listener;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;

import me.kamemae.impostor.managers.GameManager;
import net.md_5.bungee.api.ChatColor;

public class AdvancementListener implements Listener {
    private final GameManager gameManager;
    public AdvancementListener(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @EventHandler
    public void onAdvancement(PlayerAdvancementDoneEvent event) {
        if(!gameManager.isGameRunning()) return;
        if(event.getPlayer().getGameMode() == GameMode.SPECTATOR) return;

        String fullAdvancement = event.getAdvancement().getKey().getKey();
        if(fullAdvancement.startsWith("recipes/")) return;

        String name = formatName(fullAdvancement);
        if(name.startsWith("Root")) return;


        String msg = ChatColor.WHITE + event.getPlayer().getName() + " has made the advancement " + ChatColor.GREEN + "[" + name + "]";
        for(Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage(msg);
        }

    }
    private String formatName(String key) {
        String clean = key.substring(key.lastIndexOf("/") + 1);
        clean = clean.replace("_", " ");
        return Character.toUpperCase(clean.charAt(0)) + clean.substring(1);
    }
}
