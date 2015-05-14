package me.vemacs.ghettoenchants.enchants.pickaxe;

import com.google.common.collect.ImmutableMap;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class AutosmeltEnchant extends AbstractPickEnchant {
    private static final Map<Material, Material> smeltTo = ImmutableMap.<Material, Material>builder()
            .put(Material.GOLD_ORE, Material.GOLD_INGOT)
            .put(Material.IRON_ORE, Material.IRON_INGOT)
            .put(Material.COBBLESTONE, Material.STONE)
            .build();

    public AutosmeltEnchant(int level) {
        super(level);
    }

    @Override
    public void perform(BlockBreakEvent e) {
        Block block = e.getBlock();
        if (smeltTo.containsKey(block.getType())) {
            block.setType(Material.AIR);
            block.getWorld().dropItemNaturally(block.getLocation(),
                    new ItemStack(smeltTo.get(block.getType()), 1));
            e.setCancelled(true);
        }
    }

    @Override
    public String getName() {
        return "Auto Smelt";
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }
}
