package it.mauro.dragoncorebungee.api;

import net.md_5.bungee.api.ChatColor;

public class Format {

    public static String error(String s) {
        return ChatColor.translateAlternateColorCodes('&', "&c(&4&l!&c) &4" +s);
    }
    public static String msg(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }
}
