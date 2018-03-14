package net.fairfieldtek.minecraft.worldeditor.container;

import net.fairfieldtek.minecraft.Util.BlockUtil;
import net.fairfieldtek.minecraft.Util.MaterialUtil;
import org.bukkit.block.BlockFace;
import net.fairfieldtek.minecraft.Util.*;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.material.Bed;
import org.bukkit.material.Directional;
import org.bukkit.material.Ladder;
import org.bukkit.material.MaterialData;
import org.bukkit.material.Stairs;
import org.bukkit.DyeColor;
import org.bukkit.material.*;

public class BlockDef {

    private String MaterialType;
    private byte MaterialData;
    private int X;
    private int Y;
    private int Z;
    private boolean Inverted;
    private boolean IsStairs;
    private String BlockFaceCode;
    private String DyeColor;

    public String getDyeColor() {
        return DyeColor;
    }

    public void setDyeColor(String color) {
        this.DyeColor = color;
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
        return this.IsStairs;
    }

    public void setIsStairs(boolean isStairs) {
        this.IsStairs = isStairs;
    }

    public String getMaterialType() {
        return this.MaterialType;
    }

    public void setMaterialType(String materialType) {
        this.MaterialType = materialType;
    }

    public byte getMaterialData() {
        return this.MaterialData;
    }

    public void setMaterialData(byte materialData) {
        this.MaterialData = materialData;
    }

    @Override
    public String toString() {
        return Integer.toString(this.X) + " " + Integer.toString(this.Y) + " " + Integer.toString(this.Z) + " " + this.MaterialType + " " + this.BlockFaceCode;
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
        setIsStairs(true);
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
            setMaterialType(Material.AIR.name());
        } else {
            FromBlockFace(bed.getFacing());
        }

        try {
            MaterialData d = sourceBlock.getState().getData();
            DyeColor dye =  ((Colorable)((Bed)d)).getColor();

            //  DyeColor d = ((Colorable)((Bed)sourceBlock.getState())).getColor();
            System.out.println("---------------> Color " + dye.name());
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
            System.out.println(e.getMessage());
        }

        return true;
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
        if (!(Material.getMaterial((String) getMaterialType()) == Material.BED_BLOCK)) {
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

        BlockState bedHeadState = bedHeadBlock.getState();
        bedHeadState.setType(Material.BED_BLOCK);
        Bed bedHeadData = new Bed(Material.BED_BLOCK);
        bedHeadData.setHeadOfBed(true);
        bedHeadData.setFacingDirection(bedFacing);
        bedHeadState.setData(bedHeadData);

        //((Colorable) bedHeadBlock).setColor(GetDyeColor(def.getDyeColor()));
        bedHeadState.update(true);
        return true;
    }

    public boolean StairsCreate(Block changeBlock) {
        MaterialData tMat = changeBlock.getState().getData();

        if (!(tMat instanceof Stairs)) {
            return false;
        }

        changeBlock.setType(Material.getMaterial((String) getMaterialType()), true);
        BlockState state = changeBlock.getState();
        state.setRawData(getMaterialData());
        state.update(true);
        state = changeBlock.getState();
        tMat = state.getData();
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
        changeBlock.setType(Material.getMaterial((String) getMaterialType()), true);
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
