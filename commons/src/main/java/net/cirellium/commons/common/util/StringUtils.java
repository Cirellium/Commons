package net.cirellium.commons.common.util;

import org.bukkit.ChatColor;

public class StringUtils {
    
    public static String translateColorCodes(String toTranslate) {
        return ChatColor.translateAlternateColorCodes('&', toTranslate);
    }

}