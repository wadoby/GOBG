package com.kurimeshi.gobg;

import com.kurimeshi.gobg.block.ModBlocks;
import com.kurimeshi.gobg.item.ModCreativeTabs;
import com.kurimeshi.gobg.item.ModItems;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(Gobg.MODID)
public class Gobg {

    public static final String MODID = "gobg";

    public Gobg(IEventBus modEventBus) {
        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModCreativeTabs.register(modEventBus);
    }
}