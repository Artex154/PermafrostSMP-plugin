package be.artex.ice_collection;

import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;

@SuppressWarnings("DataFlowIssue")
public class Statistics {
    public static final NamespacedKey ICE_COLLECTION = new NamespacedKey(IceCollection.instance, "ice_collection");

    public static int getInt(Player player, NamespacedKey key) {
        return player.getPersistentDataContainer().get(key, PersistentDataType.INTEGER);
    }

    public static int getInt(OfflinePlayer player, NamespacedKey key) {
        return player.getPersistentDataContainer().get(key, PersistentDataType.INTEGER);
    }

    public static int setInt(Player player, NamespacedKey key, int i) {
        player.getPersistentDataContainer().set(key, PersistentDataType.INTEGER, i);

        return i;
    }

    public static int addInt(Player player, NamespacedKey key, int i) {
        int m = getInt(player, key) + i;

        setInt(player, key, m);

        return m;
    }


    public static int removeInt(Player player, NamespacedKey key, int i) {
        int m = getInt(player, key) - i;

        setInt(player, key, m);

        return m;
    }

    public static int playerHoursPlaytime(Player player) {
        return player.getStatistic(Statistic.TOTAL_WORLD_TIME) / (20 * 60 * 60);
    }
}
