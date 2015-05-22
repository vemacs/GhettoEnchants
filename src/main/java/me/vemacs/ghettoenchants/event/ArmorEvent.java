package me.vemacs.ghettoenchants.event;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.inventory.ItemStack;

public abstract class ArmorEvent extends PlayerEvent {
    private static final HandlerList handlers = new HandlerList();
    @Getter
    private final ItemStack itemStack;
    @Getter
    private final int armorIndex;

    public ArmorEvent(Player who, ItemStack itemStack) {
        super(who);
        this.itemStack = itemStack;
        String stackType = itemStack.getType().toString();
        if (stackType.endsWith("_BOOTS")) {
            armorIndex = 3;
        } else if (stackType.endsWith("_LEGGINGS")) {
            armorIndex = 2;
        } else if (stackType.endsWith("_CHESTPLATE")) {
            armorIndex = 1;
        } else {
            armorIndex = 0;
        }
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}
