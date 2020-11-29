package com.Blockelot.worldeditor.commands.tasks;

import java.util.ListIterator;
import java.util.UUID;
import com.Blockelot.PluginManager;
import com.Blockelot.worldeditor.container.BlockCollection;
import com.Blockelot.worldeditor.container.BlockInfo;
import com.Blockelot.worldeditor.container.PlayerInfo;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class UndoTask
        extends BukkitRunnable {

    BlockCollection SchematicClip;
//    UUID PlayerId;
    int ClipBoardCount = 0;
    PlayerInfo pi;
//    Player Player;
//    World World;

    public UndoTask(PlayerInfo p) {
        pi = p;
//        Player = player;
//        pi = PluginManager.PlayerInfoList.get(player);
//        PlayerId = player.getUniqueId();
        SchematicClip = pi.GetUndo().Clone();
        ClipBoardCount = this.SchematicClip.Size();
//        World = player.getWorld();
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
                if (++counter > 4000) {
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
            System.out.println(e.getLocalizedMessage());
            System.out.println(e.getMessage());
        }

        PluginManager.PlayerInfoList.get(pi.getPlayer()).setIsProcessing(false, "Undo");
        this.cancel();
    }
}
