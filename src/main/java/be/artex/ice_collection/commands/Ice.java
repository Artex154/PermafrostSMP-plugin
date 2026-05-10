package be.artex.ice_collection.commands;

import be.artex.ice_collection.Statistics;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Ice implements CommandExecutor {
    private static final NamespacedKey ICE = Statistics.ICE_COLLECTION;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (args.length <= 2)
            return false;

        String playerName = args[0];

        if (Bukkit.getPlayer(playerName) == null)
            return false;

        String amountArg = args[2];
        int amount;

        try {
            amount = Integer.parseInt(amountArg);
        } catch (NumberFormatException e) {
            return false;
        }

        Player player = Bukkit.getPlayer(playerName);

        switch (args[1]) {
            case "add" ->
                sender.sendMessage(Component.text("You added " + amountArg + " ice collection to " + playerName + ". He now has " + Statistics.addInt(player, ICE, amount) + " ice collection."));

            case "remove" ->
                sender.sendMessage(Component.text("You removed " + amountArg + " ice collection from " + playerName + ". He now has " + Statistics.removeInt(player, ICE, amount) + " ice collection."));

            case "set" ->
                sender.sendMessage(Component.text(playerName + " now has " + Statistics.setInt(player, ICE, amount) + " ice collection."));

            default -> {
                return false;
            }
        }

        return true;
    }
}
