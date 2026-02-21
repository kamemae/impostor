package me.kamemae.impostor;
import me.kamemae.impostor.managers.CompassManager;
// managers
import me.kamemae.impostor.managers.CountdownManager;
import me.kamemae.impostor.managers.GameManager;
import me.kamemae.impostor.managers.PluginManager;
import me.kamemae.impostor.managers.TimerManager;
// commands
import me.kamemae.impostor.commands.AboutCommand;
import me.kamemae.impostor.commands.JesterCommand;
import me.kamemae.impostor.commands.SetImpostorsCommand;
import me.kamemae.impostor.commands.SetRoundTimeCommand;
import me.kamemae.impostor.commands.StartCommand;
import me.kamemae.impostor.commands.CharacterCommands.Accuse;
import me.kamemae.impostor.commands.CharacterCommands.Impersonate;
// util commands
import me.kamemae.impostor.commands.UtilityCommands.Lost;
import me.kamemae.impostor.commands.UtilityCommands.Wherami;
import me.kamemae.impostor.listeners.AdvancementListener;
// listeners / events
import me.kamemae.impostor.listeners.ChatListener;
import me.kamemae.impostor.listeners.CommandsListener;
import me.kamemae.impostor.listeners.DeathListener;
import me.kamemae.impostor.listeners.ObjectiveListener;
import me.kamemae.impostor.listeners.PlayerListener;
// bukkit 
import org.bukkit.plugin.java.JavaPlugin;


public class Main extends JavaPlugin {
    private GameManager gameManager;
    private CountdownManager countdownManager;

    private TimerManager timerManager;

    public int getImpostorCount() {
        return gameManager.getImpostorCount();
    }


    @Override
    public void onEnable() {
        PluginManager.getInstance().initialize();
        gameManager = new GameManager();
        countdownManager = new CountdownManager(this);
        timerManager = new TimerManager(gameManager, this);
        gameManager.SetTimer(timerManager);

        registerCommands();
        registerListeners();

        getLogger().info("Impostor enabled");
    }

    private void registerCommands() {
        // def commands
        getCommand("start").setExecutor(new StartCommand(gameManager, countdownManager));
        getCommand("about").setExecutor(new AboutCommand());
        getCommand("setimpostors").setExecutor(new SetImpostorsCommand(gameManager));
        getCommand("setroundtime").setExecutor(new SetRoundTimeCommand(gameManager));
        getCommand("jester").setExecutor(new JesterCommand(gameManager));
    
        // util commands - during game
        getCommand("lost").setExecutor(new Lost(gameManager));
        getCommand("wherami").setExecutor(new Wherami(gameManager));

        // character specified
        getCommand("impersonate").setExecutor(new Impersonate(gameManager));
        getCommand("accuse").setExecutor(new Accuse(gameManager));
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
        // Achivements
        //getServer().getPluginManager().registerEvents(new AdvancementListener(gameManager), this);
        // Compass
        //getServer().getPluginManager().registerEvents(new CompassManager(gameManager, this), this);
        //new CompassManager(gameManager, this);
    }

    public GameManager getGameManager() {
        return gameManager;
    }

    @Override
    public void onDisable() {
        getLogger().info("Impostor disabled");
    }
}
