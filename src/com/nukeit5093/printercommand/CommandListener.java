package com.nukeit5093.printercommand;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;

import java.io.IOException;
import java.util.UUID;

public class CommandListener implements CommandExecutor, Listener {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if(!(sender instanceof Player)){
            sender.sendMessage(ChatColor.RED + "Only players can use this command.");
        }

        Player player = (Player) sender;
        UUID uuid = player.getUniqueId();

        if(command.getName().equalsIgnoreCase("printer")){
            PrinterCommand.togglePlayerPrinterStatus(player);
            if(PrinterCommand.getPlayerStatus(player)){
                Inventory inventory = (Inventory) player.getInventory();
                String base64 = InventoryHandler.invToString(inventory);
                InventoryHandler.inventoryMap.put(uuid, base64);

                player.getInventory().clear();
                player.setGameMode(GameMode.CREATIVE);
                player.sendMessage(PrinterCommand.getChatPrefix() + ChatColor.GREEN + "Enabled printer mode.");
            }else{
                player.getInventory().clear();

                String base64 = InventoryHandler.inventoryMap.get(uuid);
                try {
                    Inventory inventory = InventoryHandler.stringToInv(base64);
                    player.getInventory().setContents(inventory.getContents());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                player.setGameMode(GameMode.SURVIVAL);
                player.sendMessage(PrinterCommand.getChatPrefix() + ChatColor.RED + "Disabled printer mode.");
            }
        }

        return false;
    }
}
