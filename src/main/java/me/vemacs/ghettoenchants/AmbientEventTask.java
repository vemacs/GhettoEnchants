package me.vemacs.ghettoenchants;

import me.vemacs.ghettoenchants.enchants.BaseEnchant;
import me.vemacs.ghettoenchants.enchants.armor.AbstractAmbientEnchant;
import me.vemacs.ghettoenchants.utils.EnchantUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class AmbientEventTask extends BukkitRunnable {
    private Map<UUID, Set<AbstractAmbientEnchant>> previous = new HashMap<>();
    private static EnchantUtils utils;

    public AmbientEventTask() {
        utils = EnchantsPlugin.getUtils();
    }

    @Override
    public void run() {
        Set<UUID> currentOnlineUuids = new HashSet<>();
        for (Player p : Bukkit.getOnlinePlayers()) {
            currentOnlineUuids.add(p.getUniqueId());
        }
        Set<UUID> toRemove = new HashSet<>(previous.keySet());
        toRemove.removeAll(currentOnlineUuids);
        for (UUID uuid : toRemove) {
            previous.remove(uuid);
        }
        for (Player p : Bukkit.getOnlinePlayers()) {
            Set<AbstractAmbientEnchant> current = new HashSet<>();
            ItemStack[] armor = p.getInventory().getArmorContents();
            for (ItemStack is : armor) {
                try {
                    List<BaseEnchant> beList = utils.readEnchants(is);
                    for (BaseEnchant be : beList) {
                        if (be instanceof AbstractAmbientEnchant) {
                            current.add((AbstractAmbientEnchant) be);
                        }
                    }
                } catch (Exception e) {
                    EnchantsPlugin.getInstance().getLogger().warning(
                            "Exception caught in ambient task with message " + e.getMessage()
                    );
                }
            }
            UUID uuid = p.getUniqueId();
            if (previous.containsKey(uuid)) {
                Set<AbstractAmbientEnchant> previousSet = previous.get(uuid);
                for (AbstractAmbientEnchant ench : current) {
                    if (!previousSet.contains(ench)) {
                        ench.armorWorn(p);
                    }
                }
                for (AbstractAmbientEnchant ench : previousSet) {
                    if (!current.contains(ench)) {
                        ench.armorRemoved(p);
                    }
                }
            } else {
                for (AbstractAmbientEnchant ench : current) {
                    ench.armorWorn(p);
                }
            }
            previous.put(uuid, current);
        }
    }
}
