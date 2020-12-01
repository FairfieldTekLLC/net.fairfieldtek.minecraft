package com.Blockelot.Util;

public class MiscUtil {

    public static String padRight(String s, int n, String character) {
        String newString = s.trim();
        for (int i = newString.length(); i <= n; i++) {
            newString = newString + character;
        }
        return newString;
    }

    public static String padLeft(String s, int n, String character) {
        String newString = s.trim();
        for (int i = newString.length(); i <= n; i++) {
            newString = character + newString;
        }
        return newString;
    }
}
