package com.Blockelot.worldeditor.container;

import com.Blockelot.PluginManager;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.Blockelot.Util.EnumHelper;
import static com.Blockelot.Util.Inventory.itemStackArrayToBase64;
import com.Blockelot.Util.MaterialUtil;
import com.Blockelot.Util.ServerUtil;
import org.bukkit.block.Block;
import org.bukkit.block.data.*;
import org.bukkit.block.BlockState;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Container;
import org.bukkit.Material;
import org.bukkit.block.data.Directional;
import org.bukkit.*;
import org.bukkit.block.data.Bisected.Half;
import org.bukkit.block.data.type.Door;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author geev
 */
public final class BlockInfo {

    private int X;
    private int Y;
    private int Z;
    private BlockCollection BlockCollection;
    private int BlockTypeIndex = -1;
    private int BlockDataIndex = -1;
    private int BlockStorageIndex = -1;
    private int BlockContentsIndex = -1;

    public String getBlockDataString() {
        if (BlockDataIndex == -1) {
            ServerUtil.consoleLog("******************************************************>>>> Block Data Index NOT INITIALIZED");
        }
        return BlockCollection.getBlockDataPalette(BlockDataIndex);
    }

    public BlockData getBlockData() {
        if (BlockDataIndex == -1) {
            ServerUtil.consoleLog("******************************************************>>>> Block Data Index NOT INITIALIZED");
        }

        return Bukkit.getServer().createBlockData(BlockCollection.getBlockDataPalette(BlockDataIndex));
    }

    public final void setBlockData(String data) throws Exception {
        if (data.isEmpty()) {
            throw new Exception("DATA IS EMPTY!");
        }
        BlockDataIndex = BlockCollection.addBlockDataPalette(data);
    }

    public BlockCollection getBlockCollection() {
        return this.BlockCollection;
    }

    public void setBlockCollection(BlockCollection bc) {
        this.BlockCollection = bc;
    }

    public BlockInfo Clone(BlockCollection targetCollection) throws Exception {
        BlockInfo blockDef = new BlockInfo(targetCollection);
        blockDef.setBlockCollection(targetCollection);
        blockDef.X = this.X;
        blockDef.Y = this.Y;
        blockDef.Z = this.Z;
        blockDef.setBlockData(this.getBlockData().getAsString());
        blockDef.setBlockMaterial(this.getBlockMaterial());
        blockDef.setInventoryContentsString(this.getInventoryContentsString());
        blockDef.setInventoryStorageString(this.getInventoryStorageString());
        return blockDef;
    }

    private BlockInfo(BlockCollection collection) {
        BlockCollection = collection;
        BlockTypeIndex = -1;
        BlockDataIndex = -1;
        BlockStorageIndex = -1;
        BlockContentsIndex = -1;
    }

    public BlockInfo(Block block, BlockCollection collection) {
        BlockCollection = collection;
        X = block.getX();
        Y = block.getY();
        Z = block.getZ();

        setBlockMaterial(block.getType());
        try {
            setBlockData(block.getBlockData().getAsString());
        } catch (Exception ex) {
            Logger.getLogger(BlockInfo.class.getName()).log(Level.SEVERE, null, ex);
        }

        String contentsString = "";
        String storageString = "";

        BlockState state = block.getState();
        if (state instanceof Container) {
            ItemStack[] ContentsElements = ((Container) state).getInventory().getContents();
            ItemStack[] StorageElements = ((Container) state).getInventory().getStorageContents();
            contentsString = itemStackArrayToBase64(ContentsElements);
            storageString = itemStackArrayToBase64(StorageElements);
        }
        this.setInventoryContentsString(contentsString);
        this.setInventoryStorageString(storageString);

    }

