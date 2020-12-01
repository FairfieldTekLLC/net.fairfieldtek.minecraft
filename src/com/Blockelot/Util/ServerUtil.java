package com.Blockelot.Util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;

public class ServerUtil {

    private static Server getServer() {
        return Bukkit.getServer();
    }
    private static final String Console_Prefix = "§2[BLOCKELOT] §f";
    private static final String Error_Prefix = "§c[BLOCKELOT-Error] §f";

    public static void consoleLog(String message) {
        getServer().getConsoleSender().sendMessage(Console_Prefix + colorize(message));
    }

    public static void consoleLog(Throwable message) {
        getServer().getConsoleSender().sendMessage(Error_Prefix + message);
    }

    private static String colorize(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

}
