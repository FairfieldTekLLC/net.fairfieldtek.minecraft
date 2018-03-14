package net.fairfieldtek.minecraft.worldeditor.commands.tasks;

import java.util.ArrayList;
import java.util.ListIterator;
import java.util.UUID;
import net.fairfieldtek.minecraft.Initialization;
import net.fairfieldtek.minecraft.Util.BlockUtil;
import net.fairfieldtek.minecraft.worldeditor.container.BlockDef;
import net.fairfieldtek.minecraft.worldeditor.container.PlayerInfo;
import net.fairfieldtek.minecraft.worldeditor.enumeration.Axis;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class PasteTask
        extends BukkitRunnable {

    
    /*
    Todo:  Need to make this two pass,
    First Pass: render everything but Beds,
    Second Pass: render beds
    Need to research if any other objects use two blocks to represent itself.
    */
    
    
    ArrayList<BlockDef> ClipBoard = new ArrayList();
    ArrayList<BlockDef> UndoBuffer = new ArrayList();
    UUID PlayerId;
    int X;
    int Y;
    int Z;
    Axis Axis;
    double Degrees;

    public PasteTask(Player player, int x, int y, int z, Axis axis, double degrees) {
        this.PlayerId = player.getUniqueId();
        this.X = x;
        this.Y = y;
        this.Z = z;
        this.Axis = axis;
        this.Degrees = degrees;
        PlayerInfo pi = Initialization.PlayerInfoList.get(player);
        this.ClipBoard = pi.ClipBoard;
    }

    @Override
    public void run() {
        
        Player player = Initialization.Plugin.getServer().getPlayer(this.PlayerId);
        
        try
        {
        PlayerInfo pi = Initialization.PlayerInfoList.get(player);
        if (pi.CancelLastAction) {
            pi.CancelLastAction = false;
            this.cancel();
        }
        if (player == null) {
            this.cancel();
        }
        double radians = Math.toRadians(this.Degrees);
        int counter = 0;
        ListIterator<BlockDef> iter = this.ClipBoard.listIterator();
        while (iter.hasNext()) {
            if (++counter > 8000) {
                try {
                    player.sendMessage("Buffering... " + this.ClipBoard.size() + " left.");
                } catch (Exception e) {
                    this.cancel();
                }
                return;
            }
            BlockDef itm = iter.next();
            double dx = itm.getX();
            double dy = itm.getY();
            double dz = itm.getZ();
            if (this.Degrees > 0.0) {
                switch (this.Axis) {
                    case X: {
                        dy = (double) itm.getY() * Math.cos(radians) - (double) itm.getZ() * Math.sin(radians);
                        dz = (double) itm.getY() * Math.sin(radians) + (double) itm.getZ() * Math.cos(radians);
                        if (itm.getBlockFaceCode().equals("")) {
                            break;
                        }
                        
                        itm.GetRotX((int) this.Degrees);
                        
                        break;
                    }
                    case Y: {
                        dz = (double) itm.getZ() * Math.cos(radians) - (double) itm.getX() * Math.sin(radians);
                        dx = (double) itm.getZ() * Math.sin(radians) + (double) itm.getX() * Math.cos(radians);
                        if (itm.getBlockFaceCode().equals("")) {
                            break;
                        }
                        itm.GetRotY((int) this.Degrees);
                        //itm = BlockUtil.GetRotY(itm, (int) this.Degrees);
                        break;
                    }
                    case Z: {
                        dx = (double) itm.getX() * Math.cos(radians) - (double) itm.getY() * Math.sin(radians);
                        dy = (double) itm.getX() * Math.sin(radians) + (double) itm.getY() * Math.cos(radians);
                        if (itm.getBlockFaceCode().equals("")) {
                            break;
                        }
                        itm.GetRotZ((int) this.Degrees);
                        
                        //itm = BlockUtil.GetRotZ(itm, (int) this.Degrees);
                    }
                }
            }
            int x = (int) Math.round(dx) + this.X;
            int y = (int) Math.round(dy) + this.Y + 1;
            int z = (int) Math.round(dz) + this.Z;
            Block changeBlock = player.getWorld().getBlockAt(x, y, z);
            this.UndoBuffer.add(0, BlockUtil.GetBlockDef(changeBlock, 0, 0, 0, player));
            BlockUtil.SetBlock(changeBlock, itm, player, false);
            iter.remove();
        }
        
        
        pi.UndoBuffer = this.UndoBuffer;
        
            player.sendMessage("Blocks Pasted (" + this.UndoBuffer.size() + ")");
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
            System.out.println(e.getMessage());
            
        }
        Initialization.PlayerInfoList.get(player).setIsProcessing(false,"Paste");
        this.cancel();
    }

}
