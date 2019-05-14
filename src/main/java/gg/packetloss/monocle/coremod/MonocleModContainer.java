package gg.packetloss.monocle.coremod;

import com.google.common.collect.Lists;
import gg.packetloss.monocle.item.CustomItemTexturing;
import net.minecraftforge.fml.common.DummyModContainer;
import net.minecraftforge.fml.common.ModMetadata;

public class MonocleModContainer extends DummyModContainer {
  public MonocleModContainer() {
    super(new ModMetadata());

    ModMetadata meta = getMetadata();
    meta.modId = "monocle-core";
    meta.name = "Monocle Core Mod";
    meta.description = "Client side enhancements for the Packet Loss Gaming Minecraft server.";
    meta.version = "1.0";
    meta.authorList = Lists.newArrayList("Dark_Arc");

    new CustomItemTexturing().exec();
  }
}