//End-User License Agreement (EULA) of Blockelot
//
//This End-User License Agreement ("EULA") is a legal agreement between you and Fairfield Tek L.L.C.. Our EULA was created by EULA Template for Blockelot.
//
//This EULA agreement governs your acquisition and use of our Blockelot software ("Software") directly from Fairfield Tek L.L.C. or indirectly through a Fairfield Tek L.L.C. authorized reseller or distributor (a "Reseller"). Our Privacy Policy was created by the Privacy Policy Generator.
//
//Please read this EULA agreement carefully before completing the installation process and using the Blockelot software. It provides a license to use the Blockelot software and contains warranty information and liability disclaimers.
//
//If you register for a free trial of the Blockelot software, this EULA agreement will also govern that trial. By clicking "accept" or installing and/or using the Blockelot software, you are confirming your acceptance of the Software and agreeing to become bound by the terms of this EULA agreement.
//
//If you are entering into this EULA agreement on behalf of a company or other legal entity, you represent that you have the authority to bind such entity and its affiliates to these terms and conditions. If you do not have such authority or if you do not agree with the terms and conditions of this EULA agreement, do not install or use the Software, and you must not accept this EULA agreement.
//
//This EULA agreement shall apply only to the Software supplied by Fairfield Tek L.L.C. herewith regardless of whether other software is referred to or described herein. The terms also apply to any Fairfield Tek L.L.C. updates, supplements, Internet-based services, and support services for the Software, unless other terms accompany those items on delivery. If so, those terms apply.
//
//License Grant
//Fairfield Tek L.L.C. hereby grants you a personal, non-transferable, non-exclusive licence to use the Blockelot software on your devices 
//in accordance with the terms of this EULA agreement.
//
//You are permitted to load the Blockelot software (for example a PC, laptop, mobile or tablet) under your control. You are responsible
//for ensuring your device meets the minimum requirements of the Blockelot software.
//
//You are not permitted to:
//
//Edit, alter, modify, adapt, translate or otherwise change the whole or any part of the Software nor permit the whole or any part of
//the Software to be combined with or become incorporated in any other software, nor decompile, disassemble or reverse engineer the 
//Software or attempt to do any such things
//
//Reproduce, copy, distribute, resell or otherwise use the Software for any commercial purpose
//Allow any third party to use the Software on behalf of or for the benefit of any third party
//Use the Software in any way which breaches any applicable local, national or international law
//use the Software for any purpose that Fairfield Tek L.L.C. considers is a breach of this EULA agreement
//Intellectual Property and Ownership
//Fairfield Tek L.L.C. shall at all times retain ownership of the Software as originally downloaded by you and all subsequent downloads
// of the Software by you. The Software (and the copyright, and other intellectual property rights of whatever nature in the Software,
// including any modifications made thereto) are and shall remain the property of Fairfield Tek L.L.C..
//Fairfield Tek L.L.C. reserves the right to grant licences to use the Software to third parties.
//Termination
//This EULA agreement is effective from the date you first use the Software and shall continue until terminated. 
//You may terminate it at any time upon written notice to Fairfield Tek L.L.C..
//It will also terminate immediately if you fail to comply with any term of this EULA agreement. Upon such termination,
// the licenses granted by this EULA agreement will immediately terminate and you agree to stop all access and use of the Software.
//The provisions that by their nature continue and survive will survive any termination of this EULA agreement.
//
//Governing Law
//This EULA agreement, and any dispute arising out of or in connection with this EULA agreement, 
//shall be governed by and construed in accordance with the laws of us.
//
//By accepting this EULA, you agree to hold harmless (Blockelot) FairfieldTek in the event that the cloud storage service is discontinued.
//
//Blockelot and it's Cloud Storage is provided "as is", without warranties of any kind.
package com.Blockelot.worldeditor.commands.tasks;

