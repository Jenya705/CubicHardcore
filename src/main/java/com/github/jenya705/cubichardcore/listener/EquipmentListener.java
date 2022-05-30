package com.github.jenya705.cubichardcore.listener;

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class EquipmentListener implements Listener {

    private static final String ATTRIBUTE_NAME = "equipment-armor-slow-attribute";

    public static final double IRON_MODIFIER = 0.25;
    public static final double DIAMOND_MODIFIER = 0.5;
    public static final double NETHERITE_MODIFIER = 0.75;

    private static final Map<EquipmentSlot, List<Material>> EQUIPMENT_SLOTS = Map.of(
            EquipmentSlot.HEAD, List.of(Material.IRON_HELMET, Material.DIAMOND_HELMET, Material.NETHERITE_HELMET),
            EquipmentSlot.CHEST, List.of(Material.IRON_CHESTPLATE, Material.DIAMOND_CHESTPLATE, Material.NETHERITE_CHESTPLATE),
            EquipmentSlot.LEGS, List.of(Material.IRON_LEGGINGS, Material.DIAMOND_LEGGINGS, Material.NETHERITE_LEGGINGS),
            EquipmentSlot.FEET, List.of(Material.IRON_BOOTS, Material.DIAMOND_BOOTS, Material.NETHERITE_BOOTS)
    );

    private static double getModifier(EquipmentSlot slot, ItemStack item) {
        if (item == null) return 0;
        Material material = item.getType();
        int i = 0;
        for (Material equipmentSlotMaterial : EQUIPMENT_SLOTS.getOrDefault(slot, Collections.emptyList())) {
            if (equipmentSlotMaterial == material) break;
            i++;
        }
        return i == 0 ? IRON_MODIFIER : (i == 1 ? DIAMOND_MODIFIER : (i == 2 ? NETHERITE_MODIFIER : 0));
    }

    @EventHandler
    public void equip(PlayerArmorChangeEvent event) {
        ItemStack helmet = event.getPlayer().getInventory().getHelmet();
        ItemStack chest = event.getPlayer().getInventory().getChestplate();
        ItemStack leggings = event.getPlayer().getInventory().getLeggings();
        ItemStack boots = event.getPlayer().getInventory().getBoots();
        double modifier = getModifier(EquipmentSlot.HEAD, helmet) +
                getModifier(EquipmentSlot.CHEST, chest) +
                getModifier(EquipmentSlot.LEGS, leggings) +
                getModifier(EquipmentSlot.FEET, boots) + 1;
        AttributeInstance attributeInstance = event.getPlayer().getAttribute(Attribute.GENERIC_MOVEMENT_SPEED);
        AttributeModifier attributeModifier = new AttributeModifier(
                ATTRIBUTE_NAME,
                attributeInstance.getBaseValue() / (attributeInstance.getBaseValue() + 1) / Math.pow(modifier, 10),
                AttributeModifier.Operation.MULTIPLY_SCALAR_1
        );
        attributeInstance.getModifiers()
                .stream()
                .filter(it -> it.getName().equals(ATTRIBUTE_NAME))
                .forEach(attributeInstance::removeModifier);
        attributeInstance.addModifier(attributeModifier);
    }

}
