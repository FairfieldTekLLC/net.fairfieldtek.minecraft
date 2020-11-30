/*
 * Copyright (C) 2020 geev
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
package com.Blockelot;

import java.io.Serializable;
import org.bukkit.configuration.file.FileConfiguration;

/**
 *
 * @author geev
 */
public class Configuration implements Serializable {

    private static transient final long serialVersionUID = -1681012206529286330L;
    //Max number of blocks allowed in the clipboard.
    public int MaxClipboardSize = 1000000000;
    public String BaseUri = "https://www.blockelot.com/api/worldeditor/v1/";

    public String Permission_User = "Blockelot.WorldEditor.User";
    public String Permission_Clear = "Blockelot.WorldEditor.User.Clear";
    public String Permission_ClearHistory = "Blockelot.WorldEditor.User.ClearHistory";
    public String Permission_Size = "Blockelot.WorldEditor.User.Size";
    public String Permission_Print = "Blockelot.WorldEditor.User.Print";
    public String Permission_Select = "Blockelot.WorldEditor.User.Select";

    public String Permission_Editor = "Blockelot.WorldEditor.Editor";
    public String Permission_Copy = "Blockelot.WorldEditor.Editor.Copy";
    public String Permission_Delete = "Blockelot.WorldEditor.Editor.Delete";
    public String Permission_Distr = "Blockelot.WorldEditor.Editor.Distr";
    public String Permission_Paste = "Blockelot.WorldEditor.Editor.Paste";
    public String Permission_StripMine = "Blockelot.WorldEditor.Editor.StripMine";
    public String Permission_Undo = "Blockelot.WorldEditor.Editor.Undo";

    public String Permission_FileSystem = "Blockelot.FileSystem.User";

    public boolean SaveData() {
        FileConfiguration config = PluginManager.Plugin.getConfig();
        config.set("settings", "Blockelot.Com");
        
        config.set("settings.Description", "A tool to allow players to cut and paste across servers.");
        config.set("settings.HomePage", "Http://www.Blockelot.com");
        config.set("settings.Contact", "Vince@Fairfieldtek.com");
        
        config.set("settings.config.maxclipboardsize", MaxClipboardSize);
        config.set("settings.config.BaseUri",BaseUri);
        config.set("settings.perms.user",Permission_User);
        config.set("settings.perms.clear",Permission_Clear);
        config.set("settings.perms.clearhistory",Permission_ClearHistory);
        config.set("settings.perms.size",Permission_Size);
        config.set("settings.perms.print",Permission_Print);
        config.set("settings.perms.select",Permission_Select);
        config.set("settings.perms.editor",Permission_Editor);
        config.set("settings.perms.copy",Permission_Copy);
        config.set("settings.perms.delete",Permission_Delete);
        config.set("settings.perms.distr",Permission_Distr);
        config.set("settings.perms.paste",Permission_Paste);
        config.set("settings.perms.stripmine",Permission_StripMine);
        config.set("settings.perms.undo",Permission_Undo);
        config.set("settings.perms.filesystem",Permission_FileSystem);
        PluginManager.Plugin.saveConfig();
        return true;
    }

    public boolean LoadData() {
        FileConfiguration config = PluginManager.Plugin.getConfig();
        MaxClipboardSize = config.getInt("settings.config.maxclipboardsize");
        BaseUri = config.getString("settings.config.baseuri");
        Permission_User = config.getString("settings.perms.user");
        Permission_Clear = config.getString("settings.perms.clear");
        Permission_ClearHistory = config.getString("settings.perms.clearhistory");
        Permission_Size = config.getString("settings.perms.size");
        Permission_Print = config.getString("settings.perms.print");
        Permission_Select = config.getString("settings.perms.select");

        Permission_Editor = config.getString("settings.perms.editor");
        Permission_Copy = config.getString("settings.perms.copy");
        Permission_Delete = config.getString("settings.perms.delete");
        Permission_Distr = config.getString("settings.perms.distr");
        Permission_Paste = config.getString("settings.perms.paste");
        Permission_StripMine = config.getString("settings.perms.stripmine");
        Permission_Undo = config.getString("settings.perms.undo");
        Permission_FileSystem = config.getString("settings.perms.filesystem");

        return true;
    }

//    public boolean saveData() {
//        String filePath = "Settings.config";
//        try {
//            BukkitObjectOutputStream out = new BukkitObjectOutputStream(new FileOutputStream(filePath));
//            out.writeObject(this);
//            out.close();
//            System.out.println("Saved settings config.");
//            return true;
//        } catch (IOException e) {
//            System.out.println("Save fialed");
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    public boolean loadData() {
//        String filePath = "Settings.config";
//        try {
//            BukkitObjectInputStream in = new BukkitObjectInputStream(new FileInputStream(filePath));
//            Configuration data = (Configuration) in.readObject();
//            in.close();
//            if (data != null) {
//                MaxClipboardSize = data.MaxClipboardSize;
//                BaseUri = data.BaseUri;
//
//                Permission_User = data.Permission_User;
//                Permission_Clear = data.Permission_Clear;
//                Permission_ClearHistory = data.Permission_ClearHistory;
//                Permission_Size = data.Permission_Size;
//                Permission_Print = data.Permission_Print;
//                Permission_Select = data.Permission_Select;
//
//                Permission_Editor = data.Permission_Editor;
//                Permission_Copy = data.Permission_Copy;
//                Permission_Delete = data.Permission_Delete;
//                Permission_Distr = data.Permission_Distr;
//                Permission_Paste = data.Permission_Paste;
//                Permission_StripMine = data.Permission_StripMine;
//                Permission_Undo = data.Permission_Undo;
//                Permission_FileSystem = data.Permission_FileSystem;
//            }
//            return true;
//        } catch (ClassNotFoundException | IOException e) {
//            // TODO Auto-generated catch block
//            //e.printStackTrace();
//            System.out.println("Failed to find File.");
//            return false;
//        }
//        
//   }
}
