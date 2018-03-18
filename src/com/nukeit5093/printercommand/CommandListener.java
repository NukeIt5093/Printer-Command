package com.nukeit5093.printercommand;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

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
                player.setGameMode(GameMode.CREATIVE);
                player.sendMessage(PrinterCommand.getChatPrefix() + ChatColor.GREEN + "Enabled printer mode.");
            }else{
                player.setGameMode(GameMode.SURVIVAL);
                player.sendMessage(PrinterCommand.getChatPrefix() + ChatColor.RED + "Disabled printer mode.");
            }
        }

        return false;
    }
}
