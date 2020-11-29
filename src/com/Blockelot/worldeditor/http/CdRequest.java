package com.Blockelot.worldeditor.http;

public class CdRequest
        extends LsRequest {

    private String TargetDirectory;

    public String getTargetDirectory() {
        return this.TargetDirectory;
    }

    public void setTargetDirectory(String target) {
        this.TargetDirectory = target;
    }
}
