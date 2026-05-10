package be.artex.permafrost;

import fr.mrmicky.fastboard.adventure.FastBoard;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Scoreboard {
    private static final Component BORDER = Component.text("          ", TextColor.color(60, 60, 60))
            .decorate(TextDecoration.STRIKETHROUGH)
            .append(Component.text("           ", NamedTextColor.DARK_GRAY).decorate(TextDecoration.STRIKETHROUGH))
            .append(Component.text("          ", TextColor.color(60, 60, 60)).decorate(TextDecoration.STRIKETHROUGH));

    private static final Component ICE_COLLECTION_PREFIX = Component.text(" ɪᴄᴇ ᴄᴏʟʟᴇᴄᴛɪᴏɴ: ");
    private static final Component PLAYTIME_PREFIX = Component.text(" ᴘʟᴀʏᴛɪᴍᴇ: ");
    private static final Component ONLINE_PLAYERS_PREFIX = Component.text(" ᴏɴʟɪɴᴇ ᴘʟᴀʏᴇʀs: ");

    public static void updateScoreboard(FastBoard board, Player player) {
        List<Component> lines = new ArrayList<>();

        lines.add(BORDER);
        lines.add(Component.space());

        if (Permafrost.instance.getConfig().getBoolean("ice_collection_statistic"))
            lines.add(ICE_COLLECTION_PREFIX.append(
                Component.text(Statistics.getInt(player, Statistics.ICE_COLLECTION), TextColor.color(32, 195, 208))));

        if (Permafrost.instance.getConfig().getBoolean("playtime_statistic"))
            lines.add(PLAYTIME_PREFIX.append(
                Component.text(Statistics.playerHoursPlaytime(player) + " ʜᴏᴜʀs", NamedTextColor.BLUE)));

        lines.add(Component.space());

        if (Permafrost.instance.getConfig().getBoolean("online_player_count"))
            lines.add(ONLINE_PLAYERS_PREFIX.append(
                            Component.text(Bukkit.getOnlinePlayers().size(), TextColor.color(235, 75, 6))));

        lines.add(Component.space());
        lines.add(BORDER);

        board.updateLines(lines);
    }
}
