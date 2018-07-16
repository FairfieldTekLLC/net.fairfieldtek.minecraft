package net.fairfieldtek.minecraft.worldeditor.container;

import net.fairfieldtek.minecraft.Util.*;
import org.bukkit.Chunk;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.material.*;

public class BlockDef {

    public static void EraseLiquid(Block changeBlock, int diameter, Player player, SchematicDef undoBuffer) {
        if (diameter == 0) {
            return;
        }

        Block s1 = changeBlock;

        if (s1.isLiquid()) {
            if (undoBuffer != null) {
                undoBuffer.AddBlock(s1, 0, 0, 0, player);
            }
            s1.setType(Material.AIR, true);
            EraseLiquid(s1, diameter - 1, player, undoBuffer);
        }

        s1 = changeBlock.getRelative(BlockFace.UP, 1);
        if (s1.isLiquid()) {
            if (undoBuffer != null) {
                undoBuffer.AddBlock(s1, 0, 0, 0, player);
            }
            s1.setType(Material.AIR, true);
            EraseLiquid(s1, diameter - 1, player, undoBuffer);
        }

        if ((s1 = changeBlock.getRelative(BlockFace.DOWN, 1)).isLiquid()) {
            if (undoBuffer != null) {
                undoBuffer.AddBlock(s1, 0, 0, 0, player);
            }
            s1.setType(Material.AIR, true);
            EraseLiquid(s1, diameter - 1, player, undoBuffer);
        }

        if ((s1 = changeBlock.getRelative(BlockFace.EAST, 1)).isLiquid()) {
            if (undoBuffer != null) {
                undoBuffer.AddBlock(s1, 0, 0, 0, player);
            }
            s1.setType(Material.AIR, true);
            EraseLiquid(s1, diameter - 1, player, undoBuffer);
        }

        if ((s1 = changeBlock.getRelative(BlockFace.WEST, 1)).isLiquid()) {
            if (undoBuffer != null) {
                undoBuffer.AddBlock(s1, 0, 0, 0, player);
            }
            s1.setType(Material.AIR, true);
            EraseLiquid(s1, diameter - 1, player, undoBuffer);
        }

        if ((s1 = changeBlock.getRelative(BlockFace.NORTH, 1)).isLiquid()) {
            if (undoBuffer != null) {
                undoBuffer.AddBlock(s1, 0, 0, 0, player);
            }
            s1.setType(Material.AIR, true);
            EraseLiquid(s1, diameter - 1, player, undoBuffer);
        }

        if ((s1 = changeBlock.getRelative(BlockFace.SOUTH, 1)).isLiquid()) {
            if (undoBuffer != null) {
                undoBuffer.AddBlock(s1, 0, 0, 0, player);
            }
            s1.setType(Material.AIR, true);
            EraseLiquid(s1, diameter - 1, player, undoBuffer);
        }

    }

    private byte MaterialData;
    private int X;
    private int Y;
    private int Z;
    private boolean Inverted;
    //private boolean IsStairs;
    private String BlockFaceCode;
    private int BlockTypeIndex;
    private int BlockColorIndex;

    public SchematicDef SchematicOwner;

    public BlockDef Clone(SchematicDef owner) {
        BlockDef blockDef = new BlockDef();
        blockDef.BlockColorIndex = this.BlockColorIndex;
        blockDef.BlockFaceCode = this.BlockFaceCode;
        blockDef.BlockTypeIndex = this.BlockTypeIndex;
        blockDef.Inverted = this.Inverted;
        blockDef.MaterialData = this.MaterialData;
        blockDef.SchematicOwner = owner;
        blockDef.X = this.X;
        blockDef.Y = this.Y;
        blockDef.Z = this.Z;
        return blockDef;
    }

    public String toXferString() {
        return Byte.toString(MaterialData) + "|"
                + Integer.toString(X) + "|"
                + Integer.toString(Y) + "|"
                + Integer.toString(Z) + "|"
                + (Inverted ? "1" : "0") + "|"
                + BlockFaceCode + "|"
                + Integer.toString(BlockTypeIndex) + "|"
                + Integer.toString(BlockColorIndex);
    }

