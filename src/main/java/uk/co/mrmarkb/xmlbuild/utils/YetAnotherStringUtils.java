package uk.co.mrmarkb.xmlbuild.utils;

public class YetAnotherStringUtils {
    public static boolean isBlank(String value) {
        return isNull(value) || value.trim().isEmpty();
    }

    public static String string(String value) {
        return isNull(value) ? "" : value;
    }

    private static boolean isNull(String value) {
        return value == null;
    }
}
