package com.kurimeshi.gobg.item;

import com.kurimeshi.gobg.Gobg;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Util;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.EquipmentAssets;

import java.util.EnumMap;

/**
 * 1.20.1 Forge 時代は enum で ArmorMaterial インターフェースを実装していましたが、
 * 26.1 (旧 1.21.2+) では ArmorMaterial はレジストリオブジェクトではなく、
 * アイテムのデータコンポーネント (防御力・靭性・ノックバック耐性・修理タグ・装備アセット) を
 * まとめて保持するだけの record になりました。そのため登録は不要です。
 * <p>
 * 参考: https://docs.neoforged.net/docs/items/armor/
 */
public final class ModArmorMaterials {

    private ModArmorMaterials() {}

    /**
     * 防具の見た目 (レイヤーテクスチャ) を定義する EquipmentClientInfo JSON への参照キー。
     * assets/gobg/equipment/ruby.json を用意する必要があります。
     * (旧バージョンの armor レイヤーテクスチャ .png を持っている場合、そこから
     * EquipmentClientInfo 形式に variable 変換してください。 Armor ページのサンプル JSON を参照。)
     */
    public static final ResourceKey<EquipmentAsset> RUBY_ASSET = ResourceKey.create(
            EquipmentAssets.ROOT_ID,
            Identifier.fromNamespaceAndPath(Gobg.MODID, "ruby")
    );

    /**
     * ルビー防具・ルビー道具の両方の修理素材タグ。
     * data/gobg/tags/item/repairs_with_ruby.json を作成し、
     * {"values": ["gobg:ruby"]} を書いてください。
     */
    public static final TagKey<Item> REPAIRS_WITH_RUBY = TagKey.create(
            Registries.ITEM,
            Identifier.fromNamespaceAndPath(Gobg.MODID, "repairs_with_ruby")
    );

    public static final ArmorMaterial RUBY = new ArmorMaterial(
            // 耐久倍率 (旧: durabilityMultiplier)
            25,
            // 部位ごとの防御力 (旧: slotProtections int[]{2,5,6,2})
            Util.make(new EnumMap<>(ArmorType.class), map -> {
                map.put(ArmorType.BOOTS, 2);
                map.put(ArmorType.LEGGINGS, 5);
                map.put(ArmorType.CHESTPLATE, 6);
                map.put(ArmorType.HELMET, 2);
                // BODY は狼などの動物用防具スロット。人型には使われないが値は必要。
                map.put(ArmorType.BODY, 6);
            }),
            // エンチャント適正値 (旧: enchantmentValue)
            15,
            // 装備音 (旧: sound)
            SoundEvents.ARMOR_EQUIP_DIAMOND,
            // 靭性 (旧: toughness)
            1.0f,
            // ノックバック耐性 (旧: knockbackResistance)
            0.0f,
            // 修理素材タグ (旧: repairIngredient の Supplier<Ingredient>)
            REPAIRS_WITH_RUBY,
            // 見た目アセットのキー
            RUBY_ASSET
    );
}
