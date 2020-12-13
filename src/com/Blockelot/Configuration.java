//End-User License Agreement (EULA) of Blockelot
//
//This End-User License Agreement ("EULA") is a legal agreement between you and Fairfield Tek L.L.C.. Our EULA was created by EULA Template for Blockelot.
//
//This EULA agreement governs your acquisition and use of our Blockelot software ("Software") directly from Fairfield Tek L.L.C. or indirectly through a Fairfield Tek L.L.C. authorized reseller or distributor (a "Reseller"). Our Privacy Policy was created by the Privacy Policy Generator.
//
//Please read this EULA agreement carefully before completing the installation process and using the Blockelot software. It provides a license to use the Blockelot software and contains warranty information and liability disclaimers.
//
//If you register for a free trial of the Blockelot software, this EULA agreement will also govern that trial. By clicking "accept" or installing and/or using the Blockelot software, you are confirming your acceptance of the Software and agreeing to become bound by the terms of this EULA agreement.
//
//If you are entering into this EULA agreement on behalf of a company or other legal entity, you represent that you have the authority to bind such entity and its affiliates to these terms and conditions. If you do not have such authority or if you do not agree with the terms and conditions of this EULA agreement, do not install or use the Software, and you must not accept this EULA agreement.
//
//This EULA agreement shall apply only to the Software supplied by Fairfield Tek L.L.C. herewith regardless of whether other software is referred to or described herein. The terms also apply to any Fairfield Tek L.L.C. updates, supplements, Internet-based services, and support services for the Software, unless other terms accompany those items on delivery. If so, those terms apply.
//
//License Grant
//Fairfield Tek L.L.C. hereby grants you a personal, non-transferable, non-exclusive licence to use the Blockelot software on your devices 
//in accordance with the terms of this EULA agreement.
//
//You are permitted to load the Blockelot software (for example a PC, laptop, mobile or tablet) under your control. You are responsible
//for ensuring your device meets the minimum requirements of the Blockelot software.
//
//You are not permitted to:
//
//Edit, alter, modify, adapt, translate or otherwise change the whole or any part of the Software nor permit the whole or any part of
//the Software to be combined with or become incorporated in any other software, nor decompile, disassemble or reverse engineer the 
//Software or attempt to do any such things
//
//Reproduce, copy, distribute, resell or otherwise use the Software for any commercial purpose
//Allow any third party to use the Software on behalf of or for the benefit of any third party
//Use the Software in any way which breaches any applicable local, national or international law
//use the Software for any purpose that Fairfield Tek L.L.C. considers is a breach of this EULA agreement
//Intellectual Property and Ownership
//Fairfield Tek L.L.C. shall at all times retain ownership of the Software as originally downloaded by you and all subsequent downloads
// of the Software by you. The Software (and the copyright, and other intellectual property rights of whatever nature in the Software,
// including any modifications made thereto) are and shall remain the property of Fairfield Tek L.L.C..
//Fairfield Tek L.L.C. reserves the right to grant licences to use the Software to third parties.
//Termination
//This EULA agreement is effective from the date you first use the Software and shall continue until terminated. 
//You may terminate it at any time upon written notice to Fairfield Tek L.L.C..
//It will also terminate immediately if you fail to comply with any term of this EULA agreement. Upon such termination,
// the licenses granted by this EULA agreement will immediately terminate and you agree to stop all access and use of the Software.
//The provisions that by their nature continue and survive will survive any termination of this EULA agreement.
//
//Governing Law
//This EULA agreement, and any dispute arising out of or in connection with this EULA agreement, 
//shall be governed by and construed in accordance with the laws of us.
//
//By accepting this EULA, you agree to hold harmless (Blockelot) FairfieldTek in the event that the cloud storage service is discontinued.
//
//Blockelot and it's Cloud Storage is provided "as is", without warranties of any kind.
package com.Blockelot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

public class Configuration implements Serializable {

    private static transient final long serialVersionUID = -1681012206529286330L;
    public String WorldId = "NEWSERVER";
    public int MaxClipboardSize = 1000000000;
    public String BaseUri = "http://Blockelot.com/api/worldeditor/v1/";
    //public String BaseUri = "https://localhost:31312/api/worldeditor/v1/";

    public String Permission_User = "Blockelot.WorldEditor.User";
    public String Permission_Clear = "Blockelot.WorldEditor.User.Clear";
    public String Permission_ClearHistory = "Blockelot.WorldEditor.User.ClearHistory";
    public String Permission_Size = "Blockelot.WorldEditor.User.Size";
    public String Permission_Print = "Blockelot.WorldEditor.User.Print";
    public String Permission_Select = "Blockelot.WorldEditor.User.Select";
    
