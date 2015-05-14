package me.vemacs.ghettoenchants.enchants.sword;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.Random;

public class SmiteEnchant extends AbstractSwordEnchant {
    private static final Random random = new Random();

    public SmiteEnchant(int level) {
        super(level);
    }

    @Override
    public void perform(EntityDamageByEntityEvent e) {
        if (random.nextInt(100) < 4 * level) {
            LivingEntity damaged = (LivingEntity) e.getEntity();
            damaged.getWorld().strikeLightningEffect(damaged.getLocation());
            damaged.damage(e.getDamage());
        }
    }

    @Override
    public String getName() {
        return "Wrath of Zeus";
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }
}
