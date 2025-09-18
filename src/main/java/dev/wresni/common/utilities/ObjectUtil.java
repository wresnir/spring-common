package dev.wresni.common.utilities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

@Log4j2
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ObjectUtil {
    public static boolean allNull(Object... objects) {
        for (Object object : objects)
            if (Objects.nonNull(object)) return false;
        return true;
    }

    public static boolean anyNull(Object... objects) {
        for (Object object : objects)
            if (Objects.isNull(object)) return true;
        return false;
    }

    public static <T> T getOrDefault(T obj, T defaultValue) {
        return obj == null ? defaultValue : obj;
    }

    public static <T> String toString(T obj) {
        return obj == null ? null : obj.toString();
    }

    public static <T> void setIfNotNull(T val, Consumer<T> setter) {
        if (val == null) return;
        setter.accept(val);
    }

    public static <T> void setIfValid(T value, Predicate<T> validator, Consumer<T> setter) {
        if (anyNull(validator, value)) return;
        setter.accept(value);
    }

    public static <T, R> void setAfterMap(T obj, Function<T,R> func, Consumer<R> setter) {
        if (obj == null) return;
        R value = func.apply(obj);

        if (value == null) return;
        setter.accept(value);
    }

    public static <T, R> void setIfValidAfterMap(T obj, Predicate<T> validator, Function<T,R> func, Consumer<R> setter) {
        if (obj == null) return;
        R value = func.apply(obj);

        if (value == null) return;
        setter.accept(value);
    }

    public static <T> boolean validate(T obj, Predicate<T> validator, Collection<String> error, String errorMessage) {
        if (validator.test(obj)) return true;
        error.add(errorMessage);
        return false;
    }

    public static <T> boolean validate(T obj, Predicate<T> validator, Collection<String> error, String format, Object... values) {
        if (validator.test(obj)) return true;
        error.add(String.format(format, values));
        return false;
    }

    public static <T> boolean validateIfNotNull(T obj, Predicate<T> validator, Collection<String> error, String errorMessage) {
        if (Objects.isNull(obj)) return true;
        return validate(obj, validator, error, errorMessage);
    }

    public static boolean isValidNumber(Number value, double lowerbound, double upperBound) {
        return Objects.nonNull(value) && value.doubleValue() >= lowerbound && value.doubleValue() <= upperBound;
    }

    public static boolean isValidPercentage(Number value) {
        return isValidNumber(value, 0d, 100d);
    }

    public static boolean isValidPercentageDecimal(Number value) {
        return isValidNumber(value, 0d, 1d);
    }

    public static <T> T clone(T source, Class<T> targetClass) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        if (Objects.isNull(source)) return null;
        T target = targetClass.getDeclaredConstructor().newInstance();
        BeanUtils.copyProperties(source, target);
        return target;
    }

    @SuppressWarnings("unchecked")
    public static <T> T clone(T source) throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        return clone(source, (Class<T>) source.getClass());
    }

    public static <T> T cloneNullable(T source) {
        try {
            return clone(source);
        } catch (Exception e) {
            return null;
        }
    }

    public static <T> T cloneOrThrow(T source, Supplier<? extends RuntimeException> exceptionSupplier) {
        try {
            return clone(source);
        } catch (Exception e) {
            throw exceptionSupplier.get();
        }
    }

    public static <T> T orElse(T value, T defaultValue) {
        return value == null ? defaultValue : value;
    }

    public static <T> Optional<T> cloneOptional(T source, Class<T> targetClass) {
        try {
            return Optional.of(cloneOpt(source, targetClass));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Optional.empty();
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> Optional<T> cloneOptional(T source) {
        return cloneOptional(source, (Class<T>) source.getClass());
    }

    public static <T> T cloneOpt(T source, Class<T> targetClass) throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        T target = targetClass.getDeclaredConstructor().newInstance();
        BeanUtils.copyProperties(source, target);
        return target;
    }

    public static <T> Optional<T> readJson(byte[] jsonData, ObjectMapper mapper, Class<T> targetClass) {
        try {
            return Optional.ofNullable(mapper.readValue(jsonData, targetClass));
        } catch (IOException e) {
            return Optional.empty();
        }
    }

    public static <T> Optional<T> readJson(String jsonString, ObjectMapper mapper, Class<T> targetClass) {
        try {
            return Optional.ofNullable(mapper.readValue(jsonString, targetClass));
        } catch (JsonProcessingException e) {
            return Optional.empty();
        }
    }

    public static  <T, U> U mapOr(T source, Function<T, U> function, U defaultValue) {
        return Optional.ofNullable(source).map(function).orElse(defaultValue);
    }

    public static  <T, U> U mapOrGet(T source, Function<T, U> function, Supplier<U> supplier) {
        return mapOr(source, function, Objects.isNull(supplier) ? null : supplier.get());
    }

    public static  <T, U> U map(T source, Function<T, U> function) {
        return mapOr(source, function, null);
    }

    public static <T, R> boolean isNull(T obj, Supplier<R> supplier) {
        return obj == null || supplier.get() == null;
    }

    public static <T> void ifPresent(T obj, Consumer<T> consumer) {
        Optional.ofNullable(obj).ifPresent(consumer);
    }
}
