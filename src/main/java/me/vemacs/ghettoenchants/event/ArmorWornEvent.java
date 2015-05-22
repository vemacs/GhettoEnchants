package me.vemacs.ghettoenchants.event;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ArmorWornEvent extends ArmorEvent {
    public ArmorWornEvent(Player who, ItemStack itemStack) {
        super(who, itemStack);
    }
}
