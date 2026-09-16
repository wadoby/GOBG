package com.kurimeshi.gobg.utils;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

import java.util.ArrayList;
import java.util.List;

/**
 * FullSet Bonus ツールチップの色・テキスト生成。
 * Component / MutableComponent の API はこのバージョンでも変わっていないため、
 * ロジックは元コードのまま (パッケージのみ変更) です。
 *
 * 装備数ごとのカウンター色:
 *   0 → #555555 (グレー)
 *   1 → #883333
 *   2 → #BB4444
 *   3 → #DD6666
 *   4 → #FF2222 (ルビーレッド・フルセット)
 *
 * ボーナステキスト色:
 *   0〜3 → #808080 (非アクティブ)
 *   4    → #FF6666 (アクティブ)
 */
public class SetBonus {

    private SetBonus() {}

    // ── 色 ────────────────────────────────────────────────────────────────────

    private static final int[] COUNTER_COLORS = {
            0x555555, // 0個
            0x883333, // 1個
            0xBB4444, // 2個
            0xDD6666, // 3個
            0xFF2222  // 4個 (フルセット)
    };

    private static final int COLOR_BONUS_ACTIVE   = 0xFF6666;
    private static final int COLOR_BONUS_INACTIVE = 0x808080;
    private static final int COLOR_HEADER_LABEL   = 0xFFD700; // ゴールド

    // ── ツールチップ行生成 ────────────────────────────────────────────────────

    /**
     * ツールチップに追加する Component リストを返す。
     *
     * 例 (equipped=2):
     *   FullSet Bonus (2/4)          ← ゴールド + #BB4444
     *     ▶ Max Health +2            ← グレー
     *     ▶ Grants Regeneration II   ← グレー
     *
     * @param equipped 現在の装備枚数 (0〜4)
     */
    public static List<Component> buildTooltipLines(int equipped) {
        List<Component> lines = new ArrayList<>();

        int counterColor = COUNTER_COLORS[Math.min(equipped, 4)];
        int bonusColor   = (equipped >= 4) ? COLOR_BONUS_ACTIVE : COLOR_BONUS_INACTIVE;

        // ヘッダー行: "FullSet Bonus " + "(X/4)"
        MutableComponent header =
                Component.literal("FullSet Bonus ")
                        .withStyle(s -> s.withColor(COLOR_HEADER_LABEL));
        MutableComponent counter =
                Component.literal("(" + equipped + "/4)")
                        .withStyle(s -> s.withColor(counterColor));
        lines.add(header.append(counter));

        // ボーナス行
        lines.add(bonusLine("Max Health +1 Heart (per piece)", bonusColor));
        lines.add(bonusLine("Full Set: +1 Heart & Regeneration II", bonusColor));

        return lines;
    }

    private static MutableComponent bonusLine(String text, int color) {
        return Component.literal("  \u25B6 " + text)   // ▶ = \u25B6
                .withStyle(s -> s.withColor(color));
    }
}
