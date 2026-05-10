package be.artex.ice_collection;

import be.artex.ice_collection.commands.Ice;
import be.artex.ice_collection.commands.Leaderboard;
import be.artex.ice_collection.listeners.BlockEvent;
import be.artex.ice_collection.listeners.PlayerConnectionEvent;
import fr.mrmicky.fastboard.adventure.FastBoard;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class IceCollection extends JavaPlugin {
    public static Plugin instance = null;
    public static final Map<UUID, FastBoard> boards = new HashMap<>();

    // Config
    public static boolean showScoreboard = false;
    public static int iceValue = 0;
    public static int packedIceValue = 0;
    public static int blueIceValue = 0;

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();

        showScoreboard = getConfig().getBoolean("scoreboard");
        iceValue = getConfig().getInt("ice");
        packedIceValue = getConfig().getInt("packed_ice");
        blueIceValue = getConfig().getInt("blue_ice");

        Bukkit.getPluginManager().registerEvents(new PlayerConnectionEvent(), this);
        Bukkit.getPluginManager().registerEvents(new BlockEvent(), this);

        getCommand("ice").setExecutor(new Ice());
        getCommand("leaderboard").setExecutor(new Leaderboard());

        if (!showScoreboard)
            return;

        Bukkit.getScheduler().runTaskTimer(this, () -> Bukkit.getOnlinePlayers().forEach((player -> {
            FastBoard board = boards.get(player.getUniqueId());

            Scoreboard.updateScoreboard(board, player);
        })), 0L, 1200L);
    }
}
