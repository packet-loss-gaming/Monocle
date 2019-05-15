package gg.packetloss.monocle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.ResourcePackRepository;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;

import java.io.File;
import java.lang.reflect.Field;

@Mod(modid = MonocleMod.MODID, version = MonocleMod.VERSION)
public class MonocleMod {
  public static final String MODID = "monocle";
  public static final String VERSION = "1.0";

  @Mod.EventHandler
  public void onPostInit(FMLPostInitializationEvent event) {
    try {
      Field dirServerResourcePacks = ResourcePackRepository.class.getDeclaredField("field_148534_e");
      dirServerResourcePacks.setAccessible(true);

      File serverResourcePackDir = (File) dirServerResourcePacks.get(Minecraft.getMinecraft().getResourcePackRepository());
      serverResourcePackDir.mkdirs();
    } catch (IllegalAccessException | NoSuchFieldException e) {
      e.printStackTrace();
    }
  }
}
