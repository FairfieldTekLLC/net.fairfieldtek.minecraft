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
package com.Blockelot.worldeditor.container;

import com.Blockelot.Util.ServerUtil;
import java.util.*;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class PlayerInfo {

    public PlayerInfo(Player player) {
        Player = player;
        setUUID(player.getUniqueId().toString());
    }

    private String UUID;

    private Player Player;

    public Player getPlayer() {
        return Player;
    }

    public void setPlayer(Player player) {
        Player = player;
    }

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String uuid) {
        UUID = uuid;
    }

    private String LastAuth = "";
    private String CurrentPath = "";
    public IPoint SelectStart = null;
    public IPoint SelectEnd = null;

    public BlockCollection ClipSchematic = new BlockCollection();

    private BlockCollection UndoSchematic = new BlockCollection();

    private final Stack<BlockCollection> UndoHistory = new Stack<>();

    public BlockCollection NewUndo() {
        UndoHistory.push(UndoSchematic);
        UndoSchematic = new BlockCollection();
        return UndoSchematic;
    }

    public BlockCollection GetUndo() {
        BlockCollection current = UndoSchematic;
        if (!UndoHistory.empty()) {
            UndoSchematic = UndoHistory.pop();
        } else {
            UndoSchematic = new BlockCollection();
        }
        return current;
    }

    public void ClearHistory() {
        UndoSchematic = new BlockCollection();
        UndoHistory.empty();
    }

    public String Token;
    public boolean CancelLastAction = false;

    private boolean IsProcessing;

    public boolean getIsProcessing() {
        return this.IsProcessing;
    }

    public void setIsProcessing(boolean flag, String caller) {
        IsProcessing = flag;
        //ServerUtil.consoleLog(caller + " is setting Busy: " + flag);
    }

    public String getCurrentPath() {
        return this.CurrentPath;
    }

    public void setCurrentPath(String path) {
        this.CurrentPath = path;
    }

    public String getLastAuth() {
        return this.LastAuth;
    }

    public void setLastAuth(String lastAuth) {
        this.LastAuth = lastAuth;
    }

    private String GenLineCenter(String msg) {
        int lineLength = 52;
        int msgLength = msg.length();
        int startPos = (lineLength / 2) - msgLength / 2;

        final String lineBeginFormat = ChatColor.GOLD + "#" + ChatColor.BLACK + "--------------------------------------------------";
        final String lineEndFormat = "--------------------------------------------------";

        String OutLine = lineBeginFormat.substring(0, startPos) + ChatColor.YELLOW + msg.trim() + ChatColor.BLACK + lineEndFormat;

        OutLine = OutLine.substring(0, 57) + ChatColor.GOLD + " #";
        return OutLine;
    }

    private String GenLineLeft(String msg) {
        int lineLength = 52;
        String OutLine = ChatColor.GOLD + "#" + ChatColor.BLACK + "-" + ChatColor.YELLOW + msg + ChatColor.BLACK + "--------------------------------------------------------------";
        OutLine = OutLine.substring(0, 57) + ChatColor.GOLD + " #";
        return OutLine;

    }

    public void SendBankMessageHeader(ArrayList<String> msgs, boolean leftJustify) {
        SendBankMessageHeader(msgs, leftJustify, true);
    }

    public void SendBankMessageHeader(ArrayList<String> msgs, boolean leftJustify, boolean doBorder) {
        getPlayer().sendMessage(ChatColor.GOLD + "###################################################");
        getPlayer().sendMessage(ChatColor.GOLD + "#--Welcome to the First Minecraft Bank of Blockelot--#".toUpperCase());
        getPlayer().sendMessage(ChatColor.GOLD + "###################################################");
        for (String line : msgs) {

            if (doBorder) {
                if (!leftJustify) {
                    getPlayer().sendMessage(GenLineCenter(line));
                } else {
                    getPlayer().sendMessage(GenLineLeft(line));
                }
            } else {
                getPlayer().sendMessage(line);
            }
        }
    

    getPlayer().sendMessage(ChatColor.GOLD + "###################################################");
    }
}
