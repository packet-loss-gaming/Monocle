package gg.packetloss.monocle.item;

import com.google.gson.Gson;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class CustomItemTexturing {
  private static CustomItemTexturing inst;

  public static CustomItemTexturing inst() {
    return inst;
  }

  private Map<String, ResourceLocation> modelResourceLocationMap = new java.util.HashMap<String, ResourceLocation>();

  public CustomItemTexturing() {
    inst = this;
  }

  private void registerTextureOverride(String customItemName) {
    modelResourceLocationMap.put(customItemName, new ResourceLocation("monocle", "item/" + customItemName));

    if (customItemName.contains("bow")) {
      for (int i = 0; i < 3; ++i) {
        String animationName = customItemName + "_pulling_" + i;
        modelResourceLocationMap.put(animationName, new ResourceLocation("monocle", "item/" + animationName));
      }
    }
  }

  public ResourceLocation getOverridenModel(String customItemName) {
    return modelResourceLocationMap.get(customItemName);
  }

  public Collection<ResourceLocation> getRegisteredModelOverrides() {
    return modelResourceLocationMap.values();
  }

  private static class ItemsModel {
    public List<String> names;
  }

  public void exec() {
    try {
      URL url = new URL("http://service.packetloss.gg/monocle/items.json");
      ItemsModel items = new Gson().fromJson(IOUtils.toString(url), ItemsModel.class);
      items.names.forEach(this::registerTextureOverride);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
