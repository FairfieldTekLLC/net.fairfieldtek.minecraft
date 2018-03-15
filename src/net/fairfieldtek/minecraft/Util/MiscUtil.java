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
package net.fairfieldtek.minecraft.Util;

/**
 *
 * @author geev
 */
public class MiscUtil {

    public static String padRight(String s, int n,String character) {
        String newString = s.trim();
        for (int i = newString.length();i<=n;i++)
        {
            newString= newString + character;
           // System.out.println(newString);
        }
        return newString;
    }

    public static String padLeft(String s, int n,String character) {
        String newString = s.trim();
        //System.out.println("String length: " + newString.length());
        for (int i = newString.length();i<=n;i++)
        {
            newString = character + newString;
            //System.out.println(i + " -> " + newString);
        }
        return newString;
    }
}
