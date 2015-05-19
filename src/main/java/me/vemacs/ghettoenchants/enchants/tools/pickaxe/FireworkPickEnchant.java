package me.vemacs.ghettoenchants.enchants.tools.pickaxe;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.entity.Firework;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.meta.FireworkMeta;

public class FireworkPickEnchant extends AbstractPickEnchant {
    public FireworkPickEnchant(int level) {
        super(level);
    }

    @Override
    public void perform(BlockBreakEvent e) {
        if (random.nextInt(100) <= 3 * level) {
            Firework firework = e.getPlayer().getWorld().spawn(e.getPlayer().getLocation(), Firework.class);
            FireworkMeta data = firework.getFireworkMeta();
            data.addEffects(FireworkEffect.builder()
                    .withColor(Color.RED, Color.WHITE, Color.BLUE)
                    .with(level < 3 ? org.bukkit.FireworkEffect.Type.BALL : org.bukkit.FireworkEffect.Type.BALL_LARGE)
                    .build());
            data.setPower(0);
            firework.setFireworkMeta(data);
        }
    }

    @Override
    public String getName() {
        return "Firework";
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }
}
