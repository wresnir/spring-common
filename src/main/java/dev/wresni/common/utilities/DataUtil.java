package dev.wresni.common.utilities;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DataUtil {
    public static <I, ID, O, K, L extends Collection<O>> Map<K, O> getDataMap(Collection<I> inputs,
                                                                              Function<I, ID> idFunction,
                                                                              Function<Set<ID>, L> repoFunction,
                                                                              Function<O, K> keyFunction,
                                                                              BinaryOperator<O> mergeFunction) {
        return inputs.stream()
                .map(idFunction)
                .filter(Objects::nonNull)
                .collect(Collectors.collectingAndThen(Collectors.toSet(), repoFunction))
                .stream()
                .collect(Collectors.toMap(keyFunction, Function.identity(), mergeFunction));
    }

    public static <I, ID, O, K, L extends Collection<O>> Map<K, O> getDataMap(Collection<I> inputs,
                                               Function<I, ID> idFunction,
                                               Function<Set<ID>, L> repoFunction,
                                               Function<O, K> keyFunction) {
        return getDataMap(inputs, idFunction, repoFunction, keyFunction, FunctionUtil.keep());
    }

    public static <I, IDS extends Collection<ID>, ID, O, K, L extends Collection<O>> Map<K, O> getFlatDataMap(Collection<I> inputs,
                                                                              Function<I, IDS> idsFunction,
                                                                              Function<Set<ID>, L> repoFunction,
                                                                              Function<O, K> keyFunction) {
        return inputs.stream()
                .map(idsFunction)
                .filter(Objects::nonNull)
                .flatMap(Collection::stream)
                .collect(Collectors.collectingAndThen(Collectors.toSet(), repoFunction))
                .stream()
                .collect(Collectors.toMap(keyFunction, Function.identity(), FunctionUtil.keep()));
    }

    public static <I, ID, O, L extends Collection<O>> L getDataList(Collection<I> inputs,
                                                                              Function<I, ID> idFunction,
                                                                              Function<Set<ID>, L> repoFunction) {
        return inputs.stream()
                .map(idFunction)
                .filter(Objects::nonNull)
                .collect(Collectors.collectingAndThen(Collectors.toSet(), repoFunction));
    }

}
