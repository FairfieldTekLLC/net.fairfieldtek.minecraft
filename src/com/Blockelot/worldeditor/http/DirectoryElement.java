package com.Blockelot.worldeditor.http;

public class DirectoryElement {

    private String Name;
    private int ElementType;
    private long BlockCount;

    public long getBlockCount() {
        return this.BlockCount;
    }

    public void setBlockCount(long count) {
        this.BlockCount = count;
    }

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