import java.util.ListIterator;
import java.util.UUID;
import com.Blockelot.PluginManager;
import com.Blockelot.Util.PlayerUtils;
import com.Blockelot.Util.ServerUtil;
import com.Blockelot.worldeditor.container.BlockCollection;
import com.Blockelot.worldeditor.container.BlockInfo;
import com.Blockelot.worldeditor.container.PlayerInfo;
import com.Blockelot.worldeditor.enumeration.Axis;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.block.BlockFace;
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
    BlockCollection RotatedSchematicClipboard = new BlockCollection();
    BlockCollection SchematicClipboard;
    BlockCollection SchematicUndo;
    ArrayList<BlockInfo> ApplyLast = new ArrayList<>();

    UUID PlayerId;
    int X;
    int Y;
    int Z;
    Axis Axis;
    double Degrees;
    boolean FinishedRotation = false;

    int minX = Integer.MAX_VALUE;
    int minY = Integer.MAX_VALUE;
    int minZ = Integer.MAX_VALUE;

    int maxX = Integer.MIN_VALUE;
    int maxY = Integer.MIN_VALUE;
    int maxZ = Integer.MIN_VALUE;

    BlockFace PlayerBlockFace;

    int finalOffsetX = 0;
    int finalOffsetY = 0;
    int finalOffsetZ = 0;
    PlayerInfo pi;

    public PasteTask(Player player, int x, int y, int z, Axis axis, double degrees) {
        this.PlayerId = player.getUniqueId();
        this.X = x;
        this.Y = y;
        this.Z = z;
        this.Axis = axis;
        this.Degrees = degrees;
        pi = PluginManager.GetPlayerInfo(player.getUniqueId());
        this.SchematicClipboard = pi.ClipSchematic.Clone();
        this.SchematicUndo = pi.NewUndo();
        this.SchematicUndo.Clear();
        PlayerBlockFace = PlayerUtils.getCardinalDirection(player);
    }

    public BlockInfo Process(BlockInfo itm, double radians) {
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
                    break;
                }
                case Z: {

                    dx = (double) itm.getX() * Math.cos(radians) - (double) itm.getY() * Math.sin(radians);
                    dy = (double) itm.getX() * Math.sin(radians) + (double) itm.getY() * Math.cos(radians);
                    if (itm.getBlockFaceCode().equals("")) {
                        break;
                    }
                    itm.GetRotZ((int) this.Degrees);
                }
            }
        }
        int x = (int) Math.round(dx) + this.X;
        int y = (int) Math.round(dy) + this.Y + 1;
        int z = (int) Math.round(dz) + this.Z;

        itm.setX(x);
        itm.setY(y);
        itm.setZ(z);
        return itm;
    }

    public class XYZ {

        public int X;
        public int Y;
        public int Z;
    }

    public XYZ GetFinalOffset(ArrayList<BlockInfo> list, int x, int y, int z) {
        for (BlockInfo itm : list) {
            if (itm.getX() < minX) {
                minX = itm.getX();
            }
            if (itm.getZ() < minZ) {
                minZ = itm.getZ();
            }
            if (itm.getY() < minY) {
                minY = itm.getY();
            }
            if (itm.getX() > maxX) {
                maxX = itm.getX();
            }
            if (itm.getZ() > maxZ) {
                maxZ = itm.getZ();
            }
            if (itm.getY() > maxY) {
                maxY = itm.getY();
            }
        }

        XYZ pt = new XYZ();
        pt.X = x - minX;
        pt.Y = y - minY;
        pt.Z = z - minZ;
        return pt;

    }

    @Override
    public void run() {
        Player player = PluginManager.Plugin.getServer().getPlayer(this.PlayerId);
//        try {
        PlayerInfo pi = PluginManager.GetPlayerInfo(player.getUniqueId());
        if (pi.CancelLastAction) {
            pi.CancelLastAction = false;
            this.cancel();
        }
        if (player == null) {
            this.cancel();
        }
        double radians = Math.toRadians(this.Degrees);

        int counter = 0;

        ListIterator<BlockInfo> iter = this.SchematicClipboard.getBlocks().listIterator();
        while (iter.hasNext()) {
            if (++counter > PluginManager.Config.MaxBlocksWritePerTick) {
                try {
                    player.sendMessage("Buffering... " + this.SchematicClipboard.getBlocks().size() + " left.");
                } catch (Exception e) {
                    this.cancel();
                }
                return;
            }

            BlockInfo itm = iter.next();
            itm = Process(itm, radians);
            try {
                this.RotatedSchematicClipboard.AddBlock(itm, null);
            } catch (Exception ex) {
                Logger.getLogger(PasteTask.class.getName()).log(Level.SEVERE, null, ex);
            }
            iter.remove();

        }

        if (this.SchematicClipboard.Size() == 0 && !FinishedRotation) {
            FinishedRotation = true;
            XYZ finalOffset = GetFinalOffset(this.RotatedSchematicClipboard.getBlocks(), this.X, this.Y, this.Z);
            finalOffsetX = finalOffset.X;
            finalOffsetY = finalOffset.Y;
            finalOffsetZ = finalOffset.Z;
        }

        counter = 0;

        iter = this.RotatedSchematicClipboard.getBlocks().listIterator();

        while (iter.hasNext()) {
            if (++counter > 10000) {
                try {
                    player.sendMessage("Buffering... " + this.RotatedSchematicClipboard.getBlocks().size() + " left.");
                } catch (Exception e) {
                    this.cancel();
                }
                return;
            }
            BlockInfo itm = iter.next();
            int foz = finalOffsetZ;
            int fox = finalOffsetX;
            int foy = finalOffsetY;

            try {
                switch (PlayerBlockFace) {
                    case WEST:
                    case NORTH_WEST:
                        foz = finalOffsetZ - (maxZ - minZ);
                        fox = finalOffsetX - (maxX - minX);
                        break;
                    case SOUTH:
                    case SOUTH_WEST:
                        fox = finalOffsetX - (maxX - minX);
                        break;
                    case NORTH:
                    case NORTH_EAST:
                        foz = finalOffsetZ - (maxZ - minZ);
                        break;
                    case SOUTH_EAST:
                        break;
                }
            } catch (Exception e) {
                ServerUtil.consoleLog("Cannot determine facing.");
            }

            int x = itm.getX() + fox;
            int y = itm.getY() + foy + 1;
            int z = itm.getZ() + foz;

            if (!itm.IsDoor()) {
                try {
                    itm.ApplyBlockInfoToBlock(player.getWorld().getBlockAt(x, y, z), false, this.SchematicUndo, pi);
                } catch (Exception ex) {
                    Logger.getLogger(PasteTask.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                ApplyLast.add(itm);
            }

            iter.remove();
        }

        counter = 0;

        //Now we place door's stairs and such.
        iter = BlockCollection.SortYAscending(ApplyLast).listIterator();
        while (iter.hasNext()) {
            BlockInfo itm = iter.next();
            int foz = finalOffsetZ;
            int fox = finalOffsetX;
            int foy = finalOffsetY;
            try {
                switch (PlayerBlockFace) {
                    case WEST:
                    case NORTH_WEST:
                        foz = finalOffsetZ - (maxZ - minZ);
                        fox = finalOffsetX - (maxX - minX);
                        break;
                    case SOUTH:
                    case SOUTH_WEST:
                        fox = finalOffsetX - (maxX - minX);
                        break;
                    case NORTH:
                    case NORTH_EAST:
                        foz = finalOffsetZ - (maxZ - minZ);
                        break;
                    case SOUTH_EAST:
                        break;
                }
            } catch (Exception e) {
                ServerUtil.consoleLog("Cannot determine facing.");
            }
            int x = itm.getX() + fox;
            int y = itm.getY() + foy + 1;
            int z = itm.getZ() + foz;

            if ((this.Axis == Axis.Z) && (this.Degrees == 180)) {

                try {
                    itm.ApplyBlockInfoToBlock(player.getWorld().getBlockAt(x, y - 1, z), false, this.SchematicUndo, pi);
                } catch (Exception ex) {
                    // Logger.getLogger(PasteTask.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {

                try {
                    itm.ApplyBlockInfoToBlock(player.getWorld().getBlockAt(x, y, z), false, SchematicUndo, true, pi);
                } catch (Exception ex) {
                    //Logger.getLogger(PasteTask.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            iter.remove();
        }
        player.sendMessage("Blocks Pasted (" + this.SchematicUndo.Size() + ")");
//        } catch (Exception e) {
//            ServerUtil.consoleLog(e.getLocalizedMessage());
//            ServerUtil.consoleLog(e.getMessage());
//
//        }

        PluginManager.GetPlayerInfo(player.getUniqueId())
                .setIsProcessing(false, "Paste");

        this.cancel();
    }

}
