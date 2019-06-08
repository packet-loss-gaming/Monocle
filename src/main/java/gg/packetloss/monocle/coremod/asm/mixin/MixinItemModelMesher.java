package gg.packetloss.monocle.coremod.asm.mixin;

import gg.packetloss.monocle.coremod.util.CustomItemMatcher;
import gg.packetloss.monocle.item.CustomItemTexturing;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelManager;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Map;

@Mixin(value = ItemModelMesher.class)
public abstract class MixinItemModelMesher {
  @Shadow
  private ModelManager modelManager;
  @Shadow
  private Map<Item, ItemMeshDefinition> shapers;

  @Shadow
  protected abstract int getMetadata(ItemStack p_getMetadata_1_);

  @Shadow
  protected abstract IBakedModel getItemModel(Item p_getItemModel_1_, int p_getItemModel_2_);

  @Overwrite
  public IBakedModel getItemModel(ItemStack stack) {
    CustomItemMatcher matcher = new CustomItemMatcher(stack);

    if (matcher.matched()) {
      String snakeCaseName = matcher.getSnakeCaseName();

      ResourceLocation overridenLoc = CustomItemTexturing.inst().getOverridenModel(snakeCaseName);
      if (overridenLoc != null) {
        ModelResourceLocation overridenModelLoc = new ModelResourceLocation(overridenLoc, "inventory");
        IBakedModel foundModel = modelManager.getModel(overridenModelLoc);

        if (foundModel != modelManager.getMissingModel()) {
          return foundModel;
        }
      }
    }

    // Original method logic follows, @Inject wasn't working properly for some reason.
    Item item = stack.getItem();
    IBakedModel ibakedmodel = this.getItemModel(item, this.getMetadata(stack));

    if (ibakedmodel == null) {
      ItemMeshDefinition itemmeshdefinition = this.shapers.get(item);
      if (itemmeshdefinition != null) {
        ibakedmodel = this.modelManager.getModel(itemmeshdefinition.getModelLocation(stack));
      }
    }

    if (ibakedmodel == null) {
      ibakedmodel = this.modelManager.getMissingModel();
    }

    return ibakedmodel;

  }
}
