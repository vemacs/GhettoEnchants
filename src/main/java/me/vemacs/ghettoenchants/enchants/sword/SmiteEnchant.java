package me.vemacs.ghettoenchants.enchants.sword;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class SmiteEnchant extends AbstractSwordEnchant {
    public SmiteEnchant(int level) {
        super(level);
    }

    @Override
    public void perform(EntityDamageByEntityEvent e) {
        if (random.nextInt(100) < 4 * level) {
            LivingEntity damaged = (LivingEntity) e.getEntity();
            damaged.getWorld().strikeLightningEffect(damaged.getLocation());
            damaged.damage(e.getDamage());
            if (random.nextInt(100) < 5 * level) {
                damaged.setFireTicks(60);
            }
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