    public String Permission_BlockelotBank = "Blockelot.Bank";

    public String Permission_Editor = "Blockelot.WorldEditor.Editor";
    public String Permission_Copy = "Blockelot.WorldEditor.Editor.Copy";
    public String Permission_Delete = "Blockelot.WorldEditor.Editor.Delete";
    public String Permission_Distr = "Blockelot.WorldEditor.Editor.Distr";
    public String Permission_Paste = "Blockelot.WorldEditor.Editor.Paste";
    public String Permission_StripMine = "Blockelot.WorldEditor.Editor.StripMine";
    public String Permission_Undo = "Blockelot.WorldEditor.Editor.Undo";
    public String Permission_FileSystem = "Blockelot.FileSystem.User";
    public Boolean IncludeInventoryWhenPasting = true;
    public int MaxBlocksWritePerTick = 5000;
    public int MaxBlocksReadPerTick = 10000;
    public int MaxBlocksUploadPerCall = 10000;
    public String NonPastableBlocks = "IRON_BLOCK,GOLD_BLOCK,DIAMOND_BLOCK,BONE_BLOCK,COAL_BLOCK,DIAMOND_BLOCK,LAPIS_BLOCK,NETHERITE_BLOCK,QUART_BLOCK,SHULKER_BOX";
    public ArrayList<Material> NonPastableBlockArray = new ArrayList<>();

    public boolean SaveData() {
        FileConfiguration config = PluginManager.Plugin.getConfig();
        config.set("settings", "Blockelot.Com");
        config.set("settings.Non-Pastable.Blocks", NonPastableBlocks);

        config.set("settings.Description", "A tool to allow players to cut and paste across servers.");
        config.set("settings.HomePage", "Http://www.Blockelot.com");
        config.set("settings.Contact", "Vince@Fairfieldtek.com");
        config.set("settings.WorldId", WorldId);
        config.set("settings.config.MaxBlocksWritePerTick", MaxBlocksWritePerTick);
        config.set("settings.config.MaxBlocksReadPerTick", MaxBlocksReadPerTick);
        config.set("settings.config.MaxBlocksUploadPerCall", MaxBlocksUploadPerCall);
        config.set("settings.config.IncludeInventoryWhenPasting", IncludeInventoryWhenPasting);

        config.set("settings.config.maxclipboardsize", MaxClipboardSize);
        config.set("settings.config.BaseUri", BaseUri);
        config.set("settings.perms.user", Permission_User);
        config.set("settings.perms.clear", Permission_Clear);
        config.set("settings.perms.clearhistory", Permission_ClearHistory);
        config.set("settings.perms.size", Permission_Size);
        config.set("settings.perms.print", Permission_Print);
        config.set("settings.perms.select", Permission_Select);
        config.set("settings.perms.editor", Permission_Editor);
        config.set("settings.perms.copy", Permission_Copy);
        config.set("settings.perms.delete", Permission_Delete);
        config.set("settings.perms.distr", Permission_Distr);
        config.set("settings.perms.paste", Permission_Paste);
        config.set("settings.perms.stripmine", Permission_StripMine);
        config.set("settings.perms.undo", Permission_Undo);
        config.set("settings.perms.filesystem", Permission_FileSystem);
        config.set("settings.perms.bank",Permission_BlockelotBank);
        PluginManager.Plugin.saveConfig();
        return true;
    }

    public boolean LoadData() {
        FileConfiguration config = PluginManager.Plugin.getConfig();
        WorldId = config.getString("settings.WorldId");
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
        
        Permission_BlockelotBank=config.getString("settings.perms.bank");

        MaxBlocksReadPerTick = config.getInt("settings.config.MaxBlocksReadPerTick");
        MaxBlocksWritePerTick = config.getInt("settings.config.MaxBlocksWritePerTick");
        MaxBlocksWritePerTick = config.getInt("settings.config.MaxBlocksUploadPerCall");
        IncludeInventoryWhenPasting = config.getBoolean("IncludeInventoryWhenPasting");
        NonPastableBlocks = config.getString("settings.Non-Pastable.Blocks");

        String[] split = NonPastableBlocks.split(",");
        for (String s : split) {
            Material mat = Material.getMaterial(s);
            NonPastableBlockArray.add(mat);
        }

        return true;
    }

}
