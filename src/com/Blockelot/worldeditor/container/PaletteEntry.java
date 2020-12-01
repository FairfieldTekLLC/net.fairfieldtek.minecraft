package com.Blockelot.worldeditor.container;

/**
 *
 * @author geev
 */
public class PaletteEntry {

    private int Id = -1;
    private String Value = "";

    public PaletteEntry() {
    }

    public PaletteEntry Clone() {
        PaletteEntry e = new PaletteEntry();
        e.Id = this.Id;
        e.Value = this.Value;
        return e;
    }

    public PaletteEntry(int i, String v) {
        this.Id = i;
        this.Value = v.trim();
    }

    public int getId() {
        return this.Id;
    }

    public void setId(int i) {
        this.Id = i;
    }

    public String getValue() {
        return this.Value;
    }

    public void setValue(String v) {
        this.Value = v.trim();
    }

}
