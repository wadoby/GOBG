package com.kurimeshi.gobg.item;

import com.kurimeshi.gobg.Gobg;
import com.kurimeshi.gobg.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModCreativeTabs {

    // RegistryObject は NeoForge から廃止され、DeferredHolder に置き換わりました。
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Gobg.MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MATERIALS_TAB = CREATIVE_MODE_TABS.register("gobg_materials_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.RUBY.get()))
                    .title(Component.translatable("creativetab.materials.tab"))
                    .displayItems((p_Parameters, pOutput) -> {
                        pOutput.accept(ModItems.RUBY.get());
                        pOutput.accept(ModBlocks.RUBY_BLOCK.get());
                    })
                    .build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> NATURES_TAB = CREATIVE_MODE_TABS.register("gobg_natures_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModBlocks.RUBY_ORE.get()))
                    .title(Component.translatable("creativetab.natures.tab"))
                    .displayItems((p_Parameters, pOutput) -> {
                        pOutput.accept(ModBlocks.DEEPSLATE_RUBY_ORE.get());
                        pOutput.accept(ModBlocks.RUBY_ORE.get());
                    })
                    .build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> WEAPONS_TAB = CREATIVE_MODE_TABS.register("gobg_weapons_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.RUBY_SWORD.get()))
                    .title(Component.translatable("creativetab.weapons.tab"))
                    .displayItems((p_Parameters, pOutput) -> {
                        pOutput.accept(ModItems.RUBY_SWORD.get());
                    })
                    .build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> TOOLS_TAB = CREATIVE_MODE_TABS.register("gobg_tools_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.RUBY_PICKAXE.get()))
                    .title(Component.translatable("creativetab.tools.tab"))
                    .displayItems((p_Parameters, pOutput) -> {
                        pOutput.accept(ModItems.RUBY_AXE.get());
                        pOutput.accept(ModItems.RUBY_PICKAXE.get());
                        pOutput.accept(ModItems.RUBY_SHOVEL.get());
                        pOutput.accept(ModItems.RUBY_HOE.get());
                    })
                    .build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> ARMORS_TAB = CREATIVE_MODE_TABS.register("gobg_armors_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.RUBY_CHESTPLATE.get()))
                    .title(Component.translatable("creativetab.armors.tab"))
                    .displayItems((p_Parameters, pOutput) -> {
                        pOutput.accept(ModItems.RUBY_HELMET.get());
                        pOutput.accept(ModItems.RUBY_CHESTPLATE.get());
                        pOutput.accept(ModItems.RUBY_LEGGINGS.get());
                        pOutput.accept(ModItems.RUBY_BOOTS.get());
                    })
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
