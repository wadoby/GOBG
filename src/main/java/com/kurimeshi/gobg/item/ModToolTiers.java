package com.kurimeshi.gobg.item;

import com.kurimeshi.gobg.Gobg;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.level.block.Block;

/**
 * 1.20.1 の Tier / ForgeTier / TierSortingRegistry は廃止され、
 * ToolMaterial という単純な record に置き換わりました (登録不要)。
 * 参考: https://docs.neoforged.net/docs/items/tools/
 */
public final class ModToolTiers {

    private ModToolTiers() {}

    /**
     * ルビー道具では正しくドロップを得られない (=これより弱い扱いになる) ブロックのタグ。
     * data/gobg/tags/block/incorrect_for_ruby_tool.json を作成してください。
     * 例: {"values": ["#minecraft:incorrect_for_iron_tool"], "remove": ["#gobg:needs_ruby_tool"]}
     * <p>
     * 「このツールでないと正しくドロップしない」ブロック側のタグ (旧 NEEDS_RUBY_TOOL 相当) は
     * 引き続き別タグとして gobg:needs_ruby_tool のようなキーで管理し、
     * ModBlocks 側の requiresCorrectToolForDrops() のブロックに登録してください。
     */
    public static final TagKey<Block> INCORRECT_FOR_RUBY_TOOL = TagKey.create(
            Registries.BLOCK,
            Identifier.fromNamespaceAndPath(Gobg.MODID, "incorrect_for_ruby_tool")
    );

    public static final ToolMaterial RUBY = new ToolMaterial(
            // 正しく採掘できないブロックのタグ (旧: ForgeTier の tag 引数)
            INCORRECT_FOR_RUBY_TOOL,
            // 耐久値 (旧: 1000)
            1000,
            // 採掘速度 (旧: 6f)
            6.0f,
            // 素手基準の攻撃力ボーナス (旧: 2.5f)
            2.5f,
            // エンチャント適正値 (旧: 25)
            25,
            // 修理素材タグ (旧: () -> Ingredient.of(ModItems.RUBY.get()))
            ModArmorMaterials.REPAIRS_WITH_RUBY
    );
}
