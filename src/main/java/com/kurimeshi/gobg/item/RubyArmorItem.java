package com.kurimeshi.gobg.item;

import com.kurimeshi.gobg.Gobg;
import com.kurimeshi.gobg.utils.SetBonus;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.function.Consumer;

/**
 * 26.1 では ArmorItem という基底クラスが廃止され、防具は「ただの Item」に
 * データコンポーネント (Item.Properties#humanoidArmor) を載せたものになりました。
 * そのため独自のツールチップ表示などが必要な場合は、このように Item を直接継承します。
 * <p>
 * また ArmorItem#getDefaultAttributeModifiers という「装備中の部位だけに効く」仕組みも
 * 廃止方向にあるため、per-piece の Max Health ボーナスとフルセットボーナスの両方を
 * 旧来どおり毎 tick 計算して付け外しする方式にまとめました (下記 RubySetTickHandler)。
 */
public class RubyArmorItem extends Item {

    public RubyArmorItem(Properties properties) {
        super(properties);
    }

    // ツールチップ
    // Item#appendHoverText は 1.21.5 以降 TooltipDisplay を受け取るシグネチャに変更されています
    // (非推奨ですが引き続き動作します。将来的には TooltipProvider の利用が推奨されています)。
    @Override
    public void appendHoverText(
            ItemStack stack,
            TooltipContext context,
            TooltipDisplay displayComponent,
            Consumer<Component> tooltipAdder,
            TooltipFlag flags
    ) {
        super.appendHoverText(stack, context, displayComponent, tooltipAdder, flags);

        int equipped = 0;
        // appendHoverText はツールチップ描画、つまりクライアント側でのみ呼ばれるため
        // 旧コードの level.isClientSide() チェックに相当する処理は不要です。
        Player player = Minecraft.getInstance().player;
        if (player != null) {
            equipped = ArmorSetHelper.countEquippedRubyPieces(player);
        }

        tooltipAdder.accept(Component.empty());
        SetBonus.buildTooltipLines(equipped).forEach(tooltipAdder);
    }

    // フルセットボーナス管理
    // @EventBusSubscriber により NeoForge が自動登録。Gobg.java への追記不要。
    // (26.1 ではイベントの発火バスを NeoForge が自動判定するため bus = ... の指定は不要です)
    @EventBusSubscriber(modid = Gobg.MODID)
    public static class RubySetTickHandler {

        private static final int REGEN_DURATION  = 60;
        private static final int REGEN_AMPLIFIER = 1; // Regeneration II

        // ハート1個 = 2.0 (ハーフハート単位)
        private static final double HEALTH_PER_PIECE = 2.0;
        private static final double FULLSET_HEALTH_BONUS = 2.0;

        // AttributeModifier の識別子は UUID ではなく Identifier (旧 ResourceLocation) になりました。
        private static final Identifier PIECE_HEALTH_ID =
                Identifier.fromNamespaceAndPath(Gobg.MODID, "ruby_piece_health");
        private static final Identifier FULLSET_HEALTH_ID =
                Identifier.fromNamespaceAndPath(Gobg.MODID, "ruby_fullset_health");

        // persistentData キー
        private static final String NBT_REGEN_ACTIVE = "ruby_set_regen";

        @SubscribeEvent
        public static void onPlayerTick(PlayerTickEvent.Post event) {
            Player player = event.getEntity();
            if (player.level().isClientSide()) return;

            CompoundTag tag = player.getPersistentData();
            int equipped = ArmorSetHelper.countEquippedRubyPieces(player);
            boolean isFullSet = equipped >= 4;

            AttributeInstance healthAttr = player.getAttribute(Attributes.MAX_HEALTH);
            if (healthAttr != null) {
                // 部位ごとの MaxHealth (+1ハート x 装備数) を毎tick再計算
                // 旧コードは ArmorItem#getDefaultAttributeModifiers で部位ごとに固定UUIDのモディファイアを
                // 自動付与していましたが、その仕組みが無くなったためここで動的に付け外しします。
                healthAttr.removeModifier(PIECE_HEALTH_ID);
                if (equipped > 0) {
                    healthAttr.addPermanentModifier(new AttributeModifier(
                            PIECE_HEALTH_ID,
                            HEALTH_PER_PIECE * equipped,
                            // ADDITION は ADD_VALUE に名称変更されています
                            AttributeModifier.Operation.ADD_VALUE
                    ));
                }

                // フルセット HP ボーナス (+1ハート) の付け外し
                boolean hasFullSetBonus = healthAttr.getModifier(FULLSET_HEALTH_ID) != null;
                if (isFullSet && !hasFullSetBonus) {
                    healthAttr.addPermanentModifier(new AttributeModifier(
                            FULLSET_HEALTH_ID,
                            FULLSET_HEALTH_BONUS,
                            AttributeModifier.Operation.ADD_VALUE
                    ));
                } else if (!isFullSet && hasFullSetBonus) {
                    healthAttr.removeModifier(FULLSET_HEALTH_ID);
                }

                if (player.getHealth() > healthAttr.getValue()) {
                    player.setHealth((float) healthAttr.getValue());
                }
            }

            // Regeneration II の付け外し
            if (isFullSet) {
                MobEffectInstance current = player.getEffect(MobEffects.REGENERATION);
                if (current == null || current.getDuration() <= 20) {
                    player.addEffect(new MobEffectInstance(
                            MobEffects.REGENERATION,
                            REGEN_DURATION,
                            REGEN_AMPLIFIER,
                            false,  // ambient
                            false   // showParticles
                    ));
                    tag.putBoolean(NBT_REGEN_ACTIVE, true);
                }
            } else {
                // CompoundTag#getBoolean(key) は Optional<Boolean> を返すようになったため、
                // デフォルト値付きの getBooleanOr(key, default) を使います。
                if (tag.getBooleanOr(NBT_REGEN_ACTIVE, false)) {
                    player.removeEffect(MobEffects.REGENERATION);
                    tag.remove(NBT_REGEN_ACTIVE);
                }
            }
        }
    }
}
