package gg.packetloss.monocle;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

@Mod(modid = MonocleMod.MODID, version = MonocleMod.VERSION)
public class MonocleMod {
    public static final String MODID = "monocle";
    public static final String VERSION = "1.0";
    
    @EventHandler
    public void init(FMLInitializationEvent event) {
    }
}
