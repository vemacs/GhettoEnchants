package me.vemacs.ghettoenchants;

import lombok.Getter;
import me.vemacs.ghettoenchants.commands.GiveEnchantedItemCommand;
import me.vemacs.ghettoenchants.enchants.BaseEnchant;
import me.vemacs.ghettoenchants.enchants.armor.JumpPotionEnchant;
import me.vemacs.ghettoenchants.enchants.tools.pickaxe.*;
import me.vemacs.ghettoenchants.enchants.tools.sword.VenomEnchant;
import me.vemacs.ghettoenchants.utils.EnchantUtils;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.List;

public class EnchantsPlugin extends JavaPlugin {
    @Getter
    private static EnchantsPlugin instance;
    @Getter
    private static EnchantUtils utils;

    @Override
    public void onEnable() {
        instance = this;
        utils = new EnchantUtils();
        getCommand("giveenchanteditem").setExecutor(new GiveEnchantedItemCommand());
        getServer().getPluginManager().registerEvents(new EnchantsListener(), this);
        new AmbientEventTask().runTaskTimer(this, 0, 10);
        if (!getConfig().getBoolean("enable-samples")) return;
        List<Class<? extends BaseEnchant>> toRegister = Arrays.asList(
                DemoPickEnchant.class,
                ExplodePickEnchant.class,
                FireworkPickEnchant.class,
                AutosmeltEnchant.class,
                VenomEnchant.class,
                JumpPotionEnchant.class
        );
        for (Class<? extends BaseEnchant> c : toRegister) {
            try {
                utils.registerEnchant(c);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
