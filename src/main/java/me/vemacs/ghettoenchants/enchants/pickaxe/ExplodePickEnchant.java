package me.vemacs.ghettoenchants.enchants.pickaxe;

import lombok.Getter;
import me.vemacs.ghettoenchants.EnchantsPlugin;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class ExplodePickEnchant extends AbstractPickEnchant {
    @Getter
    private static List<String> invincible = new ArrayList<>();

    public ExplodePickEnchant(int level) {
        super(level);
    }

    @Override
    public String getName() {
        return "Explode";
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }

    @Override
    public void perform(BlockBreakEvent e) {
        e.getBlock().getWorld().createExplosion(e.getBlock().getLocation(), level);
        final String name = e.getPlayer().getName();
        invincible.add(name);
        new BukkitRunnable() {
            @Override
            public void run() {
                ExplodePickEnchant.invincible.remove(name);
            }
        }.runTaskLater(EnchantsPlugin.getInstance(), 5);
    }
}
