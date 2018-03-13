package net.fairfieldtek.minecraft;

import java.util.HashMap;
import net.fairfieldtek.minecraft.worldeditor.commands.Clear;
import net.fairfieldtek.minecraft.worldeditor.commands.Copy;
import net.fairfieldtek.minecraft.worldeditor.commands.Delete;
import net.fairfieldtek.minecraft.worldeditor.commands.Inspect;
import net.fairfieldtek.minecraft.worldeditor.commands.Paste;
import net.fairfieldtek.minecraft.worldeditor.commands.Print;
import net.fairfieldtek.minecraft.worldeditor.commands.RegenChunk;
import net.fairfieldtek.minecraft.worldeditor.commands.Rotate;
import net.fairfieldtek.minecraft.worldeditor.commands.Select;
import net.fairfieldtek.minecraft.worldeditor.commands.Undo;
import net.fairfieldtek.minecraft.worldeditor.commands.filesystem.Authenticate;
import net.fairfieldtek.minecraft.worldeditor.commands.filesystem.CD;
import net.fairfieldtek.minecraft.worldeditor.commands.filesystem.LS;
import net.fairfieldtek.minecraft.worldeditor.commands.filesystem.Login;
import net.fairfieldtek.minecraft.worldeditor.commands.filesystem.MK;
import net.fairfieldtek.minecraft.worldeditor.commands.filesystem.RM;
import net.fairfieldtek.minecraft.worldeditor.commands.filesystem.Register;
import net.fairfieldtek.minecraft.worldeditor.commands.filesystem.SaveClipboard;
import net.fairfieldtek.minecraft.worldeditor.commands.filesystem.LoadClipboard;
import net.fairfieldtek.minecraft.worldeditor.container.PlayerInfo;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;

public class Initialization {

    public static Plugin Plugin;
    public static String BaseUri;
    public static HashMap<Player, PlayerInfo> PlayerInfoList;

    public static void Initialize(Plugin plugin) {
        Plugin = plugin;
        plugin.getCommand("fft.we.select").setExecutor((CommandExecutor) new Select());
        plugin.getCommand("fft.we.print").setExecutor((CommandExecutor) new Print());
        plugin.getCommand("fft.we.clear").setExecutor((CommandExecutor) new Clear());
        plugin.getCommand("fft.we.paste").setExecutor((CommandExecutor) new Paste());
        plugin.getCommand("fft.we.copy").setExecutor((CommandExecutor) new Copy());
        plugin.getCommand("fft.we.undo").setExecutor((CommandExecutor) new Undo());
        plugin.getCommand("fft.we.in").setExecutor((CommandExecutor) new Inspect());
        plugin.getCommand("fft.we.rot").setExecutor((CommandExecutor) new Rotate());
        plugin.getCommand("fft.we.rotate").setExecutor((CommandExecutor) new Rotate());
        plugin.getCommand("fft.we.delete").setExecutor((CommandExecutor) new Delete());
        plugin.getCommand("fft.we.del").setExecutor((CommandExecutor) new Delete());
        plugin.getCommand("fft.we.chunkregen").setExecutor((CommandExecutor) new RegenChunk());
        plugin.getCommand("fft.reg").setExecutor((CommandExecutor) new Register());
        plugin.getCommand("fft.auth").setExecutor((CommandExecutor) new Authenticate());
        plugin.getCommand("fft.login").setExecutor((CommandExecutor) new Login());
        plugin.getCommand("fft.ls").setExecutor((CommandExecutor) new LS());
        plugin.getCommand("fft.cd").setExecutor((CommandExecutor) new CD());
        plugin.getCommand("fft.rm").setExecutor((CommandExecutor) new RM());
        plugin.getCommand("fft.mk").setExecutor((CommandExecutor) new MK());
        plugin.getCommand("fft.save").setExecutor((CommandExecutor) new SaveClipboard());
        plugin.getCommand("fft.load").setExecutor((CommandExecutor) new LoadClipboard());
    }

    public static void ShutDown() {
        PlayerInfoList.clear();
    }

    static {
//        BaseUri = "http://localhost:31312/api/worldeditor/v1/";
        BaseUri="http://mc.fairfieldtek.com/api/worldeditor/v1/";
        PlayerInfoList = new HashMap<>();
    }
}
