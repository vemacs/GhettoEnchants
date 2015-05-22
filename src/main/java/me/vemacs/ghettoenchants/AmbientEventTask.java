package me.vemacs.ghettoenchants;

import me.vemacs.ghettoenchants.event.ArmorRemovedEvent;
import me.vemacs.ghettoenchants.event.ArmorWornEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class AmbientEventTask extends BukkitRunnable {
    private Map<UUID, List<ItemStack>> previous = new HashMap<>();

    @Override
    public void run() {
        PluginManager pm = Bukkit.getPluginManager();
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
            List<ItemStack> current = new ArrayList<>();
            for (ItemStack is : p.getInventory().getArmorContents()) {
                if (is == null) is = new ItemStack(Material.AIR);
                current.add(is);
            }
            UUID uuid = p.getUniqueId();
            if (previous.containsKey(uuid)) {
                List<ItemStack> previousSet = previous.get(uuid);
                for (ItemStack stack : current) {
                    if (!previousSet.contains(stack)) {
                        pm.callEvent(new ArmorWornEvent(p, stack));
                    }
                }
                for (ItemStack stack : previousSet) {
                    if (!current.contains(stack)) {
                        pm.callEvent(new ArmorRemovedEvent(p, stack));
                    }
                }
            } else {
                for (ItemStack stack : current) {
                    pm.callEvent(new ArmorWornEvent(p, stack));
                }
            }
            previous.put(uuid, current);
        }
    }
}
