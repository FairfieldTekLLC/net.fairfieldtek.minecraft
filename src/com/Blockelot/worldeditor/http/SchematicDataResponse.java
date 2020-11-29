package com.Blockelot.worldeditor.http;

public class SchematicDataResponse
        extends BaseResponse {

    private int SchematicId;
    private boolean Final;

    public boolean getFinal() {
        return this.Final;
    }

    public void setFinal(boolean fin) {
        this.Final = fin;
    }

    public int getSchematicId() {
        return this.SchematicId;
    }

    public void setSchematicId(int id) {
        this.SchematicId = id;
    }
}
