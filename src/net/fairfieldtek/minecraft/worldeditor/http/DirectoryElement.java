package net.fairfieldtek.minecraft.worldeditor.http;

public class DirectoryElement {
    private String Name;
    private int ElementType;

    public int getElementType() {
        return this.ElementType;
    }

    public void setElementType(int elementType) {
        this.ElementType = elementType;
    }

    public boolean IsDirectory() {
        return this.ElementType == 0;
    }

    public boolean IsSchematic() {
        return this.ElementType == 1;
    }

    public String getName() {
        return this.Name;
    }

    public void setName(String name) {
        this.Name = name;
    }
}

