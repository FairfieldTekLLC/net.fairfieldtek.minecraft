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
import org.bukkit.inventory.meta.*;

public class BlockDef {

    private static void erase(Block changeBlock) {
        Block s1 = changeBlock.getRelative(BlockFace.UP, 1);
        if (s1.isLiquid()) {
            s1.setType(Material.AIR, true);
        }
        if ((s1 = changeBlock.getRelative(BlockFace.DOWN, 1)).isLiquid()) {
            s1.setType(Material.AIR, true);
        }
        if ((s1 = changeBlock.getRelative(BlockFace.EAST, 1)).isLiquid()) {
            s1.setType(Material.AIR, true);
        }
        if ((s1 = changeBlock.getRelative(BlockFace.WEST, 1)).isLiquid()) {
            s1.setType(Material.AIR, true);
        }
        if ((s1 = changeBlock.getRelative(BlockFace.NORTH, 1)).isLiquid()) {
            s1.setType(Material.AIR, true);
        }
        if ((s1 = changeBlock.getRelative(BlockFace.SOUTH, 1)).isLiquid()) {
            s1.setType(Material.AIR, true);
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
            erase(changeBlock);
        }

        if (!BedCreate(changeBlock)) {
            if (!StairsCreate(changeBlock)) {
                GeneralCreate(changeBlock);
            }
        }

        SetColor(changeBlock);

    }

    public int getBlockTypeIndex() {
        return this.BlockTypeIndex;
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

//    public void setIsStairs(boolean isStairs) {
//        this.IsStairs = isStairs;
//    }
    public byte getMaterialData() {
        return this.MaterialData;
    }

    public void setMaterialData(byte materialData) {
        this.MaterialData = materialData;
    }

    @Override
    public String toString() {
        return Integer.toString(this.X) + " " + Integer.toString(this.Y) + " " + Integer.toString(this.Z) + " "
                + ((SchematicOwner.getBlockTypePalette()).get(this.getBlockTypeIndex()))
                //this.MaterialType 
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

    public boolean StairsCreate(Block changeBlock) {
        //MaterialData tMat = changeBlock.getState().getData();

        if (!getIsStairs()) {
            return false;
        }

        changeBlock.setType(Material.getMaterial(SchematicOwner.getBlockTypePalette().get(this.BlockTypeIndex)));
        //Material.getMaterial((String) getMaterialType()), true);
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
        changeBlock.setType(Material.getMaterial(SchematicOwner.getBlockTypePalette().get(this.BlockTypeIndex)));
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
