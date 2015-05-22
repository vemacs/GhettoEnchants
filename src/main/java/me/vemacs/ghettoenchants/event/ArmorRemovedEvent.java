package me.vemacs.ghettoenchants.event;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ArmorRemovedEvent extends ArmorEvent {
    public ArmorRemovedEvent(Player who, ItemStack itemStack) {
        super(who, itemStack);
    }
}
