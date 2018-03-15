package net.fairfieldtek.minecraft.worldeditor.commands.tasks;

import java.util.ArrayList;
import java.util.ListIterator;
import java.util.UUID;
import net.fairfieldtek.minecraft.Initialization;
import net.fairfieldtek.minecraft.Util.BlockUtil;
import net.fairfieldtek.minecraft.worldeditor.container.BlockDef;
import net.fairfieldtek.minecraft.worldeditor.container.PlayerInfo;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import net.fairfieldtek.minecraft.worldeditor.container.SchematicDef;

public class UndoTask
        extends BukkitRunnable {

    SchematicDef SchematicClip;

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
        ListIterator<BlockDef> iter = this.SchematicClip.getBlocks().listIterator();
        while (iter.hasNext()) {
            if (++counter > 4000) {
                try {
                    player.sendMessage("Buffering... " + this.SchematicClip.Size() + " left.");
                } catch (Exception e) {
                    this.cancel();
                }
                return;
            }
            BlockDef itm = iter.next();
            Block changeBlock = world.getBlockAt(itm.getX(), itm.getY(), itm.getZ());
            //BlockUtil.SetBlock(changeBlock, itm, player, true);

            itm.SetBlock(changeBlock, player, true);

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
