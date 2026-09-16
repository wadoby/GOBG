package com.kurimeshi.gobg.item;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

/**
 * プレイヤーが装備中のルビーアーマー枚数を返すユーティリティ。
 * ModItems の DeferredItem を直接参照するため、他パッケージ不要。
 */
public class ArmorSetHelper {

    private ArmorSetHelper() {}

    /**
     * 防具4部位（頭・胸・脚・足）のスロット定義
     */
    private static final EquipmentSlot[] ARMOR_SLOTS = new EquipmentSlot[] {
            EquipmentSlot.HEAD,
            EquipmentSlot.CHEST,
            EquipmentSlot.LEGS,
            EquipmentSlot.FEET
    };

    /**
     * @param player 対象プレイヤー
     * @return 装備中のルビーアーマーの数 (0〜4)
     */
    public static int countEquippedRubyPieces(Player player) {
        if (player == null) return 0;

        int count = 0;
        // 防具スロット（HEAD, CHEST, LEGS, FEET）を直接参照
        for (EquipmentSlot slot : ARMOR_SLOTS) {
            ItemStack stack = player.getItemBySlot(slot);
            if (isRubyArmor(stack)) {
                count++;
            }
        }
        return count;
    }

    private static boolean isRubyArmor(ItemStack stack) {
        if (stack.isEmpty()) return false;

        return stack.is(ModItems.RUBY_HELMET.get())
                || stack.is(ModItems.RUBY_CHESTPLATE.get())
                || stack.is(ModItems.RUBY_LEGGINGS.get())
                || stack.is(ModItems.RUBY_BOOTS.get());
    }
}