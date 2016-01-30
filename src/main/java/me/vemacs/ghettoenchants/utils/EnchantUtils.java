package me.vemacs.ghettoenchants.utils;

import com.google.common.base.Joiner;
import lombok.Getter;
import me.vemacs.ghettoenchants.EnchantsPlugin;
import me.vemacs.ghettoenchants.enchants.BaseEnchant;
import me.vemacs.ghettoenchants.enchants.armor.WornArmorEnchant;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
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
    private Map<String, Class<? extends BaseEnchant>> registeredEnchants = new HashMap<>();
    @Getter
    private static Map<Class, Constructor> cachedConstructors = new HashMap<>();
    @Getter
    private EnchantParser parser = new EnchantParser(this);

    public void registerEnchant(Class<? extends BaseEnchant> fromClass) {
        BaseEnchant enc = newInstance(fromClass);
        getRegisteredEnchants().put(enc.getName(), enc.getClass());
        EnchantsPlugin.getInstance().getLogger()
                .info("Registered enchantment " + enc.getName() + " as " + enc.getClass().getSimpleName());
        parser.updateNameMap();
    }

    public void unregisterEnchant(Class<? extends BaseEnchant> fromClass) {
        BaseEnchant enc = newInstance(fromClass);
        cachedConstructors.remove(fromClass);
        registeredEnchants.remove(enc.getName());
        parser.updateNameMap();
    }

    public BaseEnchant newInstance(Class<? extends BaseEnchant> fromClass) {
        return newInstance(fromClass, 1);
    }

    public BaseEnchant newInstance(Class<? extends BaseEnchant> fromClass, int level) {
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

    public void setEnchantLevel(Class<? extends BaseEnchant> enchant, ItemStack is, int level)
            throws IllegalArgumentException {
        List<BaseEnchant> elist = readEnchants(is);
        boolean exists = false;
        for (BaseEnchant e : elist)
            if (e.getClass().equals(enchant)) {
                if (!e.canEnchant(is)) {
                    throw new IllegalArgumentException(enchant.getName() +
                        " is not applicable to " + is.getType().toString());
                }
                if (level > e.getMaxLevel()) {
                    throw new IllegalArgumentException("Cannot increment over max level");
                }
                e.setLevel(level);
                exists = true;
                break;
            }
        if (!exists)
            elist.add(newInstance(enchant, level));
        writeEnchants(elist, is);
    }

    public int getEnchantLevel(Class<? extends BaseEnchant> enchant, ItemStack is)
            throws IllegalArgumentException {
        List<BaseEnchant> elist = readEnchants(is);
        for (BaseEnchant e : elist) {
            if (e.getClass().equals(enchant)) {
                return e.getLevel();
            }
        }
        return 0;
    }

    public List<BaseEnchant> readEnchants(ItemStack is) {
        List<BaseEnchant> elist = new ArrayList<>();
        if ((is.getItemMeta() == null) || (is.getItemMeta().getLore() == null))
            return elist;
        for (String estr : is.getItemMeta().getLore()) {
            String stripped = ChatColor.stripColor(estr);
            try {
                elist.add(getEnchant(stripped));
            } catch (IllegalArgumentException e) {
                EnchantsPlugin.getInstance().getLogger().warning(
                        String.format("Invalid enchant for ItemStack of type %s: %s",
                                is.getType().toString(), stripped)
                );
            }
        }
        return elist;
    }

    public void writeEnchants(List<BaseEnchant> elist, ItemStack is) {
        ItemMeta meta = is.hasItemMeta() ? is.getItemMeta() : Bukkit.getItemFactory().getItemMeta(is.getType());
        List<String> comp = new ArrayList<>();
        for (BaseEnchant e : elist)
            comp.add(ChatColor.GRAY + e.getEnchantString());
        meta.setLore(comp);
        is.setItemMeta(meta);
    }

    public BaseEnchant getEnchant(String enchantString) throws IllegalArgumentException {
        String[] parts = enchantString.split(" ");
        int level = RomanNumerals.intFromRomanString(parts[(parts.length - 1)]);
        parts[(parts.length - 1)] = null;
        String name = Joiner.on(" ").skipNulls().join(parts);
        if (!registeredEnchants.containsKey(name)) {
            throw new IllegalArgumentException("Invalid enchant name: " + name);
        }
        return newInstance(registeredEnchants.get(name), level);
    }

    public void performEnchants(Event event, ItemStack... items) {
        try {
            List<BaseEnchant> elist = new ArrayList<>();
            for (ItemStack is : items) {
                elist.addAll(readEnchants(is));
            }
            for (BaseEnchant e : elist) {
                e.perform(event);
            }
        } catch (Exception ignored) {
        }
    }

    public void performArmorEnchants(Event event, Player wearer) {
        performArmorEnchants(event, wearer, wearer.getInventory().getArmorContents());
    }

    public void performArmorEnchants(Event event, Player wearer, ItemStack... contents) {
        try {
            List<BaseEnchant> elist = new ArrayList<>();
            for (ItemStack is : contents) {
                elist.addAll(EnchantsPlugin.getUtils().readEnchants(is));
            }
            for (BaseEnchant e : elist) {
                if (e instanceof WornArmorEnchant) {
                    ((WornArmorEnchant) e).perform(wearer, event);
                }
            }
        } catch (Exception ignored) {
        }
    }
}
