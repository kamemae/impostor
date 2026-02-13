package me.kamemae.impostor.managers;

import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.GameMode;
import org.bukkit.GameRule;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import net.md_5.bungee.api.ChatColor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class GameManager {
    private TimerManager timerManager;
    public void SetTimer(TimerManager timerManager) {
        this.timerManager = timerManager;
    }

    private int gameTimer;
    public void setGameTimer(int mins) {
        gameTimer = mins;
    }

    private int impostorCount = 1;
    public void setImpostorCount(int count) {
        impostorCount = count;
    }
    public int getImpostorCount() {
        return impostorCount;
    }

    private boolean gameStarted = false;
    public boolean isGameRunning() {
        return gameStarted;
    }
    public void stopGame(int winCase) {
        String tittlemsg = "";
        String submsg = "";
        ChatColor color = ChatColor.WHITE;
        switch(winCase) {
            case 0:
                tittlemsg = "IMPOSTORS WON!";
                submsg = "All innocents died";
                color = ChatColor.RED;
                break;

            case 1:
                tittlemsg = "INNOCENTS WON!";
                submsg = "Ender Dragons has been Slain";
                color = ChatColor.GREEN;
                break;

            case 2:
                tittlemsg = "INNOCENTS WON?";
                submsg = "Impostors left the game";
                color = ChatColor.GREEN;
                break;

            case 3: 
                tittlemsg = "IMPOSTORS WON?";
                submsg = "Time's up!";
                color = ChatColor.RED;
                break;

            default:
                break;
        }
        Bukkit.broadcastMessage(submsg + " " + tittlemsg);
        for(Player player : Bukkit.getOnlinePlayers()) {
            player.sendTitle(color + tittlemsg, submsg, 10, 100, 10);
        }
        gameStarted = false;
        return;
    }

    private final List<Player> impostors = new ArrayList<>();
    public List<Player> getImpostorsList() {
        return impostors;
    }

    private final List<Player> innocents = new ArrayList<>();
    public List<Player> getInnocentsList() {
        return innocents;
    }

    
    public void startGame() {
        List<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());
        Collections.shuffle(players);

        impostors.clear();
        innocents.clear();

        impostors.addAll(players.subList(0, Math.min(impostorCount, players.size())));
        innocents.addAll(players);
        innocents.removeAll(impostors);


        for(World world : Bukkit.getWorlds()) {
            world.setGameRule(GameRule.SHOW_DEATH_MESSAGES, false);
            world.setGameRule(GameRule.LOCATOR_BAR, false);
            world.setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, false);
            world.setDifficulty(Difficulty.NORMAL);
            world.setTime(0);
        }


        int effectAmp = 255;
        int effectDur = 100;

        for(Player player : players) {
            //PotionEffectType[] effects = { PotionEffectType.DARKNESS, PotionEffectTpye.BLINDNESS };
            player.addPotionEffect(new PotionEffect(PotionEffectType.DARKNESS, effectDur, effectAmp));
            player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, effectDur, effectAmp));
            player.addPotionEffect(new PotionEffect(PotionEffectType.NAUSEA, effectDur, effectAmp));
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, effectDur, effectAmp));

            player.addPotionEffect(new PotionEffect(PotionEffectType.INSTANT_HEALTH, effectDur, effectAmp));
            player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, effectDur, effectAmp));
            player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, effectDur, effectAmp));

            player.playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 1.0f, 1.0f);

            player.setGameMode(GameMode.SURVIVAL);
            player.getInventory().clear();
            player.getInventory().setArmorContents(null);
            player.getInventory().setItemInOffHand(null);
            player.getEnderChest().clear();

            if(getImpostorsList().contains(player)) {
                player.sendTitle(ChatColor.RED + "IMPOSTOR", "Objective: Kill all innocents", 10, 100, 10);
            } else {
                player.sendTitle(ChatColor.GREEN + "INNOCENT", "Objective: Slay the Ender Dragon", 10, 100, 10);
            }

            player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 2000, 1));
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 2000, 1));
            player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP_BOOST, 2000, 1));
            player.addPotionEffect(new PotionEffect(PotionEffectType.HASTE, 2000, 1));
        }

        timerManager.start(gameTimer);


        Bukkit.broadcastMessage("Game started!");
        gameStarted = true;
    }
}
