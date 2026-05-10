package be.artex.ice_collection.listeners;

import be.artex.ice_collection.IceCollection;
import be.artex.ice_collection.Statistics;
import io.papermc.paper.math.BlockPosition;
import io.papermc.paper.math.Position;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SuppressWarnings("UnstableApiUsage")
public class BlockEvent implements Listener {
    private static final List<BlockPosition> PLACED_BLOCKS = new ArrayList<>();

    private static final Map<Material, Integer> ICE_VALUES = Map.of(
            Material.ICE, IceCollection.iceValue,
            Material.PACKED_ICE, IceCollection.packedIceValue,
            Material.BLUE_ICE, IceCollection.blueIceValue
    );

    public static void markBlockAsPlaced(Block block) {
        PLACED_BLOCKS.add(Position.block(block.getLocation()));
    }

    public static boolean isMarkedAsPlaced(Block block) {
        return PLACED_BLOCKS.contains(Position.block(block.getLocation()));
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        ItemStack stack = player.getInventory().getItemInMainHand();

        if (stack == null || stack.getType().isAir() || !stack.getItemMeta().hasEnchant(Enchantment.SILK_TOUCH))
            return;

        Block block = event.getBlock();

        if (isMarkedAsPlaced(block))
            return;

        Material material = block.getType();
        Integer value = ICE_VALUES.get(material);

        if (value != null)
            Statistics.addInt(player, Statistics.ICE_COLLECTION, value);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        markBlockAsPlaced(event.getBlock());
    }
}
