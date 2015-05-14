package me.vemacs.ghettoenchants.enchants.sword;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class VenomEnchant extends AbstractSwordEnchant {
    private static final PotionEffect poisonEffect = new PotionEffect(PotionEffectType.POISON, 200, 1);

    public VenomEnchant(int level) {
        super(level);
    }

    @Override
    public void perform(EntityDamageByEntityEvent e) {
        if (random.nextInt(100) < 4 * level) {
            LivingEntity entity = (LivingEntity) e.getEntity();
            entity.addPotionEffect(poisonEffect);
        }
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public int getMaxLevel() {
        return 0;
    }
}
