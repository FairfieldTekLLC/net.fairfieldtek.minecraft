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
package com.Blockelot.Util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;

/**
 *
 * @author geev
 */
public class ServerUtil {
       private static Server getServer(){
        return Bukkit.getServer();
    }
    private static final String Console_Prefix = "§2[BLOCKELOT] §f";
    private static final String Error_Prefix = "§c[BLOCKELOT-Error] §f";

    public static void consoleLog(String message){
        getServer().getConsoleSender().sendMessage(Console_Prefix + colorize(message));
    }

    public static void consoleLog(Throwable message){
        getServer().getConsoleSender().sendMessage(Error_Prefix + message);
    }

    private static String colorize(String message){
        return ChatColor.translateAlternateColorCodes('&', message);
    }

}
