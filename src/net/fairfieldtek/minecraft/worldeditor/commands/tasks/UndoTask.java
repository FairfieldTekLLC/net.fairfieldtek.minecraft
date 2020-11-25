package net.fairfieldtek.minecraft.worldeditor.commands.tasks;

import java.util.ArrayList;
import java.util.ListIterator;
import java.util.UUID;
import net.fairfieldtek.minecraft.Initialization;
import net.fairfieldtek.minecraft.Util.BlockUtil;
import net.fairfieldtek.minecraft.worldeditor.container.BlockInfo;
import net.fairfieldtek.minecraft.worldeditor.container.PlayerInfo;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import net.fairfieldtek.minecraft.worldeditor.container.BlockCollection;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.entity.*;

public class UndoTask
        extends BukkitRunnable {

    BlockCollection SchematicClip;

    //ArrayList<BlockDef> ClipBoard = new ArrayList();
    UUID PlayerId;
    int ClipBoardCount = 0;

    public UndoTask(Player player) {
        this.PlayerId = player.getUniqueId();
        PlayerInfo pi = Initialization.PlayerInfoList.get(player);
        this.SchematicClip = pi.GetUndo();
        this.ClipBoardCount = this.SchematicClip.Size();
    }

    @Override
    public void run() {
        Player player = Initialization.Plugin.getServer().getPlayer(this.PlayerId);
        PlayerInfo pi = Initialization.PlayerInfoList.get(player);
        if (pi.CancelLastAction) {
            pi.CancelLastAction = false;
            this.cancel();
        }
        if (player == null) {
            this.cancel();
        }
        World world = player.getWorld();
        int counter = 0;
        ListIterator<BlockInfo> iter = this.SchematicClip.getBlocks().listIterator(this.SchematicClip.getBlocks().size());
        while (iter.hasPrevious()) {
            if (++counter > 4000) {
                
                
                
                
                try {
                    player.sendMessage("Buffering... " + this.SchematicClip.Size() + " left.");
                } catch (Exception e) {
                    this.cancel();
                }
                return;
            }
            BlockInfo itm = iter.previous();

            //System.out.println("Material is: " + itm.getBlockMaterial().name());
            boolean eraseWater = true;

            if (itm.getBlockMaterial() == Material.WATER || itm.getBlockMaterial() == Material.LAVA) {
                eraseWater = false;
            }

            Block changeBlock = world.getBlockAt(itm.getX(), itm.getY(), itm.getZ());
            //BlockUtil.SetBlock(changeBlock, itm, player, true);

//            Chunk chunk = world.getChunkAt(changeBlock);
            
//            Entity[] mobs = chunk.getEntities();
//            for (Entity mob : mobs) {
//                mob.remove();
//            }

            itm.SetBlock(changeBlock, player, eraseWater);

            iter.remove();
        }
        try {
            player.sendMessage("Blocks undone (" + this.ClipBoardCount + ")");
            Initialization.PlayerInfoList.get(player).setIsProcessing(false, "Undo");
        } catch (Exception itm) {
            // empty catch block
        }
        this.cancel();
    }
}
