package be.artex.permafrost.listeners;

import be.artex.permafrost.Permafrost;
import be.artex.permafrost.Statistics;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

import java.util.Map;

public class BlockEvent implements Listener {
    private static final Plugin INSTANCE = Permafrost.instance;
    private static final NamespacedKey ICE = Statistics.ICE_COLLECTION;

    private static final String PLACED_META_DATA_KEY = "placed";
    private static final FixedMetadataValue PLACED_META_DATA_VALUE = new FixedMetadataValue(INSTANCE, true);

    private static final Map<Material, Integer> ICE_VALUES = Map.of(
            Material.ICE, 1,
            Material.PACKED_ICE, 9,
            Material.BLUE_ICE, 81
    );

    public static void markBlockAsPlaced(Block block) {
        if (block.hasMetadata(PLACED_META_DATA_KEY))
            block.removeMetadata(PLACED_META_DATA_KEY, INSTANCE);

        block.setMetadata(PLACED_META_DATA_KEY, PLACED_META_DATA_VALUE);
    }

    public static boolean isMarkedAsPlaced(Block block) {
        return block.getMetadata(PLACED_META_DATA_KEY).stream()
                .filter(metadata -> metadata.getOwningPlugin() == INSTANCE)
                .map(MetadataValue::asBoolean)
                .findFirst()
                .orElse(false);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        Material material = block.getType();

        if (isMarkedAsPlaced(block))
            return;

        Player player = event.getPlayer();
        Integer value = ICE_VALUES.get(material);

        if (value != null)
            Statistics.addInt(player, ICE, value);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        markBlockAsPlaced(event.getBlock());
    }
}
