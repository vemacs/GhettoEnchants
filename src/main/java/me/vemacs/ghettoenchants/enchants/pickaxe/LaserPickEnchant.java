package me.vemacs.ghettoenchants.enchants.pickaxe;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.util.BlockIterator;

import java.util.Iterator;

public class LaserPickEnchant extends AbstractPickEnchant {
    public LaserPickEnchant(int level) {
        super(level);
    }

    @Override
    public void perform(BlockBreakEvent e) {
        Iterator<Block> bi = new BlockIterator(e.getPlayer(), 8);
        int c = 0;
        while (bi.hasNext()) {
            Block b = bi.next();
            if (b.getType() != Material.AIR) {
                b.breakNaturally();
                c++;
                if (c == level) {
                    return;
                }
            }
        }
    }

    @Override
    public String getName() {
        return "Laser";
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }
}
