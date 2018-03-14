package net.fairfieldtek.minecraft.worldeditor.commands.tasks;

import java.util.UUID;
import net.fairfieldtek.minecraft.Initialization;
import net.fairfieldtek.minecraft.Util.BlockUtil;
import net.fairfieldtek.minecraft.worldeditor.container.BlockDef;
import net.fairfieldtek.minecraft.worldeditor.container.PlayerInfo;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class DeleteTask
        extends BukkitRunnable {

    int X = 0;
    int Y = 0;
    int Z = 0;
    int sx;
    int ex;
    int sy;
    int ey;
    int sz;
    int ez;
    boolean Cancel = false;
    UUID PlayerId;
    BlockDef EmptyDef;

    public DeleteTask(Player player) {
        this.PlayerId = player.getUniqueId();
        PlayerInfo pi = Initialization.PlayerInfoList.get(player);
        if (pi.SelectEnd == null || pi.SelectStart == null) {
            this.Cancel = true;
        }
        if (pi.SelectStart.X > pi.SelectEnd.X) {
            this.sx = pi.SelectStart.X;
            this.ex = pi.SelectEnd.X;
        } else {
            this.ex = pi.SelectStart.X;
            this.sx = pi.SelectEnd.X;
        }
        if (pi.SelectStart.Y > pi.SelectEnd.Y) {
            this.sy = pi.SelectStart.Y;
            this.ey = pi.SelectEnd.Y;
        } else {
            this.ey = pi.SelectStart.Y;
            this.sy = pi.SelectEnd.Y;
        }
        if (pi.SelectStart.Z > pi.SelectEnd.Z) {
            this.sz = pi.SelectStart.Z;
            this.ez = pi.SelectEnd.Z;
        } else {
            this.ez = pi.SelectStart.Z;
            this.sz = pi.SelectEnd.Z;
        }
        this.X = this.sx;
        this.Y = this.sy;
        this.Z = this.sz;
        this.EmptyDef = new BlockDef();
        this.EmptyDef.setBlockFaceCode("");
        this.EmptyDef.setInverted(false);
        this.EmptyDef.setIsStairs(false);
        this.EmptyDef.setMaterialData((byte) 0);
        this.EmptyDef.setMaterialType(Material.AIR.name());
    }

    @Override
    public void run() {
        try {
            Player player = Initialization.Plugin.getServer().getPlayer(this.PlayerId);
            if (player == null || this.Cancel) {
                this.cancel();
            }
            PlayerInfo pi = Initialization.PlayerInfoList.get(player);
            
            pi.ClipBoard.clear();
            
            World world = player.getWorld();
            int counter = 0;
            while (this.Y >= this.ey) {
                while (this.X >= this.ex) {
                    while (this.Z >= this.ez) {
                        if (++counter > 4000) {
                            try {
                                player.sendMessage("Buffering... " + this.X + " " + this.Y + " " + this.Z);
                            } catch (Exception e) {
                                this.cancel();
                            }
                            return;
                        }
                        this.EmptyDef.setX(this.X);
                        this.EmptyDef.setY(this.Y);
                        this.EmptyDef.setZ(this.Z);
                        Block changeBlock = world.getBlockAt(this.X, this.Y, this.Z);
                        
                        pi.ClipBoard.add(BlockUtil.GetBlockDef(changeBlock, this.X, this.Y, this.Z, player));
                        
                        BlockUtil.SetBlock(changeBlock, this.EmptyDef, player, true);
                        --this.Z;
                    }
                    this.Z = this.sz;
                    --this.X;
                }
                this.X = this.sx;
                --this.Y;
            }
            Initialization.PlayerInfoList.get(player).setIsProcessing(false,"Delete");
            player.sendMessage("Finished Deleting Blocks.");
        } catch (Exception player) {
            // empty catch block
        }
        this.cancel();
    }
}
