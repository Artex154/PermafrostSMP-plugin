package be.artex.permafrost.listeners;

import be.artex.permafrost.Permafrost;
import be.artex.permafrost.Scoreboard;
import be.artex.permafrost.Statistics;
import fr.mrmicky.fastboard.adventure.FastBoard;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.persistence.PersistentDataContainer;

public class PlayerConnectionEvent implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        PersistentDataContainer playerPDC = player.getPersistentDataContainer();

        if (!playerPDC.has(Statistics.ICE_COLLECTION))
            Statistics.setInt(player, Statistics.ICE_COLLECTION, 0);

        FastBoard board = new FastBoard(player);
        board.updateTitle(Component.text("ᴘᴇʀᴍᴀғʀᴏsᴛ sᴍᴘ", TextColor.color(63, 208, 212)).decorate(TextDecoration.BOLD, TextDecoration.ITALIC));
        Scoreboard.updateScoreboard(board, player);

        Permafrost.boards.put(player.getUniqueId(), board);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Permafrost.boards.remove(event.getPlayer().getUniqueId());
    }
}
