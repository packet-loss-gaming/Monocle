package gg.packetloss.monocle.coremod.asm.mixin;

import gg.packetloss.monocle.item.CustomItemTexturing;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.FileNotFoundException;
import java.util.Map;

@Mixin(value = ModelLoader.class, remap = false)
public abstract class MixinModelLoader {
  @Shadow
  private Map<ModelResourceLocation, IModel> stateModels;

  @Shadow
  public abstract IModel getMissingModel();

  @Inject(method = "func_177590_d", at = @At("HEAD"))
  protected void onloadItems(CallbackInfo ci) {
    for (ResourceLocation loc : CustomItemTexturing.inst().getRegisteredModelOverrides()) {
      ModelResourceLocation memory = new ModelResourceLocation(loc, "inventory");
      try {
        IModel model = ModelLoaderRegistry.getModel(loc);
        if (model == null) {
          model = getMissingModel();
        }
        stateModels.put(memory, model);
      } catch (FileNotFoundException ignored) {
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }
  }
}
