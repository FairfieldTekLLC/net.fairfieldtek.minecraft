package com.Blockelot.worldeditor.container;

public class IPoint {

    public int X;
    public int Y;
    public int Z;

    public IPoint(int x, int y, int z) {
        this.X = x;
        this.Y = y;
        this.Z = z;
    }

    public IPoint(String points) {
        String[] coords = points.split(" ");
        this.X = Integer.parseInt(coords[0]);
        this.Y = Integer.parseInt(coords[1]);
        this.Z = Integer.parseInt(coords[2]);
    }

    @Override
    public String toString() {
        return Integer.toString(this.X) + " " + Integer.toString(this.Y) + " " + Integer.toString(this.Z);
    }
}
