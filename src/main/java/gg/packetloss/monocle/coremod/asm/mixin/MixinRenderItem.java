package gg.packetloss.monocle.coremod.asm.mixin;

import gg.packetloss.monocle.coremod.util.CustomItemMatcher;
import gg.packetloss.monocle.item.CustomItemTexturing;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = RenderItem.class)
public abstract class MixinRenderItem {
    @Shadow
    private ItemModelMesher itemModelMesher;

    @Shadow
    protected abstract void renderItemModelTransform(ItemStack stack, IBakedModel model, ItemCameraTransforms.TransformType cameraTransformType);

    @Overwrite
    public void renderItemModelForEntity(ItemStack stack, EntityLivingBase entityToRenderFor, ItemCameraTransforms.TransformType cameraTransformType) {
        if (stack != null && entityToRenderFor != null) {
            IBakedModel ibakedmodel = this.itemModelMesher.getItemModel(stack);

            if (entityToRenderFor instanceof EntityPlayer) {
                EntityPlayer entityplayer = (EntityPlayer)entityToRenderFor;
                Item item = stack.getItem();
                ModelResourceLocation modelresourcelocation = null;
                ModelResourceLocation unmodifiedModelResourceLocation = null;

                if (item == Items.fishing_rod && entityplayer.fishEntity != null) {
                    modelresourcelocation = new ModelResourceLocation("fishing_rod_cast", "inventory");
                } else if (item == Items.bow && entityplayer.getItemInUse() != null) {
                    CustomItemMatcher matcher = new CustomItemMatcher(stack);

                    int i = stack.getMaxItemUseDuration() - entityplayer.getItemInUseCount();

                    if (i >= 18) {
                        unmodifiedModelResourceLocation = new ModelResourceLocation("bow_pulling_2", "inventory");

                        if (matcher.matched()) {
                            String snakeCaseName = matcher.getSnakeCaseName() + "_pulling_2";
                            ResourceLocation overridenLoc = CustomItemTexturing.inst().getOverridenModel(snakeCaseName);
                            modelresourcelocation = new ModelResourceLocation(overridenLoc, "inventory");
                        } else {
                            modelresourcelocation = unmodifiedModelResourceLocation;
                        }
                    } else if (i > 13) {
                        unmodifiedModelResourceLocation = new ModelResourceLocation("bow_pulling_1", "inventory");

                        if (matcher.matched()) {
                            String snakeCaseName = matcher.getSnakeCaseName() + "_pulling_1";
                            ResourceLocation overridenLoc = CustomItemTexturing.inst().getOverridenModel(snakeCaseName);
                            modelresourcelocation = new ModelResourceLocation(overridenLoc, "inventory");
                        } else {
                            modelresourcelocation = unmodifiedModelResourceLocation;
                        }
                    } else if (i > 0) {
                        unmodifiedModelResourceLocation = new ModelResourceLocation("bow_pulling_0", "inventory");

                        if (matcher.matched()) {
                            String snakeCaseName = matcher.getSnakeCaseName() + "_pulling_0";
                            ResourceLocation overridenLoc = CustomItemTexturing.inst().getOverridenModel(snakeCaseName);
                            modelresourcelocation = new ModelResourceLocation(overridenLoc, "inventory");
                        } else {
                            modelresourcelocation = unmodifiedModelResourceLocation;
                        }
                    }
                } else {
                    // TODO: maybe switch to the smart player model / normal smart item model?
                    modelresourcelocation = item.getModel(stack, entityplayer, entityplayer.getItemInUseCount());
                }

                if (modelresourcelocation != null) {
                    ibakedmodel = this.itemModelMesher.getModelManager().getModel(modelresourcelocation);
                    if (ibakedmodel == this.itemModelMesher.getModelManager().getMissingModel()) {
                        ibakedmodel = this.itemModelMesher.getModelManager().getModel(unmodifiedModelResourceLocation);
                    }
                }
            }

            this.renderItemModelTransform(stack, ibakedmodel, cameraTransformType);
        }
    }

}
