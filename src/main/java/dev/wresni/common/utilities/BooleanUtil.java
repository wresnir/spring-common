package dev.wresni.common.utilities;

import dev.wresni.common.enums.BooleanStatus;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BooleanUtil {
    public static boolean of(Boolean val) {
        return val != null && val;
    }

    public static boolean of(Number val) {
        return val != null && val.longValue() == BooleanStatus.TRUE.getValue();
    }

    public static boolean negatedOf(Boolean val) {
        return !of(val);
    }

    public static boolean negatedOf(Number val) {
        return !of(val);
    }

    public static String toActive(Boolean isActive) {
        return isActive != null && isActive ? "Active" : "Inactive";
    }
}
