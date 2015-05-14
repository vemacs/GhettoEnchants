package me.vemacs.ghettoenchants.enchants;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public abstract class BaseEnchant {
    @Getter
    @Setter
    protected int level;

    protected static final Random random = new Random();

    public BaseEnchant(int level) {
        this.level = level;
    }

    public String getEnchantString() {
        return getName() + " " + me.vemacs.ghettoenchants.utils.RomanNumerals.romanStringFromInt(getLevel());
    }

    public abstract String getName();

    public abstract int getMaxLevel();

    public abstract boolean canEnchant(ItemStack paramItemStack);

    public abstract void perform(org.bukkit.event.Event paramEvent);
}