    public void EraseLiquid(Block changeBlock, int diameter, BlockCollection undoBuffer) throws Exception {
        if (diameter == 0) {
            return;
        }

        Block s1 = changeBlock;

        if (s1.isLiquid()) {
            if (undoBuffer != null) {
                undoBuffer.AddBlock(s1, 0, 0, 0, null);
            }
            s1.setType(Material.AIR, true);
            EraseLiquid(s1, diameter - 1, undoBuffer);
        }

        s1 = changeBlock.getRelative(BlockFace.UP, 1);
        if (s1.isLiquid()) {
            if (undoBuffer != null) {
                undoBuffer.AddBlock(s1, 0, 0, 0, null);
            }
            s1.setType(Material.AIR, true);
            EraseLiquid(s1, diameter - 1, undoBuffer);
        }

        if ((s1 = changeBlock.getRelative(BlockFace.DOWN, 1)).isLiquid()) {
            if (undoBuffer != null) {
                undoBuffer.AddBlock(s1, 0, 0, 0, null);
            }
            s1.setType(Material.AIR, true);
            EraseLiquid(s1, diameter - 1, undoBuffer);
        }

        if ((s1 = changeBlock.getRelative(BlockFace.EAST, 1)).isLiquid()) {
            if (undoBuffer != null) {
                undoBuffer.AddBlock(s1, 0, 0, 0, null);
            }
            s1.setType(Material.AIR, true);
            EraseLiquid(s1, diameter - 1, undoBuffer);
        }

        if ((s1 = changeBlock.getRelative(BlockFace.WEST, 1)).isLiquid()) {
            if (undoBuffer != null) {
                undoBuffer.AddBlock(s1, 0, 0, 0, null);
            }
            s1.setType(Material.AIR, true);
            EraseLiquid(s1, diameter - 1, undoBuffer);
        }

        if ((s1 = changeBlock.getRelative(BlockFace.NORTH, 1)).isLiquid()) {
            if (undoBuffer != null) {
                undoBuffer.AddBlock(s1, 0, 0, 0, null);
            }
            s1.setType(Material.AIR, true);
            EraseLiquid(s1, diameter - 1, undoBuffer);
        }

        if ((s1 = changeBlock.getRelative(BlockFace.SOUTH, 1)).isLiquid()) {
            if (undoBuffer != null) {
                undoBuffer.AddBlock(s1, 0, 0, 0, null);
            }
            s1.setType(Material.AIR, true);
            EraseLiquid(s1, diameter - 1, undoBuffer);
        }

    }

    public boolean ApplyBlockInfoToBlock(Block target, boolean eraseWater, BlockCollection undoBuffer) throws Exception {
        return ApplyBlockInfoToBlock(target, eraseWater, undoBuffer, true);
    }

