package me.vemacs.ghettoenchants.enchants.armor;

import me.vemacs.ghettoenchants.enchants.BaseEnchant;
import me.vemacs.ghettoenchants.event.ArmorRemovedEvent;
import me.vemacs.ghettoenchants.event.ArmorWornEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

/**
 * Unlike tool enchants, multiple pieces of worn armor can have the same enchants.
 * No stacking behavior is implemented. An implementation of this class with stacking
 * enchants is in the works.
 * No singleton enchant behavior is implemented either (that was the behavior
 * pre-event-refactor). I felt that behavior was too inflexible, but a reimplementation
 * is in the works.
 */
public abstract class AbstractAmbientEnchant extends BaseEnchant {
    private static final List<String> validMatches = Arrays.asList(
            "_HELMET",
            "_CHESTPLATE",
            "_LEGGINGS",
            "_BOOTS"
    );

    public AbstractAmbientEnchant(int level) {
        super(level);
    }

    @Override
    public boolean canEnchant(ItemStack is) {
        for (String match : validMatches) {
            if (is.getType().toString().contains(match)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void perform(Event event) {
        if (event instanceof ArmorWornEvent) {
            armorWorn(((ArmorWornEvent) event).getPlayer());
        } else if (event instanceof ArmorRemovedEvent) {
            armorRemoved(((ArmorRemovedEvent) event).getPlayer());
        }
    }

    public abstract void armorWorn(Player player);

    public abstract void armorRemoved(Player player);
}
