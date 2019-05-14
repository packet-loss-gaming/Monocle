package gg.packetloss.monocle.coremod.asm.mixin;

import com.google.common.collect.Maps;
import gg.packetloss.monocle.item.CustomItemTexturing;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelLoader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

@Mixin(value = ModelLoader.class, remap = false)
public abstract class MixinModelLoader {
  @Shadow
  private Map<ModelResourceLocation, IModel> stateModels = Maps.newHashMap();

  @Shadow
  public abstract IModel getModel(ResourceLocation location) throws IOException;

  @Shadow
  public abstract IModel getMissingModel();

  @Inject(method = "loadItems", at = @At("HEAD"))
  private void onloadItems(CallbackInfo ci) {
    for (ResourceLocation loc : CustomItemTexturing.inst().getRegisteredModelOverrides()) {
      ModelResourceLocation memory = new ModelResourceLocation(loc, "inventory");
      try {
        IModel model = getModel(loc);
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
