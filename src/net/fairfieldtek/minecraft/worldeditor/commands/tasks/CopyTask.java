package net.fairfieldtek.minecraft.worldeditor.commands.tasks;

import java.util.ArrayList;
import java.util.UUID;
import net.fairfieldtek.minecraft.Initialization;
import net.fairfieldtek.minecraft.Util.BlockUtil;
import net.fairfieldtek.minecraft.worldeditor.container.BlockDef;
import net.fairfieldtek.minecraft.worldeditor.container.PlayerInfo;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import net.fairfieldtek.minecraft.worldeditor.container.SchematicDef;

public class CopyTask
        extends BukkitRunnable {

    int sbx;
    int sex;
    int sby;
    int sey;
    int sbz;
    int sez;
    int cx = 0;
    int cy = 0;
    int cz = 0;
    UUID PlayerId;
    //ArrayList<BlockDef> ClipBoard = new ArrayList();
    SchematicDef SchematicToPaste = new SchematicDef();

    public CopyTask(int bx, int ex, int by, int ey, int bz, int ez, UUID playerId) {
        this.sbx = bx;
        this.sex = ex;
        this.sby = by;
        this.sey = ey;
        this.sbz = bz;
        this.sez = ez;
        this.cx = this.sbx;
        this.cy = this.sby;
        this.cz = this.sbz;
        this.PlayerId = playerId;
    }

    @Override
    public void run() {
        try {
            Player player = Initialization.Plugin.getServer().getPlayer(this.PlayerId);
            PlayerInfo pi = Initialization.PlayerInfoList.get(player);

            if (player == null) {
                Initialization.PlayerInfoList.get(player).setIsProcessing(false, "Copy");
                this.cancel();
            }

            int counter = 0;
            World world = player.getWorld();
            while (this.cy <= this.sey) {
                while (this.cx <= this.sex) {
                    while (this.cz <= this.sez) {

                        this.SchematicToPaste.AddBlock(world.getBlockAt(this.cx, this.cy, this.cz), this.sbx, this.sby, this.sbz, player);

                        //this.ClipBoard.add(BlockUtil.GetBlockDef(world.getBlockAt(this.cx, this.cy, this.cz), this.sbx, this.sby, this.sbz, player));
                        ++this.cz;
                        if (++counter > 32000) {
                            try {
                                player.sendMessage("Copied " + this.SchematicToPaste.Size() + " blocks so far.. waiting..");
                            } catch (Exception e) {
                                System.out.println(e.getLocalizedMessage());
                                System.out.println(e.getMessage());
                            }
                            return;
                        }

                    }
                    ++this.cx;
                    this.cz = this.sbz;
                }
                ++this.cy;
                this.cx = this.sbx;
            }
            pi.ClipSchematic = this.SchematicToPaste;

            //pi.ClipBoard = this.ClipBoard;
            player.sendMessage("Blocks Copied (" + pi.ClipSchematic.Size() + " blocks copied.)");
            Initialization.PlayerInfoList.get(player).setIsProcessing(false, "Copy");
            this.cancel();
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
            System.out.println(e.getMessage());

            this.cancel();
        }
    }
}
