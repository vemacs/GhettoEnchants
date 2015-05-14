package me.vemacs.ghettoenchants.enchants.pickaxe;

import me.vemacs.ghettoenchants.enchants.BaseEnchant;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public abstract class AbstractPickEnchant extends BaseEnchant {
    public AbstractPickEnchant(int level) {
        super(level);
    }

    @Override
    public boolean canEnchant(ItemStack is) {
        return (is != null) && (is.getType().toString().contains("_PICK"));
    }

    @Override
    public void perform(Event e) {
        if ((e instanceof BlockBreakEvent)) {
            perform((BlockBreakEvent) e);
        }
    }

    public abstract void perform(BlockBreakEvent paramBlockBreakEvent);
}
