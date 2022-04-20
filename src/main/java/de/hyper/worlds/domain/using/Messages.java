package de.hyper.worlds.domain.using;

public class Messages {

    public static String MINV = "§N§W§M";

    public static String CUSTOM(String... msgs) {
        String r = Language.PREFIX;
        for (String s : msgs) {
            r += s;
        }
        return r;
    }

    public static String SYNTAX(String label, String subCMD, String... values) {
        String r = Language.PREFIX + "§c/" + label + (subCMD == null ? "" : " " + subCMD);
        for (String s : values) {
            r = r + " <" + s + ">";
        }
        return r;
    }
}
