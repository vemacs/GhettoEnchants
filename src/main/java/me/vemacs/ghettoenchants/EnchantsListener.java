package me.vemacs.ghettoenchants;

import me.vemacs.ghettoenchants.enchants.BaseEnchant;
import me.vemacs.ghettoenchants.enchants.pickaxe.ExplodePickEnchant;
import me.vemacs.ghettoenchants.utils.EnchantUtils;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.List;

public class EnchantsListener implements Listener {
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        try {
            List<BaseEnchant> elist = EnchantUtils.readEnchants(event.getPlayer().getItemInHand());
            for (BaseEnchant e : elist) {
                e.perform(event);
            }
        } catch (Exception ignored) {
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if ((event.getEntity() instanceof Player)) {
            if ((ExplodePickEnchant.getInvincible().contains(event.getEntity().getName())) &&
                    (event.getCause() == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerDamageEntity(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player && event.getEntity() instanceof LivingEntity) {
            Player p = (Player) event.getDamager();
            try {
                List<BaseEnchant> elist = EnchantUtils.readEnchants(p.getItemInHand());
                for (BaseEnchant e : elist) {
                    e.perform(event);
                }
            } catch (Exception ignored) {
            }
        }
    }
}
