package be.artex.permafrost.listeners;

import be.artex.permafrost.Permafrost;
import be.artex.permafrost.Scoreboard;
import be.artex.permafrost.Statistics;
import fr.mrmicky.fastboard.adventure.FastBoard;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.persistence.PersistentDataContainer;

import javax.naming.ConfigurationException;

public class PlayerConnectionEvent implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) throws ConfigurationException {
        Player player = event.getPlayer();
        PersistentDataContainer playerPDC = player.getPersistentDataContainer();

        if (!playerPDC.has(Statistics.ICE_COLLECTION))
            Statistics.setInt(player, Statistics.ICE_COLLECTION, 0);

        if (!Permafrost.showScoreboard)
            return;

        FastBoard board = new FastBoard(player);
        String scoreboardTitle = Permafrost.instance.getConfig().getString("scoreboard_title");

        if (scoreboardTitle != null)
            board.updateTitle(Component.text(scoreboardTitle));
        else
            throw new ConfigurationException("scoreboard_title is null.");

        Scoreboard.updateScoreboard(board, player);

        Permafrost.boards.put(player.getUniqueId(), board);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        if (Permafrost.showScoreboard)
            Permafrost.boards.remove(event.getPlayer().getUniqueId());
    }
}
