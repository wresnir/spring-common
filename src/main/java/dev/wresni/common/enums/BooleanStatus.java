package dev.wresni.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

@Getter
@AllArgsConstructor
public enum BooleanStatus {
    TRUE    (true, -1, "Yes", "Y"),
    FALSE   (false, 0, "No", "N");

    private final Boolean valid;
    private final Integer value;
    private final String name;
    private final String key;

    public static Optional<BooleanStatus> optionalOf(Boolean value) {
        return Arrays.stream(values())
                .filter(val -> val.valid.equals(value))
                .findFirst();
    }

    public static Optional<BooleanStatus> optionalOf(Integer value) {
        return Arrays.stream(values())
                .filter(val -> val.value.equals(value))
                .findFirst();
    }

    public static Optional<BooleanStatus> optionalOf(String name) {
        return Arrays.stream(values())
                .filter(val -> val.name.equalsIgnoreCase(name) || val.key.equalsIgnoreCase(name) || val.name().equalsIgnoreCase(name))
                .findFirst();
    }

    public static BooleanStatus of(Boolean value) {
        return optionalOf(value).orElse(null);
    }

    public static BooleanStatus of(Integer value) {
        return optionalOf(value).orElse(null);
    }

    public static BooleanStatus of(Short value) {
        return optionalOf(Objects.isNull(value) ? null : value.intValue()).orElse(null);
    }

    public static BooleanStatus of(String name) {
        return optionalOf(name).orElse(null);
    }

    public boolean isValid() {
        return valid;
    }

    public Short shortValue() {
        return getValue().shortValue();
    }

    public BooleanStatus negate() {
        return BooleanStatus.of(!valid);
    }
}
