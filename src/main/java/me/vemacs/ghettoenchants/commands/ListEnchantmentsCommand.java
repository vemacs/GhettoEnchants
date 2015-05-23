package me.vemacs.ghettoenchants.commands;

import com.google.common.base.Joiner;
import me.vemacs.ghettoenchants.EnchantsPlugin;
import me.vemacs.ghettoenchants.utils.EnchantUtils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Set;

public class ListEnchantmentsCommand implements CommandExecutor {
    private EnchantUtils utils;
    private Joiner joiner = Joiner.on(", ").skipNulls();

    public ListEnchantmentsCommand() {
        utils = EnchantsPlugin.getUtils();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Set<String> ghetto = utils.getParser().getNameMap().keySet();
        sender.sendMessage(ChatColor.YELLOW + "Registered ghetto enchantments: " + joiner.join(ghetto));
        return true;
    }
}
