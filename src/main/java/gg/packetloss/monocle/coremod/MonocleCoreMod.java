package gg.packetloss.monocle.coremod;

import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.MixinEnvironment;

import java.util.Map;

@IFMLLoadingPlugin.MCVersion(value = "1.12.2")
public class MonocleCoreMod implements IFMLLoadingPlugin {
  public MonocleCoreMod() {
    MixinBootstrap.init();
    MixinEnvironment.setCompatibilityLevel(MixinEnvironment.CompatibilityLevel.JAVA_8);
    MixinEnvironment env = MixinEnvironment.getDefaultEnvironment();
    env.addConfiguration("mixins.monocle.json");
  }

  @Override
  public String[] getASMTransformerClass() {
    return null;
  }

  @Override
  public String getModContainerClass() {
    return "gg.packetloss.monocle.coremod.MonocleModContainer";
  }

  @Override
  public String getSetupClass() {
    return null;
  }

  @Override
  public void injectData(Map<String, Object> data) {

  }

  @Override
  public String getAccessTransformerClass() {
    return null;
  }
}
