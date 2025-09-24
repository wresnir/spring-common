package dev.wresni.common.utilities;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CollectionUtil {
    public static <T> boolean isBlank(final T[] arrays) {
        return Objects.isNull(arrays) || arrays.length == 0;
    }

    public static boolean isBlank(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isBlank(Map<?,?> map) {
        return map == null || map.isEmpty();
    }

    public static boolean isBlank(Page<?> page) {
        return page == null || page.isEmpty();
    }

    public static <T> boolean nonBlank(final T[] arrays) {
        return !isBlank(arrays);
    }

    public static boolean nonBlank(Collection<?> collection) {
        return !isBlank(collection);
    }

    public static boolean nonBlank(Page<?> page) {
        return !isBlank(page);
    }

    public static boolean nonBlank(Map<?,?> map) {
        return !isBlank(map);
    }

    public static <T> void doIfNonBlank(Collection<T> list, Consumer<Collection<T>> consumer) {
        Optional.ofNullable(list)
                .filter(l -> !l.isEmpty())
                .ifPresent(consumer);
    }

    public static <E, K, V> Map<K, V> toMap(Collection<E> elements, Function<E, K> keyMapper, Function<E, V> valueMapper, BinaryOperator<V> retainingFunction) {
        return isBlank(elements) ? Collections.emptyMap() : elements.stream().collect(Collectors.toMap(keyMapper, valueMapper, retainingFunction));
    }

    public static <E, K, V> Map<K, V> toMap(Collection<E> elements, Function<E, K> keyMapper, Function<E, V> valueMapper) {
        return toMap(elements,  keyMapper, valueMapper, FunctionUtil.keep());
    }

    public static <K, V> Map<K, V> toMap(Collection<V> elements, Function<V, K> keyMapper) {
        return toMap(elements, keyMapper, FunctionUtil.identity());
    }
}
