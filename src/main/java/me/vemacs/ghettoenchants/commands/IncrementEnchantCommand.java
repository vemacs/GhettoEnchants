package me.vemacs.ghettoenchants.commands;

import com.google.common.base.Joiner;
import me.vemacs.ghettoenchants.enchants.BaseEnchant;
import me.vemacs.ghettoenchants.utils.EnchantUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class IncrementEnchantCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players may use this command.");
            return true;
        }
        String name = Joiner.on(" ").join(args);
        Class<? extends BaseEnchant> ench = EnchantUtils.getRegisteredEnchants().get(name);
        if (ench == null) {
            sender.sendMessage(ChatColor.RED + "Invalid enchant name.");
            return true;
        }
        ItemStack is = ((Player) sender).getItemInHand();
        if (!EnchantUtils.newInstance(ench).canEnchant(is)) {
            sender.sendMessage(ChatColor.RED + name + " cannot be applied to " + ((Player) sender)
                    .getItemInHand().getType());
            return true;
        }
        EnchantUtils.incrementEnchant(ench, is);
        sender.sendMessage(ChatColor.GREEN + name + " incremented.");
        return true;
    }
}
