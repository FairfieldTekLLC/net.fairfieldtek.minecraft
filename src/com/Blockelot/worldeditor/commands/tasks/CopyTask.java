package com.Blockelot.worldeditor.commands.tasks;

import java.util.UUID;
import com.Blockelot.PluginManager;
import com.Blockelot.Util.ServerUtil;
import com.Blockelot.worldeditor.container.BlockCollection;
import com.Blockelot.worldeditor.container.PlayerInfo;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

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
    BlockCollection SchematicToPaste = new BlockCollection();

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
        Player player = PluginManager.Plugin.getServer().getPlayer(this.PlayerId);
        try {

            PlayerInfo pi = PluginManager.PlayerInfoList.get(player);
            

            if (player == null) {
                PluginManager.PlayerInfoList.get(player).setIsProcessing(false, "Copy");
                this.cancel();
            }

            int counter = 0;
            World world = player.getWorld();
            while (this.cy <= this.sey) {
                while (this.cx <= this.sex) {
                    while (this.cz <= this.sez) {

                        this.SchematicToPaste.AddBlock(world.getBlockAt(this.cx, this.cy, this.cz), this.sbx, this.sby, this.sbz, null);
                        ++this.cz;
                        if (++counter > 16000) {
                            try {
                                player.sendMessage("Copied " + this.SchematicToPaste.Size() + " blocks so far.. waiting..");
                            } catch (Exception e) {
                                ServerUtil.consoleLog(e.getLocalizedMessage());
                                ServerUtil.consoleLog(e.getMessage());
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
            player.sendMessage("Blocks Copied (" + pi.ClipSchematic.Size() + " blocks copied.)");
            PluginManager.PlayerInfoList.get(player).setIsProcessing(false, "Copy");
            this.cancel();
        } catch (Exception e) {
            ServerUtil.consoleLog(e.getLocalizedMessage());
            ServerUtil.consoleLog(e.getMessage());

        }
        PluginManager.PlayerInfoList.get(player).setIsProcessing(false, "Copy");
        this.cancel();
    }
}
