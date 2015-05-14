package me.vemacs.ghettoenchants;

import lombok.Getter;
import me.vemacs.ghettoenchants.commands.IncrementEnchantCommand;
import me.vemacs.ghettoenchants.enchants.BaseEnchant;
import me.vemacs.ghettoenchants.enchants.pickaxe.*;
import me.vemacs.ghettoenchants.enchants.sword.SmiteEnchant;
import me.vemacs.ghettoenchants.enchants.sword.VenomEnchant;
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
                SmiteEnchant.class,
                AutosmeltEnchant.class,
                VenomEnchant.class
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
        getCommand("incrementenchant").setExecutor(new IncrementEnchantCommand());
        getServer().getPluginManager().registerEvents(new EnchantsListener(), this);
    }
}
