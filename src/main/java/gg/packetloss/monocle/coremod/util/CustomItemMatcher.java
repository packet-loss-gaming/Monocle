package gg.packetloss.monocle.coremod.util;

import gg.packetloss.monocle.item.CustomItemNameSanitizer;
import net.minecraft.item.ItemStack;
import org.apache.commons.lang3.Validate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomItemMatcher {
    private static final Pattern ITEM_PATTERN = Pattern.compile("§.(.+)");

    private final Matcher matcher;
    private final boolean matched;
    private final String snakeCaseName;

    public CustomItemMatcher(ItemStack stack) {
        matcher = ITEM_PATTERN.matcher(stack.getDisplayName());
        matched = matcher.find();

        if (matched()) {
            snakeCaseName = CustomItemNameSanitizer.clean(matcher.group(1).toLowerCase().replaceAll(" ", "_"));
        } else {
            snakeCaseName = null;
        }
    }

    public boolean matched() {
        return matched;
    }

    public String getSnakeCaseName() {
        Validate.isTrue(matched());
        return snakeCaseName;
    }
}
