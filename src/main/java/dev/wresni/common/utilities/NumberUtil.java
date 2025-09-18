package dev.wresni.common.utilities;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NumberUtil {
    public static final int DECIMAL_SCALING = 4;

    public static BigDecimal minusOne() {
        return BigDecimal.valueOf(-1);
    }

    public static BigDecimal oneHundred() {
        return BigDecimal.valueOf(100);
    }

    public static BigDecimal oneThousand() {
        return BigDecimal.valueOf(1000);
    }

    public static BigDecimal fromPercentageToDecimal(BigDecimal value, int scale) {
        return Objects.isNull(value) ? BigDecimal.ZERO : value.divide(oneHundred(), scale, RoundingMode.HALF_UP);
    }

    public static BigDecimal fromPercentageToDecimal(BigDecimal value) {
        return fromPercentageToDecimal(value, DECIMAL_SCALING);
    }

    public static BigDecimal fromPercentageToDecimal(Double value) {
        BigDecimal finalValue = Objects.isNull(value) ? BigDecimal.ZERO : BigDecimal.valueOf(value);
        return fromPercentageToDecimal(finalValue, DECIMAL_SCALING);
    }

    public static BigDecimal fromDecimalToPercentage(BigDecimal value, Integer scale) {
        BigDecimal multipliedValue = Objects.isNull(value) ? BigDecimal.ZERO : value.multiply(oneHundred());
        return Objects.isNull(scale) ? multipliedValue : multipliedValue.setScale(scale, RoundingMode.HALF_UP);
    }

    public static BigDecimal fromDecimalToPercentage(BigDecimal value) {
        return fromDecimalToPercentage(value, null);
    }

    public static BigDecimal fromDecimalToPercentage(Double value, Integer scale) {
        BigDecimal convertedValue = Objects.isNull(value) ? BigDecimal.ZERO : BigDecimal.valueOf(value);
        return fromDecimalToPercentage(convertedValue, scale);
    }

    public static boolean greaterThan(BigDecimal value, Number comparison) {
        return !ObjectUtil.anyNull(value, comparison) && value.compareTo(BigDecimal.valueOf(comparison.byteValue())) >= 0;
    }

    public static boolean isPositive(Number value) {
        return Objects.nonNull(value) && value.doubleValue() > 0;
    }

    public static boolean isPositiveOrZero(Number value) {
        return Objects.nonNull(value) && value.doubleValue() >= 0;
    }

    public static BigDecimal percent(BigDecimal dividend, BigDecimal divisor) {
        return dividend == null || divisor == null || divisor.equals(BigDecimal.ZERO) ?
                BigDecimal.ZERO :
                dividend.divide(divisor, MathContext.DECIMAL64).multiply(BigDecimal.valueOf(100L));
    }

    public static BigDecimal percent(BigDecimal dividend, BigDecimal divisor, int scale) {
        return dividend == null || divisor == null || divisor.compareTo(BigDecimal.ZERO) == 0 ?
                BigDecimal.ZERO :
                dividend.divide(divisor, MathContext.DECIMAL64).multiply(BigDecimal.valueOf(100L)).setScale(scale, RoundingMode.HALF_UP);
    }

    public static BigDecimal percentDecimal(BigDecimal dividend, BigDecimal divisor, int scale) {
        return dividend == null || divisor == null || divisor.equals(BigDecimal.ZERO) ?
                BigDecimal.ZERO :
                dividend.divide(divisor, scale,  RoundingMode.HALF_UP);
    }

    public static BigDecimal percentDecimal(BigDecimal dividend, BigDecimal divisor) {
        return percentDecimal(dividend, divisor, DECIMAL_SCALING);
    }

    public static BigDecimal positiveOrZero(BigDecimal value) {
        return Objects.isNull(value) || BigDecimal.ZERO.compareTo(value) > 0 ? BigDecimal.ZERO : value;
    }

    public static boolean isBetween(BigDecimal value, BigDecimal min, BigDecimal max) {
        return !ObjectUtil.anyNull(value, min, max) &&
                value.compareTo(min) >= 0 &&
                value.compareTo(max) <= 0;
    }

    public static boolean isBetween(Number value, Number min, Number max) {
        if (ObjectUtil.anyNull(value, min, max)) return false;

        BigDecimal valueDecimal = new BigDecimal(value.toString());
        BigDecimal minDecimal = new BigDecimal(min.toString());
        BigDecimal maxDecimal = new BigDecimal(max.toString());
        return valueDecimal.compareTo(minDecimal) >= 0 &&
                valueDecimal.compareTo(maxDecimal) <= 0;
    }

    public static Optional<Integer> parseIntOptional(String str) {
        try {
            return Optional.of(Integer.parseInt(str));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    public static boolean isValidPercentage(BigDecimal value) {
        return Objects.nonNull(value)
                && value.compareTo(BigDecimal.ZERO) > 0
                && value.compareTo(oneHundred()) <= 0;
    }

    public static boolean gtZero(Number value) {
        return BigDecimal.ZERO.compareTo(new BigDecimal(value.toString())) < 0;
    }

    public static boolean goeZero(Number value) {
        return BigDecimal.ZERO.compareTo(new BigDecimal(value.toString())) <= 0;
    }

    public static boolean ltZero(Number value) {
        return BigDecimal.ZERO.compareTo(new BigDecimal(value.toString())) > 0;
    }

    public static boolean loeZero(Number value) {
        return BigDecimal.ZERO.compareTo(new BigDecimal(value.toString())) >= 0;
    }

    public static boolean eqZero(Number value) {
        return BigDecimal.ZERO.compareTo(new BigDecimal(value.toString())) == 0;
    }

    public static <V extends Number> V doIfOr(V value, Predicate<V> predicate, Function<V, V> function, V defaultValue) {
        return predicate.test(value) ? function.apply(value) : defaultValue;
    }

    public static boolean isGreaterThan(Number value1, Number value2) {
        return !ObjectUtil.anyNull(value1, value2) && new BigDecimal(value1.toString()).compareTo(new BigDecimal(value2.toString())) > 0;
    }

    public static boolean isGreaterThanEquals(Number value1, Number value2) {
        return !ObjectUtil.anyNull(value1, value2) && new BigDecimal(value1.toString()).compareTo(new BigDecimal(value2.toString())) >= 0;
    }

    public static boolean isLesserThan(Number value1, Number value2) {
        return !ObjectUtil.anyNull(value1, value2) && new BigDecimal(value1.toString()).compareTo(new BigDecimal(value2.toString())) < 0;
    }

    public static boolean isLesserThanEquals(Number value1, Number value2) {
        return !ObjectUtil.anyNull(value1, value2) && new BigDecimal(value1.toString()).compareTo(new BigDecimal(value2.toString())) <= 0;
    }

    public static BigDecimal sum(Number... values) {
        return values == null ?
                BigDecimal.ZERO :
                Arrays.stream(values)
                        .map(Number::toString)
                        .map(BigDecimal::new)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public static <X extends Number> BigDecimal sum(Collection<X> values) {
        return CollectionUtil.isBlank(values) ? BigDecimal.ZERO : sum(values.toArray(new Number[0]));
    }
}
