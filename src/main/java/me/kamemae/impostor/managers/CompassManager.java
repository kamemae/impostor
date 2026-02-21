package me.kamemae.impostor.managers;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.ChatColor;
public class CompassManager {
    private final GameManager gameManager;
    private final JavaPlugin plugin;

    public CompassManager(GameManager gameManager, JavaPlugin plugin) {
        this.gameManager = gameManager;
        this.plugin = plugin;
        startLocatorBarUpdater(); 
    }

    private double getRelativeAngle(Player from, Player to) {
        double dx = to.getLocation().getX() - from.getLocation().getX();
        double dz = to.getLocation().getZ() - from.getLocation().getZ();

        double targetAngle = Math.toDegrees(Math.atan2(-dx, dz));
        double yaw = from.getLocation().getYaw();
        double angle = targetAngle - yaw;

        while (angle > 180) angle -= 360;
        while (angle < -180) angle += 360;

        return angle;
    }

    private String buildLocatorBar(Player impostor) {
        int barSize = 41;
        char[] bar = new char[barSize];
        for(int i = 0; i < barSize; i++) bar[i] = '-';

        for(Player player : gameManager.getInnocentsList()) {
            if(!player.isOnline()) continue;

            double angle = getRelativeAngle(impostor, player);

            if(angle > 90) bar[barSize - 1] = '>';
            else if(angle < -90) bar[0] = '<';
            else {
                int index = (int) ((angle + 180) / 360 * (barSize - 1));
                if(index >= 0 && index < barSize) { 
                    ChatColor color = ChatColor.WHITE;
                    String worldName = player.getWorld().getName().toLowerCase();
                    if(worldName.contains("nether")) color = ChatColor.RED;
                    else if(worldName.contains("end")) color = ChatColor.LIGHT_PURPLE;
                    else color = ChatColor.GREEN;

                    bar[index] = '◆';

                    StringBuilder sb = new StringBuilder(new String(bar));
                    sb.setCharAt(index, '◆');
                    sb.insert(index, color);
                    sb.insert(index + color.toString().length() + 1, ChatColor.WHITE);
                    bar = sb.toString().toCharArray();
                }
            }
        }

        return "[" + new String(bar) + "]";
    }

    private void startLocatorBarUpdater() {
        Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            for(Player impostor : gameManager.getImpostorsList()) {
                if(!impostor.isOnline()) continue;

                String bar = buildLocatorBar(impostor);

                impostor.spigot().sendMessage(
                    net.md_5.bungee.api.ChatMessageType.ACTION_BAR,
                    new net.md_5.bungee.api.chat.TextComponent(bar)
                );
            }
        }, 0L, 1L);
    }
}
