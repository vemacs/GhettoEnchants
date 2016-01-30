package me.vemacs.ghettoenchants.enchants.armor;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public abstract class WornArmorEnchant extends AbstractAmbientEnchant {
    public WornArmorEnchant(int level) {
        super(level);
    }

    public abstract void perform(Player wearing, Event event);
}
