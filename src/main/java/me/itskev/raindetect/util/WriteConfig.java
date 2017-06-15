package me.itskev.raindetect.util;

import me.itskev.raindetect.RainDetect;
import org.bukkit.plugin.java.JavaPlugin;

public class WriteConfig {

    private static WriteConfig instance;

    public static WriteConfig getInstance() {
        return instance == null ? instance = new WriteConfig() : instance;
    }

    private JavaPlugin plugin;

    private WriteConfig() {
        this.plugin = RainDetect.getInstance();
        this.plugin.getConfig().options().copyDefaults(true);
        this.plugin.saveDefaultConfig();
        this.plugin.saveConfig();
    }
}