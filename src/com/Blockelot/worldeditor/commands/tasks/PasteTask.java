package com.Blockelot.worldeditor.commands.tasks;

import java.util.ListIterator;
import java.util.UUID;
import com.Blockelot.PluginManager;
import com.Blockelot.Util.PlayerUtils;
import com.Blockelot.worldeditor.container.BlockCollection;
import com.Blockelot.worldeditor.container.BlockInfo;
import com.Blockelot.worldeditor.container.PlayerInfo;
import com.Blockelot.worldeditor.enumeration.Axis;
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

    public PasteTask(Player player, int x, int y, int z, Axis axis, double degrees) {
        this.PlayerId = player.getUniqueId();
        this.X = x;
        this.Y = y;
        this.Z = z;
        this.Axis = axis;
        this.Degrees = degrees;
        PlayerInfo pi = PluginManager.PlayerInfoList.get(player);
        this.SchematicClipboard = pi.ClipSchematic.Clone();
        this.SchematicUndo = pi.NewUndo();
        this.SchematicUndo.Clear();
        PlayerBlockFace = PlayerUtils.getCardinalDirection(player);
    }

    @Override
    public void run() {

        Player player = PluginManager.Plugin.getServer().getPlayer(this.PlayerId);

        try {
            PlayerInfo pi = PluginManager.PlayerInfoList.get(player);
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
                if (++counter > 8000) {
                    try {
                        player.sendMessage("Buffering... " + this.SchematicClipboard.getBlocks().size() + " left.");
                    } catch (Exception e) {
                        this.cancel();
                    }
                    return;
                }
                BlockInfo itm = iter.next();
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

                this.RotatedSchematicClipboard.AddBlock(itm,null);
                iter.remove();

            }

            if (this.SchematicClipboard.Size() == 0 && !FinishedRotation) {
                FinishedRotation = true;
                iter = this.RotatedSchematicClipboard.getBlocks().listIterator();
                while (iter.hasNext()) {
                    BlockInfo itm = iter.next();
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

                finalOffsetX = this.X - minX;
                finalOffsetY = this.Y - minY;
                finalOffsetZ = this.Z - minZ;

            }

            counter = 0;

            iter = this.RotatedSchematicClipboard.getBlocks().listIterator();

            while (iter.hasNext()) {
                if (++counter > 8000) {
                    try {
                        player.sendMessage("Buffering... " + this.RotatedSchematicClipboard.getBlocks().size() + " left.");
                    } catch (Exception e) {
                        this.cancel();
                    }
                    return;
                }
                BlockInfo itm = iter.next();

                if ((itm.getBlockMaterial().name().endsWith("_DOOR"))
                        || (itm.getBlockMaterial().name().endsWith("_STAIRS"))
                        || (itm.getBlockMaterial().name().endsWith("_RAIL"))
                        || (itm.getBlockMaterial().name().endsWith("TORCH"))) {
                    continue;
                }

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
                    System.out.println("Cannot determine facing.");
                }

                int x = itm.getX() + fox;
                int y = itm.getY() + foy + 1;
                int z = itm.getZ() + foz;

                itm.ApplyBlockInfoToBlock( player.getWorld().getBlockAt(x, y, z), false, this.SchematicUndo);

                iter.remove();
            }

            counter = 0;

            //Now we place door's stairs and such.
            iter = this.RotatedSchematicClipboard.getBlocks().listIterator();

            while (iter.hasNext()) {
                if (++counter > 8000) {
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
                    System.out.println("Cannot determine facing.");
                }

                int x = itm.getX() + fox;
                int y = itm.getY() + foy + 1;
                int z = itm.getZ() + foz;

                itm.ApplyBlockInfoToBlock(player.getWorld().getBlockAt(x, y, z), false, SchematicUndo);

                iter.remove();
            }

            player.sendMessage("Blocks Pasted (" + this.SchematicUndo.Size() + ")");
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
            System.out.println(e.getMessage());

        }

        PluginManager.PlayerInfoList.get(player).setIsProcessing(false, "Paste");
        this.cancel();
    }

}
