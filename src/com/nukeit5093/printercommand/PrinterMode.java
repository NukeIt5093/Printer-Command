package com.nukeit5093.printercommand;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class PrinterMode implements Listener{

    @EventHandler
    public void onChatMessage(PlayerCommandPreprocessEvent e){
        Player player = e.getPlayer();
        if(PrinterCommand.isPlayerInMap(player)){
            if(PrinterCommand.getPlayerStatus(player)){
                if(!e.getMessage().equalsIgnoreCase("/printer")){
                    e.setCancelled(true);
                    player.sendMessage(PrinterCommand.getChatPrefix() + ChatColor.RED + "You cannot use commands while in printer mode. \n" + PrinterCommand.getChatPrefix() + "To Exit this mode use '/printer'.");
                }
            }
        }
    }

}
