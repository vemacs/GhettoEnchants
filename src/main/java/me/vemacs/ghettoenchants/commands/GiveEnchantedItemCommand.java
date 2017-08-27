package me.vemacs.ghettoenchants.commands;

import me.vemacs.ghettoenchants.EnchantsPlugin;
import me.vemacs.ghettoenchants.utils.EnchantUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class GiveEnchantedItemCommand implements CommandExecutor {
    private EnchantUtils utils;

    public GiveEnchantedItemCommand() {
        utils = EnchantsPlugin.getUtils();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 3) return false;
        Player p = Bukkit.getPlayerExact(args[0]);
        if (p == null) {
            sender.sendMessage(ChatColor.RED + "Player offline");
            return true;
        }
        Material type;
        try {
            type = Material.valueOf(args[1].toUpperCase());
        } catch (IllegalArgumentException e) {
            sender.sendMessage(ChatColor.RED + e.getMessage());
            return true;
        }
        ItemStack is;
        try {
            is = new ItemStack(type, 1);
            utils.getParser().applyAllEnchants(args[2], is);
        } catch (IllegalArgumentException e) {
            sender.sendMessage(ChatColor.RED + e.getMessage());
            return true;
        }
        safeAddItem(p, is);
        sender.sendMessage(ChatColor.GREEN + "The " + type + " has been delivered to " + p.getName());
        return true;
    }

    public static void safeAddItem(Player p, ItemStack... items) {
        Map<Integer, ItemStack> toDrop = p.getInventory()
                .addItem(items);
        for (Map.Entry<Integer, ItemStack> entry : toDrop.entrySet()) {
            p.getWorld().dropItemNaturally(p.getLocation(), entry.getValue());
        }
    }
}
