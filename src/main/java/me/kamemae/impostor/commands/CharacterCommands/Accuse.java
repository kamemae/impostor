package me.kamemae.impostor.commands.CharacterCommands;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.kamemae.impostor.managers.GameManager;
import net.md_5.bungee.api.ChatColor;

public class Accuse implements CommandExecutor {
    private final GameManager gameManager;
    public Accuse(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    private boolean investigation = true;

    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(gameManager.isGameRunning() && sender instanceof Player player && player == gameManager.getInvestigator() && investigation) {
            investigation = false;

            if(args.length == 0) {
                investigation = true;
                return false;
            }

            String targetName = args[0];

            Player accused = null;
            for(Player impostor : gameManager.getImpostorsList()) {
                if(impostor.getName().equalsIgnoreCase(targetName)) {
                    accused = impostor;
                    break;
                }
            }
            
            if(accused != null) {
                Bukkit.broadcastMessage(ChatColor.RED + "" + ChatColor.BOLD + targetName + " was found guilty!");

                accused.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 100, 255));
                accused.addPotionEffect(new PotionEffect(PotionEffectType.NAUSEA, 100, 255));
                accused.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 6000, 255));

                for (Player allPlayers : Bukkit.getOnlinePlayers()) {
                    allPlayers.sendTitle(ChatColor.RED + "" + ChatColor.BOLD + targetName + " is guilty", "Detective guessed right", 10, 100, 10);
                    allPlayers.playSound(player.getLocation(), Sound.ENTITY_BLAZE_DEATH, 1.0f, 1.5f);
                }
            } else {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "kill " + player.getName());
            }
        }
        return true;
    }

    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();
        if(args.length == 1) {
            completions = Bukkit.getOnlinePlayers().stream().map(Player::getName).filter(name -> name.toLowerCase().startsWith(args[0].toLowerCase())).collect(Collectors.toList());
        }
        return completions;
    }
}
