package me.vemacs.ghettoenchants;

import me.vemacs.ghettoenchants.enchants.BaseEnchant;
import me.vemacs.ghettoenchants.enchants.tools.pickaxe.ExplodePickEnchant;
import me.vemacs.ghettoenchants.event.ArmorRemovedEvent;
import me.vemacs.ghettoenchants.event.ArmorWornEvent;
import me.vemacs.ghettoenchants.utils.EnchantUtils;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class EnchantsListener implements Listener {
    private static EnchantUtils utils;

    public EnchantsListener() {
        utils = EnchantsPlugin.getUtils();
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event) {
        performEnchants(event, event.getPlayer().getItemInHand());
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onEntityDamage(EntityDamageEvent event) {
        if ((event.getEntity() instanceof Player)) {
            if ((ExplodePickEnchant.getInvincible().contains(event.getEntity().getName())) &&
                    (event.getCause() == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerDamageEntity(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player && event.getEntity() instanceof LivingEntity) {
            Player p = (Player) event.getDamager();
            performEnchants(event, p.getItemInHand());
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onEntityDamagePlayer(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof LivingEntity && event.getEntity() instanceof Player) {
            Player p = (Player) event.getEntity();
            performEnchants(event, p.getInventory().getArmorContents());
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerFish(PlayerFishEvent event) {
        performEnchants(event, event.getPlayer().getItemInHand());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onArmorWorn(ArmorWornEvent event) {
        performEnchants(event, event.getItemStack());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onArmorRemoved(ArmorRemovedEvent event) {
        performEnchants(event, event.getItemStack());
    }

    public static void performEnchants(Event event, ItemStack... items) {
        try {
            List<BaseEnchant> elist = new ArrayList<>();
            for (ItemStack is : items) {
                elist.addAll(utils.readEnchants(is));
            }
            for (BaseEnchant e : elist) {
                e.perform(event);
            }
        } catch (Exception ignored) {
        }
    }
}
