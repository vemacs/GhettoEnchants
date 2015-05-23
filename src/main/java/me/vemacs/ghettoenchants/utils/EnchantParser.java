package me.vemacs.ghettoenchants.utils;

import lombok.Getter;
import me.vemacs.ghettoenchants.enchants.BaseEnchant;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class EnchantParser {
    private EnchantUtils utils;
    @Getter
    private Map<String, String> nameMap = new HashMap<>();

    public EnchantParser(EnchantUtils utils) {
        this.utils = utils;
    }

    public void updateNameMap() {
        nameMap.clear();
        for (String str : utils.getRegisteredEnchants().keySet()) {
            nameMap.put(str.replace(" ", "").toLowerCase(), str);
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
        if (!nameMap.containsKey(parts[0])) {
            throw new IllegalArgumentException("Invalid enchant name: " + parts[0]);
        }
        encName = nameMap.get(parts[0]);
        int level;
        try {
            level = Integer.parseInt(parts[1]);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid level: " + parts[1]);
        }
        Class<? extends BaseEnchant> fromClass = utils.getRegisteredEnchants().get(encName);
        utils.setEnchantLevel(fromClass, is, level);
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
