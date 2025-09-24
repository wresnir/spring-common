package dev.wresni.common.utilities;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UuidUtil {
    private static final String HEXADECIMAL_REGEX = "\\p{XDigit}+";

    public static UUID zero() {
        return UUID.fromString("00000000-0000-0000-0000-000000000000");
    }

    public static String toHex(UUID uuid) {
        return Objects.isNull(uuid) ? null : uuid.toString().replaceAll("-", "").toUpperCase();
    }

    public static UUID fromHex(String hex) {
        if (StringUtil.isBlank(hex) || hex.length() != 32 || !hex.matches(HEXADECIMAL_REGEX)) return null;
        String key1 = hex.substring(0, 8);
        String key2 = hex.substring(8, 12);
        String key3 = hex.substring(12, 16);
        String key4 = hex.substring(16, 20);
        String key5 = hex.substring(20, 32);

        return UUID.fromString(String.join("-", key1, key2, key3, key4, key5));
    }

    public static UUID fromString(String string) {
        try {
            return UUID.fromString(string);
        } catch (Exception e) {
            log.error("Invalid UUID String: {}, with error: {}", string, e.getMessage(), e);
            return null;
        }
    }

    public static Boolean isValid(UUID uuid) {
        // check if not null and uuid zero string
        return uuid != null && !uuid.equals(new UUID(0L, 0L));
    }
}
