package com.nukeit5093.printercommand;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

public class PrinterCommand extends JavaPlugin{

    private static HashMap<UUID, Boolean> playerMap;
    public CommandListener commandListener;
    public static String chatPrefix = ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "[Printer] " + ChatColor.WHITE;


    @Override
    public void onEnable(){
        commandListener = new CommandListener();
        playerMap = new HashMap<>();
        getCommand("printer").setExecutor(new CommandListener());
        Bukkit.getPluginManager().registerEvents(new PrinterMode(), this);
    }

    public static String getChatPrefix(){
        return chatPrefix;
    }

    public static Boolean isPlayerInMap(Player player){
        return playerMap.containsKey(player.getUniqueId());
    }

    public static void togglePlayerPrinterStatus(Player player){
        if(playerMap.containsKey(player.getUniqueId())){
            playerMap.put(player.getUniqueId(), !playerMap.get(player.getUniqueId()));
        }else{
            playerMap.put(player.getUniqueId(), true);
        }
    }

    public static Boolean getPlayerStatus(Player player){
        return playerMap.get(player.getUniqueId());
    }

}
