package me.kamemae.impostor;

import me.kamemae.impostor.listeners.PlayerListener;
import me.kamemae.impostor.listeners.ChatListener;
import me.kamemae.impostor.listeners.CommandsListener;
import me.kamemae.impostor.listeners.DeathListener;
import me.kamemae.impostor.listeners.ObjectiveListener;
import me.kamemae.impostor.managers.PluginManager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Difficulty;
import org.bukkit.GameMode;
import org.bukkit.GameRule;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CompassMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.Sound;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main extends JavaPlugin {

    private boolean gameStarted = false;
    public boolean isGameStarted() { return gameStarted; }

    private List<Player> impostors = new ArrayList<>();
    public List<Player> getImpostors() { return impostors; }

    private List<Player> runners = new ArrayList<>();
    public List<Player> getRunners() { return runners; }

    private int impostorCount = 1;

    @Override
    public void onEnable() {
        PluginManager.getInstance().initialize();

        getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
        getServer().getPluginManager().registerEvents(new ChatListener(false), this);
        getServer().getPluginManager().registerEvents(new CommandsListener(), this);
        getServer().getPluginManager().registerEvents(new DeathListener(this), this);
        getServer().getPluginManager().registerEvents(new ObjectiveListener(), this);

        getLogger().info("Impostor enabled");
    }

    @Override
    public void onDisable() {
        getLogger().info("Impostor disabled");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(command.getName().equalsIgnoreCase("about")) {
            sender.sendMessage("========================================");
            sender.sendMessage(ChatColor.RED + "ඞ IMPOSTOR ඞ");
            sender.sendMessage("Plugin made by " + ChatColor.UNDERLINE + "kamemae");
            sender.sendMessage("========================================");
            sender.sendMessage("");
            return true;
        }

        if(command.getName().equalsIgnoreCase("setimpostors")) {
            if(args.length < 1) {
                sender.sendMessage("Usage: /setimpostors <num>");
                sender.sendMessage("Where <num> is greater than zero");
                return true;
            }

            try {
                impostorCount = Integer.parseInt(args[0]);
                sender.sendMessage("Number of impostors: " + ChatColor.RED + ChatColor.BOLD + impostorCount);
            } catch(NumberFormatException event) {
                sender.sendMessage("Number of impostors is not a number!");
            }
            return true;
        }

        if(command.getName().equalsIgnoreCase("start")) {
            if(!gameStarted) {
                gameStarted = true;
                countdown(10, () -> {
                    startGame();
                    sender.sendMessage("Game started!");
                });
            }
            return true;
        }

        // /lost
        if(command.getName().equalsIgnoreCase("lost")) {
            if(gameStarted) {
                Bukkit.broadcastMessage(sender.getName() + " is lost");
            }
            return true;
        }

        // /wherami
        if(command.getName().equalsIgnoreCase("wherami")) {
            if(gameStarted) {
                if(sender instanceof Player player) {
                    Location loc = player.getLocation();
                    int x = loc.getBlockX();
                    int y = loc.getBlockY();
                    int z = loc.getBlockZ();
                    String worldName = loc.getWorld().getName();

                    Bukkit.broadcastMessage(player.getName() + " location [" + x + ", " + y + ", " + z + "] in " + worldName);
                }
            }
            return true;
        }

        return false;
    }

    private void startGame() {
        List<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());
        Collections.shuffle(players);

        impostors.clear();
        for(int i = 0; i < Math.min(impostorCount, players.size()); i++) {
            impostors.add(players.get(i));
        }

        for(World world : Bukkit.getWorlds()) {
            world.setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, false);
            world.setGameRule(GameRule.SHOW_DEATH_MESSAGES, false);
            world.setGameRule(GameRule.LOCATOR_BAR, false);
        }


        runners.clear();
        runners.addAll(players);
        runners.removeAll(impostors);

        World world = Bukkit.getWorlds().get(0);
        world.setDifficulty(Difficulty.NORMAL);
        world.setGameRule(GameRule.SHOW_DEATH_MESSAGES, false);
        world.setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, false);

        int effectDuration = 100;

        for(Player player : players) {
            player.setGameMode(GameMode.SURVIVAL);

            player.addPotionEffect(new PotionEffect(PotionEffectType.DARKNESS, effectDuration, 255));
            player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, effectDuration, 255));
            player.addPotionEffect(new PotionEffect(PotionEffectType.NAUSEA, effectDuration, 255));
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, effectDuration, 255));

            player.addPotionEffect(new PotionEffect(PotionEffectType.INSTANT_HEALTH, effectDuration, 255));
            player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, effectDuration, 255));
            player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, effectDuration, 255));

            if(impostors.contains(player)) {
                player.sendTitle(ChatColor.RED + "" + ChatColor.BOLD + "IMPOSTOR", ChatColor.WHITE + "Objective: Kill all runners", 10, 100, 20);
                giveTrackingCompass(player);
                player.playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 1.0f, 1.0f);
            } else {
                player.sendTitle(ChatColor.GREEN + "" + ChatColor.BOLD + "RUNNER", ChatColor.WHITE + "Objective: Slay the Ender Dragon", 10, 100, 20);
                player.playSound(player.getLocation(), Sound.ENTITY_ALLAY_AMBIENT_WITHOUT_ITEM, 1.0f, 1.0f);
            }
        }

        Bukkit.getScheduler().runTaskTimer(this, () -> {
            for(Player impostor : impostors) {
                updateTrackingCompass(impostor);
            }
        }, 0L, 20L); 
    }

    private void countdown(int secs, Runnable onFinish) {
        for(int i = secs; i > 0; i--) {
            final int timeLeft = i;
            Bukkit.getScheduler().runTaskLater(this, () -> {
                String chatmsg = "Game starting in " + timeLeft + " second" + (timeLeft > 1 ? "s" : "");
                Bukkit.broadcastMessage(chatmsg);

                for(Player player : Bukkit.getOnlinePlayers()) {
                    player.sendTitle(ChatColor.RED + "" + timeLeft, ChatColor.WHITE + "We will try to start the game with " + impostorCount + " impostor" + (impostorCount > 1 ? "s" : ""), 1, 20, 1);
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 1.0f, 1.0f);
                }
            }, 20L * (secs - i));
        }
        Bukkit.getScheduler().runTaskLater(this, onFinish, 20L * secs);
    }

    public void giveTrackingCompass(Player impostor) {
        if(runners.isEmpty()) return;

        ItemStack compass = new ItemStack(Material.COMPASS);
        CompassMeta meta = (CompassMeta) compass.getItemMeta();
        if(meta != null) {
            Player nearest = getNearestRunner(impostor);
            if(nearest != null) {
                meta.setLodestone(nearest.getLocation());
                meta.setLodestoneTracked(false);
                meta.setDisplayName(ChatColor.RED + "Runner Tracker");
                compass.setItemMeta(meta);
                impostor.getInventory().addItem(compass);
            }
        }
    }

    private void updateTrackingCompass(Player impostor) {
        if(runners.isEmpty()) return;

        Player nearest = getNearestRunner(impostor);
        if(nearest == null) return;

        for(ItemStack item : impostor.getInventory().getContents()) {
            if(item != null && item.getType() == Material.COMPASS
                    && item.hasItemMeta()
                    && item.getItemMeta().hasDisplayName()
                    && item.getItemMeta().getDisplayName().equals(ChatColor.RED + "Runner Tracker")) {
                CompassMeta meta = (CompassMeta) item.getItemMeta();
                meta.setLodestone(nearest.getLocation());
                meta.setLodestoneTracked(false);
                item.setItemMeta(meta);
            }
        }
    }

    private Player getNearestRunner(Player impostor) {
        Player nearest = null;
        double nearestDist = Double.MAX_VALUE;

        for(Player runner : runners) {
            double distance = impostor.getLocation().distanceSquared(runner.getLocation());
            if(distance < nearestDist) {
                nearestDist = distance;
                nearest = runner;
            }
        }
        return nearest;
    }
}
