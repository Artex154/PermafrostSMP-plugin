package be.artex.ice_collection.commands;

import be.artex.ice_collection.Statistics;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class Leaderboard implements CommandExecutor {
    private static final NamespacedKey ICE = Statistics.ICE_COLLECTION;

    private static final Component BORDER = Component.text("          ", TextColor.color(60, 60, 60))
            .decorate(TextDecoration.STRIKETHROUGH)
            .append(Component.text("           ", NamedTextColor.DARK_GRAY).decorate(TextDecoration.STRIKETHROUGH))
            .append(Component.text("          ", TextColor.color(60, 60, 60)).decorate(TextDecoration.STRIKETHROUGH));

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!(sender instanceof Player))
            return true;

        Map<String, Integer> playersIceCount = new HashMap<>();

        Bukkit.getOnlinePlayers().forEach((player ->
            playersIceCount.put(player.getName(), Statistics.getInt(player, ICE))
        ));

        for (OfflinePlayer player : Bukkit.getOfflinePlayers()) {
            playersIceCount.put(player.getName(), Statistics.getInt(player, ICE));
        }

        List<Map.Entry<String, Integer>> leaderboard = playersIceCount.entrySet().stream()
                .sorted(Comparator.comparingInt(Map.Entry::getValue))
                .toList();

        sender.sendMessage(BORDER);

        for (int i = 0; i < Math.min(10, leaderboard.size()); i++) {
            Map.Entry<String, Integer> entry = leaderboard.get(i);

            if (entry.getKey() == null || entry.getValue() == null)
                break;

            sender.sendMessage(Component.text("#" + (i + 1) + ".", TextColor.color(63, 208, 212))
                    .append(Component.text(" " + entry.getKey() + ": ", NamedTextColor.WHITE))
                    .append(Component.text(entry.getValue(), TextColor.color(63, 208, 212))));
        }

        sender.sendMessage(BORDER);

        return true;
    }
}
