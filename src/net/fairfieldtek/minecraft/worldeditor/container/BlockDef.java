package net.fairfieldtek.minecraft.worldeditor.container;

import net.fairfieldtek.minecraft.Util.BlockUtil;
import org.bukkit.block.BlockFace;

public class BlockDef {

    private String MaterialType;
    private byte MaterialData;
    private int X;
    private int Y;
    private int Z;
    private boolean Inverted;
    private boolean IsStairs;
    private String BlockFaceCode;

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
        return BlockUtil.ToBlockFaceFromCode(this.BlockFaceCode);
    }

    public void FromBlockFace(BlockFace face) {
        this.BlockFaceCode = BlockUtil.ToCodeFromBlockFace(face);
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
}
