package com.kurimeshi.gobg.item;

import com.kurimeshi.gobg.Gobg;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.equipment.ArmorType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Gobg.MODID);

    // --- 通常アイテム ---
    public static final DeferredItem<Item> RUBY = ITEMS.registerSimpleItem("ruby");

    // ★重要: RUBY_BLOCK_ITEM / RUBY_ORE_ITEM などの BlockItem 定義はここには書きません！
    // (ModBlocks 側で自動生成されるため、書くと重複してクラッシュします)

    // --- ツール類 ---
    public static final DeferredItem<Item> RUBY_SWORD = ITEMS.registerItem("ruby_sword",
            props -> new Item(props.sword(ModToolTiers.RUBY, 3, -2.4f)));

    public static final DeferredItem<Item> RUBY_PICKAXE = ITEMS.registerItem("ruby_pickaxe",
            props -> new Item(props.pickaxe(ModToolTiers.RUBY, 2, -2.4f)));

    public static final DeferredItem<Item> RUBY_SHOVEL = ITEMS.registerItem("ruby_shovel",
            props -> new Item(props.shovel(ModToolTiers.RUBY, 1, -2.4f)));

    public static final DeferredItem<Item> RUBY_AXE = ITEMS.registerItem("ruby_axe",
            props -> new Item(props.axe(ModToolTiers.RUBY, 5, -3.2f)));

    public static final DeferredItem<Item> RUBY_HOE = ITEMS.registerItem("ruby_hoe",
            props -> new Item(props.hoe(ModToolTiers.RUBY, -1, -1.4f)));

    // --- 防具類 ---
    public static final DeferredItem<Item> RUBY_HELMET = ITEMS.registerItem("ruby_helmet",
            props -> new RubyArmorItem(props.humanoidArmor(ModArmorMaterials.RUBY, ArmorType.HELMET)));

    public static final DeferredItem<Item> RUBY_CHESTPLATE = ITEMS.registerItem("ruby_chestplate",
            props -> new RubyArmorItem(props.humanoidArmor(ModArmorMaterials.RUBY, ArmorType.CHESTPLATE)));

    public static final DeferredItem<Item> RUBY_LEGGINGS = ITEMS.registerItem("ruby_leggings",
            props -> new RubyArmorItem(props.humanoidArmor(ModArmorMaterials.RUBY, ArmorType.LEGGINGS)));

    public static final DeferredItem<Item> RUBY_BOOTS = ITEMS.registerItem("ruby_boots",
            props -> new RubyArmorItem(props.humanoidArmor(ModArmorMaterials.RUBY, ArmorType.BOOTS)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}