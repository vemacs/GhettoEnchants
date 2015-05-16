package me.vemacs.ghettoenchants;

import lombok.Getter;
import me.vemacs.ghettoenchants.commands.GiveEnchantedItemCommand;
import me.vemacs.ghettoenchants.enchants.BaseEnchant;
import me.vemacs.ghettoenchants.enchants.pickaxe.*;
import me.vemacs.ghettoenchants.enchants.sword.*;
import me.vemacs.ghettoenchants.utils.EnchantUtils;
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
                AutosmeltEnchant.class,
                SmiteEnchant.class,
                VenomEnchant.class
        );
        for (Class<? extends BaseEnchant> c : toRegister) {
            try {
                EnchantUtils.registerEnchant(c);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        getCommand("giveenchanteditem").setExecutor(new GiveEnchantedItemCommand());
        getServer().getPluginManager().registerEvents(new EnchantsListener(), this);
    }
}
