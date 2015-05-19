package me.vemacs.ghettoenchants.enchants.tools.pickaxe;

import org.bukkit.event.block.BlockBreakEvent;

public class DemoPickEnchant extends AbstractPickEnchant {
    public DemoPickEnchant(int level) {
        super(level);
    }

    @Override
    public void perform(BlockBreakEvent e) {
        org.bukkit.block.Block b = e.getBlock();
        e.getPlayer().sendMessage(
                String.format(org.bukkit.ChatColor.YELLOW + "Block broken at %d, %d, %d by %s, enchant level %d",
                        b.getX(), b.getY(), b.getZ(), e.getPlayer().getName(), level));
    }

    @Override
    public String getName() {
        return "Demo";
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }
}
