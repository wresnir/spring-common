package dev.wresni.common.utilities;

import dev.wresni.common.enums.BooleanStatus;

import java.util.Objects;

public class BooleanUtil {
    public static boolean of(Boolean val) {
        return val != null && val;
    }

    public static boolean of(Number val) {
        return val != null && val.longValue() == BooleanStatus.TRUE.getValue();
    }

    public static boolean negatedOf(Number val) {
        return val != null && val.longValue() == BooleanStatus.FALSE.getValue();
    }

    public static boolean negatedOf(Boolean val) {
        return val != null && !val;
    }

    public static boolean isNullOrFalse(Boolean isNewProduct) {
        return Objects.isNull(isNewProduct) || !isNewProduct;
    }

    public static Integer set(BooleanStatus auto){
        return BooleanStatus.TRUE.equals(auto) ? -1 : 0;
    }

    public static String toActive(Boolean isActive) {
        return isActive ? "Active" : "Inactive";
    }
}
