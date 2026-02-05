package me.kamemae.impostor.listeners;

import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {

    private boolean chatEnabled = true;
    public ChatListener(boolean enabled) {
        this.chatEnabled = enabled;
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        if (!chatEnabled) {
            event.setCancelled(true);
            event.getPlayer().sendMessage("Chat is disabled during the game");
        }
    }
}