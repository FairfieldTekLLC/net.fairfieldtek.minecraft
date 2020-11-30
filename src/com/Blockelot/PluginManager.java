package com.Blockelot;

import com.Blockelot.Util.ServerUtil;
import com.Blockelot.Util.Verify;
import com.Blockelot.worldeditor.commands.Clear;
import com.Blockelot.worldeditor.commands.ClearHistory;
import com.Blockelot.worldeditor.commands.ClipDimensions;
import com.Blockelot.worldeditor.commands.Copy;
import com.Blockelot.worldeditor.commands.Delete;
import com.Blockelot.worldeditor.commands.Demographics;
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
import java.util.HashMap;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;
import java.util.logging.Level;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;


public class PluginManager {

    public static Plugin Plugin;
    public static String Version;
    public static HashMap<Player, PlayerInfo> PlayerInfoList;
    private static String WorldId;
    public static Configuration Config;
    

    static {
        PlayerInfoList = new HashMap<>();
        Version = "1.0.0.0";
        Config = new Configuration();
    }

    public static String getWorldId() {
        return WorldId;
    }

    public static boolean Initialize(Plugin plugin)  {
        Plugin = plugin;
        if (!plugin.getDataFolder().exists())
        {
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
        

        ServerUtil.consoleLog("Calling home... no really, I am.");

        ServerUtil.consoleLog("No reason for concern, how do you think the cloud storage works?");

        try {
            WorldId = Verify.Register(Plugin);
        } catch (Exception e) {
            ServerUtil.consoleLog("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            ServerUtil.consoleLog("!!   Warning, cannot reach www.Blockelot.com    !!");
            ServerUtil.consoleLog("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            return false;
        }
        return true;
    }

    public static void ShutDown() {
        Config.SaveData();
        PlayerInfoList.clear();
    }

}
