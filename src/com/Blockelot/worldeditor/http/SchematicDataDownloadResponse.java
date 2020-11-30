/*
 * Copyright (C) 2018 geev
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package com.Blockelot.worldeditor.http;

import com.Blockelot.worldeditor.container.BlockInfo;
import com.Blockelot.worldeditor.container.PaletteEntry;

/**
 *
 * @author geev
 */
public class SchematicDataDownloadResponse {

    private boolean IsAuthorized = false;
    private String Auth = "";
    private String Message;
    private String Uuid;
    private boolean WasSuccessful = false;
    private String DirectoryPath;
    private String FileName = "";
    private PaletteEntry[] BlockDataPalette = null;
    private PaletteEntry[] BlockTypePalette = null;
    private PaletteEntry[] BlockInvePalette = null;
    private BlockInfo[] Blocks = null;

    public boolean getIsAuthorized() {
        return this.IsAuthorized;
    }

    public void setIsAuthorized(boolean isAuthorized) {
        this.IsAuthorized = isAuthorized;
    }

    public String getAuth() {
        return this.Auth;
    }

    public void setAuth(String lastAuth) {
        this.Auth = lastAuth;
    }

    public String getMessage() {
        return this.Message;
    }

    public void setMessage(String msg) {
        this.Message = msg;
    }

    public String getUuid() {
        return this.Uuid;
    }

    public void setUuid(String uuid) {
        this.Uuid = uuid;
    }

    public Boolean getWasSuccessful() {
        return this.WasSuccessful;
    }

    public void setWasSuccessful(Boolean status) {
        this.WasSuccessful = status;
    }

    public String getDirectoryPath() {
        return DirectoryPath;
    }

    public void setDirectoryPath(String path) {
        DirectoryPath = path;
    }

    public String getFileName() {
        return this.FileName;
    }

    public void setFileName(String name) {
        this.FileName = name;
    }

    public PaletteEntry[] getBlockDataPalette() {
        return BlockDataPalette;
    }

    public void setBlockDataPalette(PaletteEntry[] palette) {
        BlockDataPalette = palette;
    }

    public PaletteEntry[] getBlockTypePalette() {
        return BlockTypePalette;
    }

    public void setBlockTypePalette(PaletteEntry[] palette) {
        BlockTypePalette = palette;
    }

    public PaletteEntry[] getBlockInvePalette() {
        return BlockInvePalette;
    }

    public void setBlockInvePalette(PaletteEntry[] palette) {
        BlockInvePalette = palette;
    }

    public BlockInfo[] getBlocks() {
        return this.Blocks;
    }

    public void setBlocks(BlockInfo[] blocks) {
        this.Blocks = blocks;
    }
}
