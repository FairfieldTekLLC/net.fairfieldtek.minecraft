package com.Blockelot;

import java.util.HashMap;
import com.Blockelot.worldeditor.commands.Clear;
import com.Blockelot.worldeditor.commands.ClearHistory;
import com.Blockelot.worldeditor.commands.Copy;
import com.Blockelot.worldeditor.commands.Delete;
import com.Blockelot.worldeditor.commands.Paste;
import com.Blockelot.worldeditor.commands.Print;
import com.Blockelot.worldeditor.commands.Rotate;
import com.Blockelot.worldeditor.commands.ClipDimensions;
import com.Blockelot.worldeditor.commands.Select;
import com.Blockelot.worldeditor.commands.Undo;
import com.Blockelot.worldeditor.commands.MatList;
import com.Blockelot.worldeditor.commands.MapTheWorld;
import com.Blockelot.worldeditor.commands.StripMine;
import com.Blockelot.worldeditor.commands.Demographics;
import com.Blockelot.worldeditor.commands.filesystem.Authenticate;
import com.Blockelot.worldeditor.commands.filesystem.CD;
import com.Blockelot.worldeditor.commands.filesystem.LS;
import com.Blockelot.worldeditor.commands.filesystem.MK;
import com.Blockelot.worldeditor.commands.filesystem.RM;
import com.Blockelot.worldeditor.commands.filesystem.Register;
import com.Blockelot.worldeditor.commands.filesystem.SaveClipboard;
import com.Blockelot.worldeditor.commands.filesystem.LoadClipboard;
import com.Blockelot.worldeditor.container.PlayerInfo;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;

public class PluginManager {

    public static Plugin Plugin;
    public static String BaseUri;
    public static HashMap<Player, PlayerInfo> PlayerInfoList;

    public static void Initialize(Plugin plugin) {
        Plugin = plugin;
        plugin.getCommand("fft.we.select").setExecutor((CommandExecutor) new Select());
        plugin.getCommand("fft.we.print").setExecutor((CommandExecutor) new Print());
        plugin.getCommand("fft.we.clear").setExecutor((CommandExecutor) new Clear());
        plugin.getCommand("fft.we.clearhistory").setExecutor((CommandExecutor) new ClearHistory());
        plugin.getCommand("fft.we.paste").setExecutor((CommandExecutor) new Paste());
        plugin.getCommand("fft.we.copy").setExecutor((CommandExecutor) new Copy());
        plugin.getCommand("fft.we.undo").setExecutor((CommandExecutor) new Undo());
        plugin.getCommand("fft.we.rot").setExecutor((CommandExecutor) new Rotate());
        plugin.getCommand("fft.we.rotate").setExecutor((CommandExecutor) new Rotate());
        plugin.getCommand("fft.we.delete").setExecutor((CommandExecutor) new Delete());
        plugin.getCommand("fft.we.del").setExecutor((CommandExecutor) new Delete());
        plugin.getCommand("fft.we.distr").setExecutor((CommandExecutor) new Demographics());
        plugin.getCommand("fft.we.size").setExecutor((CommandExecutor) new ClipDimensions());
        plugin.getCommand("fft.we.matlist").setExecutor((CommandExecutor) new MatList());
        plugin.getCommand("fft.we.maptheworld").setExecutor((CommandExecutor) new MapTheWorld());
        plugin.getCommand("fft.reg").setExecutor((CommandExecutor) new Register());
        plugin.getCommand("fft.auth").setExecutor((CommandExecutor) new Authenticate());
        plugin.getCommand("fft.ls").setExecutor((CommandExecutor) new LS());
        plugin.getCommand("fft.cd").setExecutor((CommandExecutor) new CD());
        plugin.getCommand("fft.rm").setExecutor((CommandExecutor) new RM());
        plugin.getCommand("fft.mk").setExecutor((CommandExecutor) new MK());
        plugin.getCommand("fft.save").setExecutor((CommandExecutor) new SaveClipboard());
        plugin.getCommand("fft.load").setExecutor((CommandExecutor) new LoadClipboard());
        plugin.getCommand("fft.strip").setExecutor((CommandExecutor) new StripMine());
    }

    public static void ShutDown() {
        PlayerInfoList.clear();
    }

    static {
        BaseUri = "http://localhost:31312/api/worldeditor/v1/";
        //BaseUri = "http://mc.fairfieldtek.com/api/worldeditor/v1/";
        PlayerInfoList = new HashMap<>();
    }
    
    
    
}
