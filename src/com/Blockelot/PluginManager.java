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

import com.Blockelot.Util.ServerUtil;
import com.Blockelot.Util.Verify;
import com.Blockelot.worldeditor.commands.About;
import com.Blockelot.worldeditor.commands.BlockBankDeposit;
import com.Blockelot.worldeditor.commands.BlockBankInventory;
import com.Blockelot.worldeditor.commands.BlockBankWithdrawl;
import com.Blockelot.worldeditor.commands.Clear;
import com.Blockelot.worldeditor.commands.ClearHistory;
import com.Blockelot.worldeditor.commands.ClipDimensions;
import com.Blockelot.worldeditor.commands.Copy;
import com.Blockelot.worldeditor.commands.Delete;
import com.Blockelot.worldeditor.commands.Demographics;
import com.Blockelot.worldeditor.commands.Help;
import com.Blockelot.worldeditor.commands.Paste;
import com.Blockelot.worldeditor.commands.Print;
import com.Blockelot.worldeditor.commands.Select;
import com.Blockelot.worldeditor.commands.StripMine;
import com.Blockelot.worldeditor.commands.Undo;
import com.Blockelot.worldeditor.commands.filesystem.Authenticate;
import com.Blockelot.worldeditor.commands.filesystem.CD;
import com.Blockelot.worldeditor.commands.filesystem.LS;
import com.Blockelot.worldeditor.commands.filesystem.LoadClipboard;
import com.Blockelot.worldeditor.commands.filesystem.MK;
import com.Blockelot.worldeditor.commands.filesystem.RM;
import com.Blockelot.worldeditor.commands.filesystem.Register;
import com.Blockelot.worldeditor.commands.filesystem.SaveClipboard;
import com.Blockelot.worldeditor.container.PlayerInfo;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import net.milkbowl.vault.permission.Permission;

import java.util.logging.Logger;

import java.util.HashMap;
import java.util.UUID;
import org.bukkit.plugin.RegisteredServiceProvider;

public class PluginManager {

    public static Plugin Plugin;
    public static String Version;
    
    private static HashMap<UUID, PlayerInfo> PlayerInfoList;
    
    public static PlayerInfo GetPlayerInfo(UUID key){
        return PlayerInfoList.get(key);
    }
    
    public static void AddPlayerInfo(PlayerInfo pi){
        PlayerInfoList.put(pi.getPlayer().getUniqueId(), pi);
    }
    
    public static boolean HasPlayer(Player player){
        if (PlayerInfoList.containsKey(player.getUniqueId()))
            return true;
        return false;
    }
    
    public static void RemovePlayer(Player player){
        if (PlayerInfoList.containsKey(player.getUniqueId())){
            PlayerInfoList.remove(player.getUniqueId());
        }
    }
    
    public static Configuration Config;
    private static final Logger log = Logger.getLogger("Minecraft");

//    private static Economy econ = null;
//    private static Permission perms = null;
//    private static Chat chat = null;
    static {
        PlayerInfoList = new HashMap<>();
        Version = "1.0.0.0";
        Config = new Configuration();
        try {
            Config.LoadData();
        } catch (Exception e) {

        }
    }

    public static String getWorldId() {
        return Config.WorldId;
    }

    public static boolean Initialize(Plugin plugin) {
        Plugin = plugin;
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdir();
            plugin.saveDefaultConfig();
        }
        plugin.getCommand("b.we.clear").setExecutor((CommandExecutor) new Clear());
        plugin.getCommand("b.we.clearhistory").setExecutor((CommandExecutor) new ClearHistory());
        plugin.getCommand("b.we.size").setExecutor((CommandExecutor) new ClipDimensions());
        plugin.getCommand("b.we.print").setExecutor((CommandExecutor) new Print());
        plugin.getCommand("b.we.select").setExecutor((CommandExecutor) new Select());