    public void SetBlock(Block changeBlock, Player player, boolean erase) {
        Chunk chunk;
        World world = player.getWorld();

        if (!world.isChunkLoaded(chunk = world.getChunkAt(changeBlock))) {
            world.loadChunk(chunk);
        }
        if (erase) {
            EraseLiquid(changeBlock, 1, player, null);
        }

        if (!BedCreate(changeBlock)) {
            if (!DoorCreate(changeBlock)) {
                if (!StairsCreate(changeBlock)) {
                    GeneralCreate(changeBlock);
                }
            }
        }

        SetColor(changeBlock);

    }

    public int getBlockTypeIndex() {
        return this.BlockTypeIndex;
    }

    public Material getBlockMaterial() {
        return SchematicOwner.GetBlockTypePaletteEntry(this.BlockTypeIndex);
    }

    public void setBlockTypeIndex(int idx) {
        this.BlockTypeIndex = idx;
    }

    public int getBlockColorIndex() {
        return this.BlockColorIndex;
    }

    public void setBlockColorIndex(int idx) {
        this.BlockColorIndex = idx;
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

    public String getBlockFaceCode() {
        return this.BlockFaceCode;
    }

    public void setBlockFaceCode(String code) {
        this.BlockFaceCode = code;
    }

    public BlockFace ToBlockFace() {
        return EnumHelper.ToBlockFaceFromCode(this.BlockFaceCode);
    }

    public void FromBlockFace(BlockFace face) {
        this.BlockFaceCode = EnumHelper.ToCodeFromBlockFace(face);
    }

    public void setInverted(boolean inverted) {
        this.Inverted = inverted;
    }

    public boolean getInverted() {
        return this.Inverted;
    }

    public boolean getIsStairs() {
        //System.out.println("Stair Check! " + SchematicOwner.GetBlockTypePaletteEntry(this.BlockTypeIndex).name());
        if (SchematicOwner.GetBlockTypePaletteEntry(this.BlockTypeIndex).name().endsWith("_STAIRS")) {
            //System.out.println("TRUE!!!!");
            return true;
        }
        //System.out.println("FALSE!!!!");
        return false;

    }

    public byte getMaterialData() {
        return this.MaterialData;
    }

    public void setMaterialData(byte materialData) {
        this.MaterialData = materialData;
    }

    @Override
    public String toString() {
        return Integer.toString(this.X) + " " + Integer.toString(this.Y) + " " + Integer.toString(this.Z) + " "
                + (SchematicOwner.GetBlockTypePaletteEntry(getBlockTypeIndex()))
                + " " + this.BlockFaceCode;
    }

    public void GetRotZ(int degrees) {
        BlockFace bf = ToBlockFace();
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
                        if (getIsStairs()) {
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
                        if (getIsStairs()) {
                            setInverted(!getInverted());
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
                        if (getIsStairs()) {
                            setInverted(!getInverted());
                        }
                        x = 0;
                        y = 0;
                        z = 1;
                        break block0;
                    }
                    case "00-": {
                        if (getIsStairs()) {
                            setInverted(!getInverted());
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
                        if (getIsStairs()) {
                            setInverted(!getInverted());
                        }
                        x = -1;
                        y = 0;
                        z = 0;
                        break block0;
                    }
                    case "-00": {
                        if (getIsStairs()) {
                            setInverted(!getInverted());
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
                        if (getIsStairs()) {
                            setInverted(!getInverted());
                        }
                        x = 0;
                        y = -1;
                        z = 0;
                        break block0;
                    }
                    case "-00": {
                        if (getIsStairs()) {
                            x = 1;
                            y = 0;
                            z = 0;
                            break block0;
                        }
                        x = 0;
                        y = 1;
                        z = 0;
                    }
                }
            }
        }
        FromBlockFace(MaterialUtil.getFacingByMod(x, y, z));
        //return itm;
    }

    public void GetRotY(int degrees) {
        BlockFace bf = ToBlockFace();
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
        FromBlockFace(MaterialUtil.getFacingByMod(x, y, z));
        //return itm;
    }

    public void GetRotX(int degrees) {
        BlockFace bf = ToBlockFace();
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
                        if (getIsStairs()) {
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
                        if (getIsStairs()) {
                            x = 0;
                            y = 0;
                            z = 1;
                            setInverted(!getInverted());
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
                    }
                }
                break;
            }
            case 180: {
                switch (j) {
                    case "-00": {
                        if (getIsStairs()) {
                            setInverted(!getInverted());
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
                        if (getIsStairs()) {
                            setInverted(!getInverted());
                        }
                        x = 0;
                        y = 0;
                        z = 1;
                        break block0;
                    }
                    case "00+": {
                        if (getIsStairs()) {
                            x = 0;
                            y = 0;
                            z = -1;
                            setInverted(!getInverted());
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
                        if (getIsStairs()) {
                            setInverted(!getInverted());
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
                        if (getIsStairs()) {
                            x = 0;
                            y = 0;
                            z = -1;
                            setInverted(!getInverted());
                            break block0;
                        }
                        x = 0;
                        y = -1;
                        z = 0;
                        break block0;
                    }
                    case "00+": {
                        if (getIsStairs()) {
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
        FromBlockFace(MaterialUtil.getFacingByMod(x, y, z));
        //return itm;
    }

    public boolean DoorsGetDirectionalCond(Block sourceBlock) {
        MaterialData sMat = sourceBlock.getState().getData();
        if (!(sMat instanceof Door)) {
            return false;
        }
        Door door = (Door) sMat;
        if (door.isTopHalf()) {
            this.BlockTypeIndex = SchematicOwner.AddBlockTypeToPalette(Material.AIR);
        } else {
            FromBlockFace(door.getFacing());
        }

        return true;

    }

    public boolean StairsGetDirectionalCond(Block sourceBlock) {
        MaterialData sMat = sourceBlock.getState().getData();
        if (!(sMat instanceof Stairs)) {
            return false;
        }
        Stairs stairs = (Stairs) sMat;
        FromBlockFace(stairs.getDescendingDirection());
        setInverted(stairs.isInverted());
        return true;
    }

    public boolean LadderGetDirectionalCond(Block sourceBlock) {
        MaterialData sMat = sourceBlock.getState().getData();
        if (!(sMat instanceof Ladder)) {
            return false;
        }
        Ladder ladder = (Ladder) sMat;
        FromBlockFace(ladder.getAttachedFace());
        return true;
    }

    public boolean BedGetDirectionalCond(Block sourceBlock) {
        MaterialData sMat = sourceBlock.getState().getData();
        if (!(sMat instanceof Bed)) {
            return false;
        }

        Bed bed = (Bed) sMat;

        if (!bed.isHeadOfBed()) {
            this.BlockTypeIndex = SchematicOwner.AddBlockTypeToPalette(Material.AIR);
        } else {
            FromBlockFace(bed.getFacing());
        }

        return true;
    }

    public DyeColor GetColor(Block sourceBlock) {
        MaterialData sMat = sourceBlock.getState().getData();
        DyeColor dyeColor = DyeColor.WHITE;

        if (sMat instanceof Wool) {
            dyeColor = ((Wool) sMat).getColor();
        } else if (sMat instanceof Bed) {
            dyeColor = ((org.bukkit.block.Bed) sourceBlock.getState()).getColor();
        } else if (sMat instanceof Banner) {

            dyeColor = ((org.bukkit.block.Banner) sourceBlock.getState()).getBaseColor();
        }

        this.BlockColorIndex = SchematicOwner.AddBlockColorToPalette(dyeColor);
        return dyeColor;
    }

    public void SetColor(Block sourceBlock) {

        DyeColor dyeColor = this.SchematicOwner.GetColorPaletteEntry(this.BlockColorIndex);

        BlockState sourceBlockState = sourceBlock.getState();

        MaterialData sMat = sourceBlockState.getData();

        if (sMat instanceof Wool) {
            ((Wool) sMat).setColor(dyeColor);
        } else if (sMat instanceof Bed) {
            ((org.bukkit.block.Bed) sourceBlockState).setColor(dyeColor);
        } else if (sMat instanceof Banner) {
            ((org.bukkit.block.Banner) sourceBlockState).setBaseColor(dyeColor);
        }

        sourceBlockState.update(true);
    }

    public boolean GetDirectionalCond(Block sourceBlock) {
        MaterialData sMat = sourceBlock.getState().getData();
        if (!(sMat instanceof Directional)) {
            return false;
        }

        Directional d = (Directional) sMat;
        FromBlockFace(d.getFacing());

        return true;
    }

    public boolean BedCreate(Block changeBlock) {

        if (this.BlockTypeIndex != SchematicOwner.AddBlockTypeToPalette(Material.BED_BLOCK)) {
            return false;
        }
        Block bedHeadBlock = changeBlock;

        BlockFace bedFacing = EnumHelper.ToBlockFaceFromCode(getBlockFaceCode());

        Block bedFootBlock = bedHeadBlock.getRelative(bedFacing.getOppositeFace());

        BlockState bedFootState = bedFootBlock.getState();
        bedFootState.setType(Material.BED_BLOCK);
        Bed bedFootData = new Bed(Material.BED_BLOCK);
        bedFootData.setHeadOfBed(false);
        bedFootData.setFacingDirection(bedFacing);
        bedFootState.setData(bedFootData);
        bedFootState.update(true);
        SetColor(bedFootBlock);

        BlockState bedHeadState = bedHeadBlock.getState();
        bedHeadState.setType(Material.BED_BLOCK);
        Bed bedHeadData = new Bed(Material.BED_BLOCK);
        bedHeadData.setHeadOfBed(true);
        bedHeadData.setFacingDirection(bedFacing);
        bedHeadState.setData(bedHeadData);
        bedHeadState.update(true);
        SetColor(bedHeadBlock);

        return true;
    }

    public boolean DoorCreate(Block changeBlock) {
        if (!this.getBlockMaterial().name().endsWith("_DOOR")) {
            return false;
        }
        Block DoorBotBlock = changeBlock;
        Block DoorTopBlock = changeBlock.getRelative(BlockFace.UP);
        BlockState botState = DoorBotBlock.getState();
        BlockState topState = DoorTopBlock.getState();

        botState.setType(SchematicOwner.GetBlockTypePaletteEntry(BlockTypeIndex));
        topState.setType(SchematicOwner.GetBlockTypePaletteEntry(BlockTypeIndex));
        ((org.bukkit.material.Door) botState.getData()).setTopHalf(false);
        ((org.bukkit.material.Door) botState.getData()).setFacingDirection(ToBlockFace());
        ((org.bukkit.material.Door) topState.getData()).setTopHalf(false);
        ((org.bukkit.material.Door) topState.getData()).setFacingDirection(ToBlockFace());
        botState.setRawData(getMaterialData());
        topState.setRawData(getMaterialData());
        botState.update(true);
        topState.update(true);
        return true;
    }

    public boolean StairsCreate(Block changeBlock) {
        //MaterialData tMat = changeBlock.getState().getData();

        if (!getIsStairs()) {
            return false;
        }

        changeBlock.setType(SchematicOwner.GetBlockTypePaletteEntry(this.BlockTypeIndex));
        BlockState state = changeBlock.getState();
        state.setRawData(getMaterialData());
        state.update(true);
        state = changeBlock.getState();
        MaterialData tMat = state.getData();
        if (getBlockFaceCode().equals("")) {
            return true;
        }
        Stairs stairs = (Stairs) tMat;
        switch (ToBlockFace()) {
            case EAST: {
                stairs.setFacingDirection(BlockFace.WEST);
                break;
            }
            case WEST: {
                stairs.setFacingDirection(BlockFace.EAST);
                break;
            }
            case NORTH: {
                stairs.setFacingDirection(BlockFace.SOUTH);
                break;
            }
            case SOUTH: {
                stairs.setFacingDirection(BlockFace.NORTH);
            }
        }
        state.setData((MaterialData) stairs);
        state.update(true);
        state = changeBlock.getState();
        tMat = state.getData();
        stairs = (Stairs) tMat;
        stairs.setInverted(getInverted());
        state.setData((MaterialData) stairs);
        state.update(true);

        return true;

    }

    public boolean GeneralCreate(Block changeBlock) {
        //changeBlock.setType(Material.getMaterial((String) getMaterialType()), true);
        changeBlock.setType(SchematicOwner.GetBlockTypePaletteEntry(BlockTypeIndex));
        BlockState state = changeBlock.getState();
        state.setRawData(getMaterialData());
        state.update(true);
        state = changeBlock.getState();
        MaterialData tMat = state.getData();
        if (tMat instanceof Directional) {
            Directional td = (Directional) tMat;
            td.setFacingDirection(ToBlockFace());
            state.setData((MaterialData) td);
            state.update(true);
        }
        return true;
    }
}
