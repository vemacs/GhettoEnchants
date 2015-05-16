package me.vemacs.ghettoenchants.utils;

import me.vemacs.ghettoenchants.EnchantsPlugin;
import me.vemacs.ghettoenchants.enchants.BaseEnchant;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class EnchantParser {
    private Map<String, String> addSpaces = new HashMap<>();

    public EnchantParser() {
        for (String str : EnchantUtils.getRegisteredEnchants().keySet()) {
            addSpaces.put(str.replace(" ", "").toLowerCase(), str);
        }
    }

    public void applyAllEnchants(String enchStr, ItemStack is) throws IllegalArgumentException {
        String[] parts = enchStr.split(",");
        for (String part : parts) {
            applyEnchant(part, is);
        }
    }

    public void applyEnchant(String str, ItemStack is) {
        boolean success = false;
        try {
            applyGhettoEnchant(str, is);
            success = true;
        } catch (IllegalArgumentException ignored) {

        }
        try {
            applyVanillaEnchant(str, is);
            success = true;
        } catch (IllegalArgumentException ignored) {

        }
        if (!success)
            throw new IllegalArgumentException("Invalid enchant string: " + str);
    }

    public void applyGhettoEnchant(String str, ItemStack is) throws IllegalArgumentException {
        String[] parts = str.split(":");
        String encName;
        if (!addSpaces.containsKey(parts[0])) {
            throw new IllegalArgumentException("Invalid enchant name: " + parts[0]);
        }
        encName = addSpaces.get(parts[0]);
        int level;
        try {
            level = Integer.parseInt(parts[1]);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid level: " + parts[1]);
        }
        Class<? extends BaseEnchant> fromClass = EnchantUtils.getRegisteredEnchants().get(encName);
        EnchantUtils.setEnchantLevel(fromClass, is, level);
    }

    public void applyVanillaEnchant(String str, ItemStack is) throws IllegalArgumentException {
        String[] parts = str.split(":");
        Enchantment enc = VanillaEnchants.getByName(parts[0]);
        if (enc == null) {
            throw new IllegalArgumentException("Invalid enchant name: " + parts[0]);
        }
        int level;
        try {
            level = Integer.parseInt(parts[1]);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid level: " + parts[1]);
        }
        is.addEnchantment(enc, level);
    }
}