    public boolean ApplyBlockInfoToBlock(Block target, boolean eraseWater, BlockCollection undoBuffer, boolean applyPhysics) throws Exception {

        if (!target.getChunk().isLoaded()) {
            target.getChunk().load();
        }

        if (undoBuffer != null) {
            undoBuffer.AddBlock(target, 0, 0, 0, null);
        }
        if (eraseWater) {
            EraseLiquid(target, 1, undoBuffer);
        }

        //Inventory handler to prevent bug.
        try {
            BlockState state = target.getState();
            Container chest = (Container) state;
            chest.update(true);
            chest.getInventory().clear();
            target.setType(Material.AIR);
            return false;
        } catch (Exception e) {
        }

        if (this.getBlockData() instanceof Door) {
            final Block bottom = target;
            final Block top = bottom.getRelative(BlockFace.UP, 1);

            Door door = (Door) Bukkit.createBlockData(this.getBlockMaterial());
            door.setHalf(Half.BOTTOM);
            bottom.setBlockData(door, false);
            door.setHalf(Half.TOP);
            top.setBlockData(door, false);

        } else {
            target.setType(getBlockMaterial(), applyPhysics);
            target.setBlockData(getBlockData(), applyPhysics);

            target.getState().update();

            if (PluginManager.Config.IncludeInventoryWhenPasting) {
                if (target.getState() instanceof Container) {
                    try {
                        if (!"".equals(this.getInventoryContentsString())) {
                            ((Container) target.getState()).getInventory().setContents(com.Blockelot.Util.Inventory.itemStackArrayFromBase64(this.getInventoryContentsString()));
                        }
                        if (!"".equals(this.getInventoryStorageString())) {
                            ((Container) target.getState()).getInventory().setStorageContents(com.Blockelot.Util.Inventory.itemStackArrayFromBase64(this.getInventoryStorageString()));
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(BlockInfo.class.getName()).log(Level.WARNING, null, ex);
                    }
                }
            }
        }

        return true;
    }

    public Material getBlockMaterial() {
        return BlockCollection.GetBlockTypePaletteEntry(this.BlockTypeIndex);
    }

    public void setBlockMaterial(Material mat) {

        this.BlockTypeIndex = BlockCollection.AddBlockTypeToPalette(mat);
    }

    public void setInventoryContentsString(String inv) {
        this.BlockContentsIndex = BlockCollection.addBlockInventoryPalette(inv);
    }

    public String getInventoryContentsString() {
        return BlockCollection.getBlockInventoryPalette(this.BlockContentsIndex);
    }

    public void setInventoryStorageString(String inv) {
        this.BlockStorageIndex = BlockCollection.addBlockInventoryPalette(inv);
    }

    public String getInventoryStorageString() {
        return BlockCollection.getBlockInventoryPalette(this.BlockStorageIndex);
    }

    public String getBlockFaceCode() {

        BlockData newBlockData = this.getBlockData();

        if (newBlockData instanceof Directional) {
            return EnumHelper.ToCodeFromBlockFace(((Directional) newBlockData).getFacing());
        }
        return "";
    }

    public BlockFace GetBlockFace() {

        BlockData newBlockData = this.getBlockData();
        if (newBlockData instanceof Directional) {
            return ((Directional) newBlockData).getFacing();
        }

        return BlockFace.SELF;

    }

    public void SetBlockFace(BlockFace face) {

        BlockData newBlockData = this.getBlockData();
        if (newBlockData instanceof Directional) {

            ((Directional) newBlockData).setFacing(face);
            try {
                setBlockData(newBlockData.getAsString());
            } catch (Exception ex) {
                Logger.getLogger(BlockInfo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void SetBlockFaceCode(String code) {

        try {
            BlockData newBlockData = this.getBlockData();
            if (newBlockData instanceof Directional) {
                ((Directional) newBlockData).setFacing(EnumHelper.ToBlockFaceFromCode(code));
                setBlockData(newBlockData.getAsString());
            }
        } catch (Exception ex) {
            Logger.getLogger(BlockInfo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void ClearEntities(Chunk chunk) {
        try {

            //Lets clear any entities int he chunk....
            Entity[] ent = chunk.getEntities();
            for (Entity ent1 : ent) {
                ent1.remove();
            }
        } catch (Exception itm) {
            // empty catch block
        }
    }

    public int getX() {
        return this.X;
    }

    public void setX(int x) {
        this.X = x;
    }

    public int getY() {
        return this.Y;
    }

    public void setY(int y) {
        this.Y = y;
    }

    public int getZ() {
        return this.Z;
    }

    public void setZ(int z) {
        this.Z = z;
    }

    public void GetRotZ(int degrees) {
        BlockFace bf = GetBlockFace();
        String j = EnumHelper.ToCodeFromBlockFace(bf);
        int x = bf.getModX();
        int y = bf.getModY();
        int z = bf.getModZ();
        block0:
        switch (degrees) {
            case 90: {
                switch (j) {
                    case "00+": {
                        x = 0;
                        y = 0;
                        z = 1;
                        break block0;
                    }
                    case "00-": {
                        x = 0;
                        y = 0;
                        z = -1;
                        break block0;
                    }
                    case "0+0": {
                        x = -1;
                        y = 0;
                        z = 0;
                        break block0;
                    }
                    case "0-0": {
                        x = 1;
                        y = 0;
                        z = 0;
                        break block0;
                    }
                    case "+00": {
                        if (this.IsStairs()) {
                            x = -1;
                            y = 0;
                            z = 0;
                            break block0;
                        }
                        x = 0;
                        y = 1;
                        z = 0;
                        break block0;
                    }
                    case "-00": {
                        if (this.IsStairs()) {
                            this.Invert();
                        }
                        x = 0;
                        y = -1;
                        z = 0;
                    }
                }
                break;
            }
            case 180: {
                switch (j) {
                    case "00+": {
                        if (this.IsStairs()) {
                            this.Invert();
                        }
                        x = 0;
                        y = 0;
                        z = 1;
                        break block0;
                    }
                    case "00-": {
                        if (this.IsStairs()) {
                            this.Invert();
                        }
                        x = 0;
                        y = 0;
                        z = -1;
                        break block0;
                    }
                    case "0+0": {
                        x = 0;
                        y = -1;
                        z = 0;
                        break block0;
                    }
                    case "0-0": {
                        x = 0;
                        y = 1;
                        z = 0;
                        break block0;
                    }
                    case "+00": {
                        if (this.IsStairs()) {
                            this.Invert();
                        }
                        x = -1;
                        y = 0;
                        z = 0;
                        break block0;
                    }
                    case "-00": {
                        if (this.IsStairs()) {
                            this.Invert();
                        }
                        x = 1;
                        y = 0;
                        z = 0;
                    }
                }
                break;
            }
            case 270: {
                switch (j) {
                    case "00+": {
                        x = 1;
                        y = 0;
                        z = 0;
                        break block0;
                    }
                    case "00-": {
                        x = 0;
                        y = 0;
                        z = -1;
                        break block0;
                    }
                    case "0+0": {
                        x = 1;
                        y = 0;
                        z = 0;
                        break block0;
                    }
                    case "0-0": {
                        x = -1;
                        y = 0;
                        z = 0;
                        break block0;
                    }
                    case "+00": {
                        if (this.IsStairs()) {
                            this.Invert();
                        }
                        x = 0;
                        y = -1;
                        z = 0;
                        break block0;
                    }
                    case "-00": {
                        x = 0;
                        y = 1;
                        z = 0;
                    }
                }
            }
        }

        this.SetBlockFace(MaterialUtil.getFacingByMod(x, y, z));
    }

    public void GetRotY(int degrees) {
        BlockFace bf = GetBlockFace();//ToBlockFace();
        String j = EnumHelper.ToCodeFromBlockFace(bf);
        int x = bf.getModX();
        int y = bf.getModY();
        int z = bf.getModZ();
        block0:
        switch (degrees) {
            case 90: {
                switch (j) {
                    case "00+": {
                        x = 1;
                        y = 0;
                        z = 0;
                        break block0;
                    }
                    case "00-": {
                        x = -1;
                        y = 0;
                        z = 0;
                        break block0;
                    }
                    case "0+0": {
                        x = 0;
                        y = 1;
                        z = 0;
                        break block0;
                    }
                    case "0-0": {
                        x = 0;
                        y = -1;
                        z = 0;
                        break block0;
                    }
                    case "+00": {
                        x = 0;
                        y = 0;
                        z = -1;
                        break block0;
                    }
                    case "-00": {
                        x = 0;
                        y = 0;
                        z = 1;
                    }
                }
                break;
            }
            case 180: {
                switch (j) {
                    case "00+": {
                        x = 0;
                        y = 0;
                        z = -1;
                        break block0;
                    }
                    case "00-": {
                        x = 0;
                        y = 0;
                        z = 1;
                        break block0;
                    }
                    case "0+0": {
                        x = 0;
                        y = 1;
                        z = 0;
                        break block0;
                    }
                    case "0-0": {
                        x = 0;
                        y = -1;
                        z = 0;
                        break block0;
                    }
                    case "+00": {
                        x = -1;
                        y = 0;
                        z = 0;
                        break block0;
                    }
                    case "-00": {
                        x = 1;
                        y = 0;
                        z = 0;
                    }
                }
                break;
            }
            case 270: {
                switch (j) {
                    case "00+": {
                        x = -1;
                        y = 0;
                        z = 0;
                        break block0;
                    }
                    case "00-": {
                        x = 1;
                        y = 0;
                        z = 0;
                        break block0;
                    }
                    case "0+0": {
                        x = 0;
                        y = 1;
                        z = 0;
                        break block0;
                    }
                    case "0-0": {
                        x = 0;
                        y = -1;
                        z = 0;
                        break block0;
                    }
                    case "+00": {
                        x = 0;
                        y = 0;
                        z = 1;
                        break block0;
                    }
                    case "-00": {
                        x = 0;
                        y = 0;
                        z = -1;
                    }
                }
            }
        }
        this.SetBlockFace(MaterialUtil.getFacingByMod(x, y, z));

    }

    public void GetRotX(int degrees) {
        BlockFace bf = GetBlockFace();
        String j = EnumHelper.ToCodeFromBlockFace(bf);
        int x = bf.getModX();
        int y = bf.getModY();
        int z = bf.getModZ();
        block0:
        switch (degrees) {
            case 90: {
                switch (j) {
                    case "-00": {
                        x = -1;
                        y = 0;
                        z = 0;
                        break block0;
                    }
                    case "0-0": {
                        x = 0;
                        y = 0;
                        z = -1;
                        break block0;
                    }
                    case "00-": {
                        if (this.IsStairs()) {
                            x = 0;
                            y = 0;
                            z = 1;
                            break block0;
                        }
                        x = 0;
                        y = 1;
                        z = 0;
                        break block0;
                    }
                    case "00+": {
                        if (this.IsStairs()) {
                            x = 0;
                            y = 0;
                            z = 1;
                            this.Invert();
                            break block0;
                        }
                        x = 0;
                        y = -1;
                        z = 0;
                        break block0;
                    }
                    case "0+0": {
                        x = 0;
                        y = 0;
                        z = 1;
                        break block0;
                    }
                    case "+00": {
                        x = 1;
                        y = 0;
                        z = 0;
                        break block0;
                    }
                }
                break;
            }
            case 180: {
                switch (j) {
                    case "-00": {
                        if (this.IsStairs()) {
                            this.Invert();
                        }
                        x = -1;
                        y = 0;
                        z = 0;
                        break block0;
                    }
                    case "0-0": {
                        x = 0;
                        y = 1;
                        z = 0;
                        break block0;
                    }
                    case "00-": {
                        if (this.IsStairs()) {
                            this.Invert();
                        }
                        x = 0;
                        y = 0;
                        z = 1;
                        break block0;
                    }
                    case "00+": {
                        if (IsStairs()) {
                            x = 0;
                            y = 0;
                            z = -1;
                            this.Invert();
                            break block0;
                        }
                        x = 0;
                        y = 0;
                        z = -1;
                        break block0;
                    }
                    case "0+0": {
                        x = 0;
                        y = -1;
                        z = 0;
                        break block0;
                    }
                    case "+00": {
                        if (IsStairs()) {
                            this.Invert();
                        }
                        x = 1;
                        y = 0;
                        z = 0;
                    }
                }
                break;
            }
            case 270: {
                switch (j) {
                    case "-00": {
                        x = -1;
                        y = 0;
                        z = 0;
                        break block0;
                    }
                    case "0-0": {
                        x = 0;
                        y = 0;
                        z = 1;
                        break block0;
                    }
                    case "00-": {
                        if (IsStairs()) {
                            x = 0;
                            y = 0;
                            z = -1;
                            this.Invert();
                            break block0;
                        }
                        x = 0;
                        y = -1;
                        z = 0;
                        break block0;
                    }
                    case "00+": {
                        if (IsStairs()) {
                            x = 0;
                            y = 0;
                            z = -1;
                            break block0;
                        }
                        x = 0;
                        y = 1;
                        z = 0;
                        break block0;
                    }
                    case "0+0": {
                        x = 0;
                        y = 0;
                        z = -1;
                        break block0;
                    }
                    case "+00": {
                        x = 1;
                        y = 0;
                        z = 0;
                    }
                }
            }
        }
        try {
            this.SetBlockFace(MaterialUtil.getFacingByMod(x, y, z));
        } catch (Exception e) {
            //
        }

    }

    public int getBlockTypeIndex() {
        return this.BlockTypeIndex;
    }

    public String toXferString() {
        return Integer.toString(X) + "|"
                + Integer.toString(Y) + "|"
                + Integer.toString(Z) + "|"
                + Integer.toString(BlockTypeIndex) + "|"
                + Integer.toString(BlockDataIndex) + "|"
                + Integer.toString(BlockContentsIndex) + "|"
                + Integer.toString(BlockStorageIndex) + "|";
    }

    public boolean IsDoor() {
        if (this.getBlockData().getMaterial().name().toLowerCase().endsWith("_door")) {
            return true;
        }

        return false;
    }

    public boolean IsStairs() {
        if (this.getBlockData().getMaterial().name().toLowerCase().endsWith("_STAIRS".toLowerCase())) {
            return true;
        }
        return false;
    }

    public void Invert() {
        BlockData data = this.getBlockData();

        if (data instanceof Bisected) {
            Bisected.Half h = ((Bisected) data).getHalf();
            if (h == Half.BOTTOM) {
                ((Bisected) data).setHalf(Half.TOP);
            } else {
                ((Bisected) data).setHalf(Half.BOTTOM);
            }
        }

        try {
            this.setBlockData(data.getAsString());
        } catch (Exception ex) {
            Logger.getLogger(BlockInfo.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
