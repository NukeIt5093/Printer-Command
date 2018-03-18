package com.nukeit5093.printercommand;

import com.earth2me.essentials.Essentials;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Logger;

public class PrinterCommand extends JavaPlugin{

    private static Economy eco = null;

    private static HashMap<UUID, Boolean> playerMap;
    public CommandListener commandListener;
    public static String chatPrefix = ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "[Printer] " + ChatColor.WHITE;


    @Override
    public void onEnable(){

        if(!setupEconomy()){
            Logger.getLogger("Minecraft").severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        commandListener = new CommandListener();
        playerMap = new HashMap<>();
        getCommand("printer").setExecutor(new CommandListener());
        Bukkit.getPluginManager().registerEvents(new PrinterMode(), this);
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        eco = rsp.getProvider();
        return eco != null;
    }

    public static Economy getEconomy() {
        return eco;
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
