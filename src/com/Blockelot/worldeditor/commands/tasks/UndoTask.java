package com.Blockelot.worldeditor.commands.tasks;

import java.util.ListIterator;
import com.Blockelot.PluginManager;
import com.Blockelot.Util.ServerUtil;
import com.Blockelot.worldeditor.container.BlockCollection;
import com.Blockelot.worldeditor.container.BlockInfo;
import com.Blockelot.worldeditor.container.PlayerInfo;
import org.bukkit.Material;
import org.bukkit.scheduler.BukkitRunnable;

public class UndoTask
        extends BukkitRunnable {

    BlockCollection SchematicClip;
    int ClipBoardCount = 0;
    PlayerInfo pi;

    public UndoTask(PlayerInfo p) {
        pi = p;
        SchematicClip = pi.GetUndo().Clone();
        ClipBoardCount = this.SchematicClip.Size();
    }

    @Override
    public void run() {
        try {
            if (pi.CancelLastAction) {
                pi.CancelLastAction = false;
                this.cancel();
            }
            int counter = 0;
            ListIterator<BlockInfo> iter = this.SchematicClip.getBlocks().listIterator(this.SchematicClip.getBlocks().size());
            while (iter.hasPrevious()) {
                if (++counter > PluginManager.Config.MaxBlocksWritePerTick) {
                    try {
                        pi.getPlayer().sendMessage("Buffering... " + this.SchematicClip.Size() + " left.");
                    } catch (Exception e) {
                        this.cancel();
                    }
                    return;
                }
                BlockInfo itm = iter.previous();
                boolean eraseWater = true;
                if (itm.getBlockMaterial() == Material.WATER || itm.getBlockMaterial() == Material.LAVA) {
                    eraseWater = false;
                }

                if (!itm.ApplyBlockInfoToBlock(pi.getPlayer().getWorld().getBlockAt(itm.getX(), itm.getY(), itm.getZ()), eraseWater, null)) {
                    return;
                }

                iter.remove();
            }

            pi.getPlayer().sendMessage("Blocks undone (" + this.ClipBoardCount + ")");
        } catch (Exception e) {
            ServerUtil.consoleLog(e.getLocalizedMessage());
            ServerUtil.consoleLog(e.getMessage());
        }

        PluginManager.PlayerInfoList.get(pi.getPlayer()).setIsProcessing(false, "Undo");
        this.cancel();
    }
}
