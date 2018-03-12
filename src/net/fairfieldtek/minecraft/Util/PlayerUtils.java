package net.fairfieldtek.minecraft.Util;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.BlockIterator;

public class PlayerUtils {

    public static final Block getTargetBlock(Player player, int range) {
        BlockIterator iter = new BlockIterator((LivingEntity) player, range);
        Block lastBlock = iter.next();
        while (iter.hasNext() && (lastBlock = iter.next()).getType() == Material.AIR) {
        }
        return lastBlock;
    }
}
