package dev.wresni.common.blueprints.tuples;

import dev.wresni.common.utilities.ObjectUtil;
import lombok.Builder;

@Builder
public record Pair<F, S>(
        F first,
        S second
) {
    public static <F, S> Pair<F, S> empty() {
        return Pair.<F, S>builder().build();
    }

    public static <F, S> Pair<F, S> of(F first, S second) {
        return Pair.<F, S>builder()
                .first(first)
                .second(second)
                .build();
    }

    public boolean hasBoth() {
        return !ObjectUtil.anyNull(first, second);
    }
}
