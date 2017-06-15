package me.itskev.raindetect;

import me.itskev.raindetect.events.PlayerEvent;
import me.itskev.raindetect.util.WriteConfig;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Main class of the RainDetect plugin
 *
 * @author ItsKev
 */
public class RainDetect extends JavaPlugin{

    private static RainDetect instance;

    public static RainDetect getInstance() {return instance;}

    @Override
    public void onEnable() {
        instance = this;
        this.registerEvents();
        WriteConfig.getInstance();
    }

    @Override
    public void onDisable() {
    }

    private void registerEvents() {
        PluginManager pm = getServer().getPluginManager();

        pm.registerEvents(new PlayerEvent(), this);
    }
}