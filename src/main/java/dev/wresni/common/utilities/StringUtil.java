package dev.wresni.common.utilities;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StringUtil {
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private static final String DEFAULT_PASCAL_DELIMITER = "_";
    public static final int LEFT = 0;

    public static boolean isBlank(String val) {
        return val == null || val.trim().isEmpty();
    }

    public static boolean nonBlank(String val) {
        return !isBlank(val);
    }

    public static boolean anyBlank(String... values) {
        for (String val : values)
            if (isBlank(val)) return true;
        return false;
    }

    public static boolean allBlank(String... values) {
        for (String val : values)
            if (nonBlank(val)) return false;
        return true;
    }

    public static boolean isSizeBetween(String value, int min, int max) {
        return nonBlank(value) && value.length() >= min && value.length() <= max;
    }

    public static boolean isSizeBelow(String value, int max) {
        return nonBlank(value) && value.length() <= max;
    }

    public static boolean isSizeBelowOrBlank(String value, int max) {
        return isBlank(value) || value.length() <= max;
    }

    public static boolean isEmail(String value) {
        return nonBlank(value) && value.matches(EMAIL_REGEX);
    }

    private static <T extends Number> T parse(String val, Function<String, T> parser, T defaultValue) {
        if (isBlank(val)) return defaultValue;
        try {
            return parser.apply(val);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static Integer toBoxInteger(String val, Integer defaultValue) {
        return parse(val, Integer::parseInt, defaultValue);
    }

    public static Integer toBoxInteger(String val) {
        return toBoxInteger(val, null);
    }

    public static int toInt(String val) {
        return toBoxInteger(val, 0);
    }

    public static Double toBoxDouble(String val, Double defaultValue) {
        return parse(val, Double::parseDouble, defaultValue);
    }

    public static Double toBoxDouble(String val) {
        return toBoxDouble(val, null);
    }

    public static double toDouble(String val) {
        return toBoxDouble(val, 0D);
    }

    public static Long toBoxLong(String val, Long defaultValue) {
        return parse(val, Long::parseLong, defaultValue);
    }

    public static Long toBoxLong(String val) {
        return toBoxLong(val, null);
    }

    public static long toLong(String val) {
        return toBoxLong(val, 0L);
    }

    public static String pascalCase(String name, String delimiter) {
        String finalDelimiter = isBlank(delimiter) ? DEFAULT_PASCAL_DELIMITER : delimiter.substring(0, 1);
        return isBlank(name) ?
                null :
                Arrays.stream(name.split(finalDelimiter))
                        .map(val -> val.substring(0, 1).toUpperCase() + val.substring(1).toLowerCase())
                        .collect(Collectors.joining(" "));
    }

    public static String pascalCase(String name) {
        return pascalCase(name, DEFAULT_PASCAL_DELIMITER);
    }
}
