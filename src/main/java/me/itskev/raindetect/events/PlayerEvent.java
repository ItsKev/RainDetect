package me.itskev.raindetect.events;

import me.itskev.raindetect.RainDetect;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * Checks if a player is standing in the rain.
 */
public class PlayerEvent implements Listener {

    private List<UUID> playersInRain;
    private RainDetect plugin;

    public PlayerEvent() {
        this.playersInRain = Collections.synchronizedList(new ArrayList<>());
        this.plugin = RainDetect.getInstance();
        this.startCleanUp();
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        if (event.getTo().getBlock().equals(event.getFrom().getBlock())) {
            return;
        }
        if (!player.getWorld().hasStorm()) {
            return;
        }
        if (this.playersInRain.contains(player.getUniqueId())) {
            return;
        }
        if (this.hasSolidBlocksAbove(event.getTo())) {
            return;
        }
        Biome biome = player.getLocation().getBlock().getBiome();
        if (biome.toString().contains("DESERT")
                || biome.toString().contains("SAVANNA")
                || biome.toString().contains("MESA")
                || biome.toString().contains("PLATEAU")
                || biome.toString().contains("TAIGA")) {
            return;
        }
        this.playersInRain.add(player.getUniqueId());
        String commandToExecute = this.plugin.getConfig().getString("CommandToExecute");
        if (commandToExecute != null) {
            Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), commandToExecute + " " + player.getName());
        }
    }

    private boolean hasSolidBlocksAbove(Location location) {
        for (int y = location.getBlockY() + 2; y < location.getWorld().getMaxHeight(); y++) {
            if (location.getWorld().getBlockAt(location.getBlockX(), y, location.getBlockZ()).getType() != Material.AIR) {
                return true;
            }
        }
        return false;
    }

    private void startCleanUp() {
        int cooldown = this.plugin.getConfig().getInt("Interval");
        new BukkitRunnable() {

            @Override
            public void run() {
                playersInRain.clear();
            }
        }.runTaskTimerAsynchronously(this.plugin, 0, cooldown * 20);
    }

}
