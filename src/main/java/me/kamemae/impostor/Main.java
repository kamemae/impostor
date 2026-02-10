package me.kamemae.impostor;
// managers
import me.kamemae.impostor.managers.CountdownManager;
import me.kamemae.impostor.managers.GameManager;
import me.kamemae.impostor.managers.PluginManager;
// commands
import me.kamemae.impostor.commands.AboutCommand;
import me.kamemae.impostor.commands.SetImpostorsCommand;
import me.kamemae.impostor.commands.StartCommand;
// util commands
import me.kamemae.impostor.commands.UtilityCommands.Lost;
import me.kamemae.impostor.commands.UtilityCommands.Wherami;
// listeners / events
import me.kamemae.impostor.listeners.ChatListener;
import me.kamemae.impostor.listeners.CommandsListener;
import me.kamemae.impostor.listeners.DeathListener;
import me.kamemae.impostor.listeners.ObjectiveListener;
import me.kamemae.impostor.listeners.PlayerListener;

import org.bukkit.plugin.java.JavaPlugin;


public class Main extends JavaPlugin {
    private GameManager gameManager;
    private CountdownManager countdownManager;

    public int getImpostorCount() {
        return gameManager.getImpostorsList().size();
    }

    @Override
    public void onEnable() {
        PluginManager.getInstance().initialize();

        gameManager = new GameManager();
        countdownManager = new CountdownManager(this);

        registerCommands();
        registerListeners();

        getLogger().info("Impostor enabled");
    }

    private void registerCommands() {
        // def commands
        getCommand("start").setExecutor(new StartCommand(gameManager, countdownManager));
        getCommand("about").setExecutor(new AboutCommand());
        getCommand("setimpostors").setExecutor(new SetImpostorsCommand(gameManager));

        // util commands - during game
        getCommand("lost").setExecutor(new Lost(gameManager));
        getCommand("wherami").setExecutor(new Wherami(gameManager));
    }
    private void registerListeners() {
        // Players
        getServer().getPluginManager().registerEvents(new PlayerListener(gameManager), this);
        // Objectives
        getServer().getPluginManager().registerEvents(new ObjectiveListener(gameManager), this);
        // Chat
        getServer().getPluginManager().registerEvents(new ChatListener(gameManager), this);
        // Deaths
        getServer().getPluginManager().registerEvents(new DeathListener(gameManager), this);
        // Commands
        getServer().getPluginManager().registerEvents(new CommandsListener(gameManager), this);
    }

    public GameManager getGameManager() {
        return gameManager;
    }

    @Override
    public void onDisable() {
        getLogger().info("Impostor disabled");
    }
}