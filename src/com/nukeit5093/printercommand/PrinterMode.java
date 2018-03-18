package com.nukeit5093.printercommand;

import com.earth2me.essentials.Essentials;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.inventory.ItemStack;

import java.math.BigDecimal;

public class PrinterMode implements Listener{

    Economy economy = PrinterCommand.getEconomy();
    Essentials essentials = (Essentials) Bukkit.getPluginManager().getPlugin("Essentials");

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

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e){
        Player player = e.getPlayer();
        if(PrinterCommand.isPlayerInMap(player)) {
            if (PrinterCommand.getPlayerStatus(player)) {
                e.setCancelled(true);
                player.sendMessage(PrinterCommand.getChatPrefix() + ChatColor.RED + "You cannot break blocks while in printer mode.");
            }
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e){
        Player player = e.getPlayer();
        Block block = e.getBlock();
        ItemStack itemStack = new ItemStack(block.getType());

        if(PrinterCommand.isPlayerInMap(player)) {
            if (PrinterCommand.getPlayerStatus(player)) {
                double r = economy.getBalance(player);
                double itemWorth = essentials.getWorth().getPrice(itemStack).doubleValue();
                if(itemWorth > r){
                    player.sendMessage(PrinterCommand.getChatPrefix() + ChatColor.RED + "You do not have enough money to place " + block.getType().toString());
                    e.setCancelled(true);
                }else{
                    economy.withdrawPlayer(player, itemWorth);
                    player.sendMessage(PrinterCommand.getChatPrefix() + ChatColor.GREEN + "Bought " + block.getType().toString() + " for $" + ChatColor.WHITE + itemWorth + ChatColor.GREEN + ". Your new balance is: $" + ChatColor.WHITE + economy.getBalance(player));
                }

            }
        }

    }

}
