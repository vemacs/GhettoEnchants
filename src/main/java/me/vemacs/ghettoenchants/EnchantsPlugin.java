package me.vemacs.ghettoenchants;

import com.google.common.base.Joiner;
import lombok.Getter;
import me.vemacs.ghettoenchants.enchants.BaseEnchant;
import me.vemacs.ghettoenchants.enchants.pickaxe.*;
import me.vemacs.ghettoenchants.enchants.sword.SmiteEnchant;
import me.vemacs.ghettoenchants.utils.EnchantUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.List;

public class EnchantsPlugin extends JavaPlugin {
    @Getter
    private static EnchantsPlugin instance;

    @Override
    public void onEnable() {
        instance = this;
        List<Class<? extends BaseEnchant>> toRegister = Arrays.asList(
                DemoPickEnchant.class,
                ExplodePickEnchant.class,
                FireworkPickEnchant.class,
                LaserPickEnchant.class,
                SmiteEnchant.class,
                AutosmeltEnchant.class
        );
        for (Class<? extends BaseEnchant> c : toRegister) {
            try {
                BaseEnchant enc = EnchantUtils.newInstance(c);
                EnchantUtils.getRegisteredEnchants().put(enc.getName(), enc.getClass());
                getLogger().info("Registered enchantment " + enc.getName() + " as " + enc.getClass().getSimpleName());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        getServer().getPluginManager().registerEvents(new EnchantsListener(), this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
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
