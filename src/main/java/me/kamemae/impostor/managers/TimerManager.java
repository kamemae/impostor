package me.kamemae.impostor.managers;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class TimerManager {
    private final GameManager gameManager;
    private final JavaPlugin plugin;
    public TimerManager(GameManager gameManager, JavaPlugin plugin) {
        this.gameManager = gameManager;
        this.plugin = plugin;
    }

    private BossBar timer;
    private int timeLeft;
    private int maxTime;
    private int taskId = -1;

    private void buildTimer() {
        timer = Bukkit.createBossBar("Time Left: " + formatTime(timeLeft), BarColor.GREEN, BarStyle.SOLID);

        for(Player player : Bukkit.getOnlinePlayers()) {
            timer.addPlayer(player);
        }
    }

    private void startTimerBarUpdater() {
        taskId = Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            if(!gameManager.isGameRunning()) {
                timer.removeAll();
                if(taskId != -1) {
                    Bukkit.getScheduler().cancelTask(taskId);
                    taskId = -1;
                }
                return;
            }
            if(timeLeft <= 0) {
                timer.setTitle("Time is up!");
                timer.setProgress(0);

                for(Player player : Bukkit.getOnlinePlayers()) {
                    player.playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 1.0f, 1.0f);
                }

                gameManager.stopGame(3);
                return;
            }
            double progress = (double) timeLeft / maxTime;
            timer.setProgress(progress);

            timer.setTitle("Time Left: " + formatTime(timeLeft));

            if(timeLeft <= 30) {
                timer.setColor(BarColor.RED);
            } else if(timeLeft <= 60) {
                timer.setColor(BarColor.YELLOW);
            } else {
                timer.setColor(BarColor.GREEN);
            }

            if(timeLeft <= 10) {
                for(Player player : Bukkit.getOnlinePlayers()) {
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1.0f, 1.5f);
                }
            }
            timeLeft--;

        }, 0L, 20L).getTaskId();
    }

    private String formatTime(int totalSeconds) {
        int hours = totalSeconds / 3600;
        int minutes = (totalSeconds % 3600) / 60;
        int seconds = totalSeconds % 60;

        if(hours > 0) {
            return String.format("%d:%02d:%02d", hours, minutes, seconds);
        } else {
            return String.format("%02d:%02d", minutes, seconds);
        }
    }

    public void start(int seconds) {
        if(taskId != -1) {
            Bukkit.getScheduler().cancelTask(taskId);
        }

        this.maxTime = seconds;
        this.timeLeft = seconds;

        buildTimer();
        startTimerBarUpdater();
    }
}