        plugin.getCommand("b.we.copy").setExecutor((CommandExecutor) new Copy());
        plugin.getCommand("b.we.del").setExecutor((CommandExecutor) new Delete());
        plugin.getCommand("b.we.delete").setExecutor((CommandExecutor) new Delete());
        plugin.getCommand("b.we.distr").setExecutor((CommandExecutor) new Demographics());
        plugin.getCommand("b.we.paste").setExecutor((CommandExecutor) new Paste());
        plugin.getCommand("b.we.stripmine").setExecutor((CommandExecutor) new StripMine());
        plugin.getCommand("b.we.undo").setExecutor((CommandExecutor) new Undo());

        plugin.getCommand("b.reg").setExecutor((CommandExecutor) new Register());
        plugin.getCommand("b.auth").setExecutor((CommandExecutor) new Authenticate());
        plugin.getCommand("b.ls").setExecutor((CommandExecutor) new LS());
        plugin.getCommand("b.cd").setExecutor((CommandExecutor) new CD());
        plugin.getCommand("b.rm").setExecutor((CommandExecutor) new RM());
        plugin.getCommand("b.mk").setExecutor((CommandExecutor) new MK());
        plugin.getCommand("b.save").setExecutor((CommandExecutor) new SaveClipboard());
        plugin.getCommand("b.load").setExecutor((CommandExecutor) new LoadClipboard());
        plugin.getCommand("b.bbdep").setExecutor((CommandExecutor) new BlockBankDeposit());
        plugin.getCommand("b.bbinv").setExecutor((CommandExecutor) new BlockBankInventory());
        plugin.getCommand("b.bbwd").setExecutor((CommandExecutor) new BlockBankWithdrawl());
        plugin.getCommand("b.help").setExecutor((CommandExecutor) new Help());
        plugin.getCommand("b.about").setExecutor((CommandExecutor) new About());

//        if (!setupEconomy()) {
//            log.severe(String.format("[%s] - Disabled due to no Vault dependency found!",plugin.getDescription().getName()));
//            PluginManager.Plugin.getServer().getPluginManager().disablePlugin(PluginManager.Plugin);
//            return false;
//        }
//        
//        setupPermissions();
//        setupChat();
        ServerUtil.consoleLog("Calling home... no really, I am.");

        ServerUtil.consoleLog("No reason for concern, how do you think the cloud storage works?");

        try {
            Verify.Register(Plugin);

        } catch (Exception e) {
            ServerUtil.consoleLog("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            ServerUtil.consoleLog("!!   Warning, cannot reach www.Blockelot.com    !!");
            ServerUtil.consoleLog("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            ServerUtil.consoleLog(e);
            return false;
        }
        return true;
    }

    public static void ShutDown() {
        log.info(String.format("[%s] Disabled Version %s", PluginManager.Plugin.getDescription().getName(), PluginManager.Plugin.getDescription().getVersion()));
        Config.SaveData();
        PlayerInfoList.clear();
    }

//    private static boolean setupEconomy() {
//        if (PluginManager.Plugin.getServer().getPluginManager().getPlugin("Vault") == null) {
//            return false;
//        }
//        RegisteredServiceProvider<Economy> rsp = PluginManager.Plugin.getServer().getServicesManager().getRegistration(Economy.class);
//        if (rsp == null) {
//            return false;
//        }
//        econ = rsp.getProvider();
//        return econ != null;
//    }
//
//    private static boolean setupChat() {
//        RegisteredServiceProvider<Chat> rsp = PluginManager.Plugin.getServer().getServicesManager().getRegistration(Chat.class);
//        chat = rsp.getProvider();
//        return chat != null;
//    }
//
//    private static boolean setupPermissions() {
//        RegisteredServiceProvider<Permission> rsp = PluginManager.Plugin.getServer().getServicesManager().getRegistration(Permission.class);
//        perms = rsp.getProvider();
//        return perms != null;
//    }
//
//    public static Economy getEconomy() {
//        return econ;
//    }
//
//    public static Permission getPermissions() {
//        return perms;
//    }
//
//    public static Chat getChat() {
//        return chat;
//    }
}
