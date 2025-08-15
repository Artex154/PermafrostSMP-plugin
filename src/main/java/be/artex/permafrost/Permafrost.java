package be.artex.permafrost;

import be.artex.permafrost.commands.Ice;
import be.artex.permafrost.commands.Leaderboard;
import be.artex.permafrost.listeners.BlockEvent;
import be.artex.permafrost.listeners.PlayerConnectionEvent;
import fr.mrmicky.fastboard.adventure.FastBoard;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class Permafrost extends JavaPlugin {
    public static Plugin instance = null;
    public static final Map<UUID, FastBoard> boards = new HashMap<>();

    @Override
    public void onEnable() {
        instance = this;

        Bukkit.getPluginManager().registerEvents(new PlayerConnectionEvent(), this);
        Bukkit.getPluginManager().registerEvents(new BlockEvent(), this);

        getCommand("ice").setExecutor(new Ice());
        getCommand("leaderboard").setExecutor(new Leaderboard());

        Bukkit.getScheduler().runTaskTimer(this, () -> Bukkit.getOnlinePlayers().forEach((player -> {
            FastBoard board = boards.get(player.getUniqueId());

            Scoreboard.updateScoreboard(board, player);
        })), 0L, 1200L);
    }
}
