package me.vemacs.ghettoenchants.enchants.sword;

import me.vemacs.ghettoenchants.enchants.BaseEnchant;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

public abstract class AbstractSwordEnchant extends BaseEnchant {
    public AbstractSwordEnchant(int level) {
        super(level);
    }

    @Override
    public boolean canEnchant(ItemStack is) {
        return (is != null) && (is.getType().toString().contains("_SWORD"));
    }

    @Override
    public void perform(Event e) {
        if ((e instanceof EntityDamageByEntityEvent)) {
            perform((EntityDamageByEntityEvent) e);
        }
    }

    public abstract void perform(EntityDamageByEntityEvent e);
}
