package com.github.jenya705.cubichardcore.listener;

import com.github.jenya705.cubichardcore.CubicHardcore;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class EyeBedrockListener implements Listener {

    private final CubicHardcore plugin;

    public EyeBedrockListener(CubicHardcore plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void interact(PlayerInteractEvent event) {
        if (event.getClickedBlock() != null &&
                event.getClickedBlock().getType() == Material.BEDROCK &&
                event.getClickedBlock().getLocation().getY() >= CubicHardcore.MIN_Y
        ) {
            event.getPlayer().getInventory().addItem(new ItemStack(Material.ENDER_EYE));
            event.getClickedBlock().setType(Material.AIR);
            plugin.respawnBedrock(event.getClickedBlock().getLocation());
        }
    }

}
