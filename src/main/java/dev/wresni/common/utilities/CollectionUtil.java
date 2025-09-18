package dev.wresni.common.utilities;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CollectionUtil {
    public static boolean isBlank(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean nonBlank(Collection<?> collection) {
        return !isBlank(collection);
    }
    public static boolean isBlank(Page<?> page) {
        return page == null || page.isEmpty();
    }

    public static boolean nonBlank(Page<?> page) {
        return !isBlank(page);
    }

    public static boolean isBlank(Map<?,?> map) {
        return map == null || map.isEmpty();
    }

    public static boolean nonBlank(Map<?,?> map) {
        return !isBlank(map);
    }

    public static <T> boolean nonBlank(final T[] arrays) {
        return Objects.nonNull(arrays) && !isBlank(List.of(arrays));
    }

    public static <T> List<T> outerJoin(Collection<T> collection1, Collection<T> collection2) {
        List<T> unique1 = new ArrayList<>(collection1);
        List<T> unique2 = new ArrayList<>(collection2);

        unique1.removeAll(collection2);
        unique2.removeAll(collection1);

        List<T> result = new ArrayList<>(unique1);
        result.addAll(unique2);

        return result;
    }

    public static <T> List<T> emptyIfNull(List<T> list) {
        return list == null ? new ArrayList<>() : new ArrayList<>(list);
    }

    public static <T> void doIfNonBlank(Collection<T> list, Consumer<Collection<T>> consumer) {
        Optional.ofNullable(list)
                .filter(l -> !l.isEmpty())
                .ifPresent(consumer);
    }

    public static boolean anyBlank(Collection<?>... collections) {
        for (Collection<?> collection : collections)
            if (isBlank(collection)) return true;
        return false;
    }

    public static <E, K, V> Map<K, V> toMap(Collection<E> elements, Function<E, K> keyMapper, Function<E, V> valueMapper) {
        return isBlank(elements) ? Collections.emptyMap() : elements.stream().collect(Collectors.toMap(keyMapper, valueMapper, FunctionUtil.keep()));
    }

    public static <K, V> Map<K, V> toMap(Collection<V> elements, Function<V, K> keyMapper) {
        return toMap(elements, keyMapper, FunctionUtil.identity());
    }
}
