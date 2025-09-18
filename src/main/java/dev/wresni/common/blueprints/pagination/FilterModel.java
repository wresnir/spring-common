package dev.wresni.common.blueprints.pagination;

import com.querydsl.core.types.Predicate;

import java.util.Objects;

public abstract class FilterModel {
    public abstract Predicate build();
    public boolean isEmpty() {
        return Objects.isNull(this.build());
    }
}
