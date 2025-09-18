package dev.wresni.common.utilities;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.function.BinaryOperator;
import java.util.function.Function;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FunctionUtil {
    public static <T> BinaryOperator<T> replace() {
        return (oldData, newData) -> newData;
    }
    public static <T> BinaryOperator<T> keep() {
        return (oldData, newData) -> newData;
    }
    public static <T> Function<T, T> identity() {
        return Function.identity();
    }
}
