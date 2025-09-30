package dev.wresni.common.utilities;

import java.util.function.Function;

public class ArrayUtil {
    public static <T> T get(T[] array, int index) {
        return array == null || array.length == 0 || index < 0 || index >= array.length ?
                null :
                array[index];
    }

    public static <T, R> R getAndMap(T[] array, int index, Function<? super T, ? extends R> mapper) {
        T obj = get(array, index);
        return ObjectUtil.anyNull(mapper, obj) ?  null : mapper.apply(obj);
    }
}
