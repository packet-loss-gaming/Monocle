package gg.packetloss.monocle.item;

public class CustomItemNameSanitizer {
    public static String clean(String name) {
        return name.replaceAll("'s", "");
    }
}
