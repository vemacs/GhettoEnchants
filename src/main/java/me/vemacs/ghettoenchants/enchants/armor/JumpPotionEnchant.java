package me.vemacs.ghettoenchants.enchants.armor;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class JumpPotionEnchant extends AbstractAmbientEnchant {
    public JumpPotionEnchant(int level) {
        super(level);
    }

    @Override
    public void armorWorn(Player player) {
        PotionEffect potion = new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, level - 1);
        player.addPotionEffect(potion);
    }

    @Override
    public void armorRemoved(Player player) {
        player.removePotionEffect(PotionEffectType.JUMP);
    }

    @Override
    public String getName() {
        return "Jump";
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }

    @Override
    public void perform(Event paramEvent) {
        // ignored
    }
}
