package me.vemacs.ghettoenchants.utils;

import com.google.common.base.Joiner;
import lombok.Getter;
import me.vemacs.ghettoenchants.enchants.BaseEnchant;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Not my proudest work ever
 */
public class EnchantUtils {
    @Getter
    private static Map<String, Class<? extends BaseEnchant>> registeredEnchants = new HashMap<>();
    @Getter
    private static Map<String, BaseEnchant> cachedEnchants = new HashMap<>();
    @Getter
    private static Map<Class, Constructor> cachedConstructors = new HashMap<>();

    public static BaseEnchant newInstance(Class<? extends BaseEnchant> fromClass) {
        return newInstance(fromClass, 1);
    }

    public static BaseEnchant newInstance(Class<? extends BaseEnchant> fromClass, int level) {
        try {
            if (!cachedConstructors.containsKey(fromClass)) {
                Constructor constructor = fromClass.getDeclaredConstructor(Integer.TYPE);
                constructor.setAccessible(true);
                cachedConstructors.put(fromClass, constructor);
            }
            return (BaseEnchant) cachedConstructors.get(fromClass).newInstance(level);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void incrementEnchant(Class<? extends BaseEnchant> toIncrement, ItemStack is)
            throws IllegalArgumentException {
        List<BaseEnchant> elist = readEnchants(is);
        boolean exists = false;
        for (BaseEnchant e : elist)
            if (e.getClass().equals(toIncrement)) {
                int toLevel = e.getLevel() + 1;
                if (toLevel <= e.getMaxLevel()) {
                    e.setLevel(toLevel);
                } else
                    throw new IllegalArgumentException("Cannot increment over max level");
                exists = true;
                break;
            }
        if (!exists)
            elist.add(newInstance(toIncrement, 1));
        writeEnchants(elist, is);
    }

    public static List<BaseEnchant> readEnchants(ItemStack is) {
        List<BaseEnchant> elist = new ArrayList<>();
        if ((is.getItemMeta() == null) || (is.getItemMeta().getLore() == null))
            return elist;
        for (String estr : is.getItemMeta().getLore())
            elist.add(getEnchant(ChatColor.stripColor(estr)));
        return elist;
    }

    public static void writeEnchants(List<BaseEnchant> elist, ItemStack is) {
        ItemMeta meta = is.hasItemMeta() ? is.getItemMeta() : Bukkit.getItemFactory().getItemMeta(is.getType());
        List<String> comp = new ArrayList<>();
        for (BaseEnchant e : elist)
            comp.add(ChatColor.GRAY + e.getEnchantString());
        meta.setLore(comp);
        is.setItemMeta(meta);
    }

    public static BaseEnchant getEnchant(String enchantString) {
        if (cachedEnchants.containsKey(enchantString))
            return cachedEnchants.get(enchantString);
        String[] parts = enchantString.split(" ");
        int level = RomanNumerals.intFromRomanString(parts[(parts.length - 1)]);
        parts[(parts.length - 1)] = null;
        BaseEnchant enchant;
        enchant = newInstance(registeredEnchants.get(Joiner.on(" ").skipNulls()
                .join(parts)), level);
        cachedEnchants.put(enchantString, enchant);
        return enchant;
    }
}
