package me.kamemae.impostor.listeners;

import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import me.kamemae.impostor.managers.GameManager;

public class ChatListener implements Listener {
    private final GameManager gameManager;
    public ChatListener(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        if(gameManager.isGameRunning()) {
            event.setCancelled(true);
            event.getPlayer().sendMessage("Chat is disabled during the game");
        }
    }
}