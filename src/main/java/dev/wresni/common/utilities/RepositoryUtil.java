package dev.wresni.common.utilities;

import dev.wresni.common.blueprints.pagination.FilterModel;
import dev.wresni.common.blueprints.pagination.PageModel;
import dev.wresni.common.blueprints.pagination.SearchModel;
import dev.wresni.common.blueprints.tuples.Pair;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RepositoryUtil {
    public static <E, R extends JpaRepository<E, ?> & QuerydslPredicateExecutor<E>, F extends FilterModel> List<E> findAll(R repository, SearchModel<F> filter) {
        Objects.requireNonNull(repository);
        return Optional.of(filter)
                .map(f -> f.isEmpty() ?
                        repository.findAll(f.buildSorting()) :
                        repository.findAll(f.buildFilter(), f.buildSorting()))
                .map(iterable -> StreamSupport.stream(iterable.spliterator(), false).collect(Collectors.toList()))
                .orElseGet(Collections::emptyList);
    }

    public static <E, R extends JpaRepository<E, ?> & QuerydslPredicateExecutor<E>, F extends FilterModel> List<E> findAll(R repository, F filter) {
        Objects.requireNonNull(repository);
        return Optional.of(filter)
                .map(f -> f.isEmpty() ? repository.findAll() : repository.findAll(f.build()))
                .map(iterable -> StreamSupport.stream(iterable.spliterator(), false).collect(Collectors.toList()))
                .orElseGet(Collections::emptyList);
    }

    public static <E, R extends JpaRepository<E, ?> & QuerydslPredicateExecutor<E>, F extends FilterModel> Page<E> findPage(R repository, SearchModel<F> filter) {
        Objects.requireNonNull(repository);
        return Optional.of(filter)
                .map(f -> f.isEmpty() ?
                        repository.findAll(f.buildPageable()) :
                        repository.findAll(f.buildFilter(), f.buildPageable()))
                .orElseGet(Page::empty);
    }

    public static <E, R extends JpaRepository<E, ?> & QuerydslPredicateExecutor<E>, F extends FilterModel> Page<E> findPage(R repository, F filter, PageModel paging) {
        Objects.requireNonNull(repository);
        Objects.requireNonNull(paging);
        return Optional.of(filter)
                .map(f -> f.isEmpty() ?
                        repository.findAll(paging.build()) :
                        repository.findAll(f.build(), paging.build()))
                .orElseGet(Page::empty);
    }

    public static <E, R extends JpaRepository<E, ?> & QuerydslPredicateExecutor<E>, F extends FilterModel> Page<E> findPage(R repository, F filter, Pageable pageable) {
        Objects.requireNonNull(repository);
        Objects.requireNonNull(pageable);
        return Optional.of(filter)
                .map(f -> f.isEmpty() ?
                        repository.findAll(pageable) :
                        repository.findAll(f.build(), pageable))
                .orElseGet(Page::empty);
    }

    public static <E, R extends JpaRepository<E, ?> & QuerydslPredicateExecutor<E>, F extends FilterModel> Page<E> findPage(R repository, org.springframework.data.util.Pair<F, Pageable> pair) {
        Objects.requireNonNull(repository);
        return findPage(repository, pair.getFirst(), pair.getSecond());
    }

    public static <E, R extends JpaRepository<E, ?> & QuerydslPredicateExecutor<E>, F extends FilterModel> Page<E> findPage(R repository, Pair<F, Pageable> pair) {
        Objects.requireNonNull(repository);
        return findPage(repository, pair.first(), pair.second());
    }

    public static <I, E> Map<I, E> findMapByIds(Collection<I> ids, Function<Collection<I>, Collection<E>> repositoryFunction, Function<E, I> idFunction) {
        return CollectionUtil.isBlank(ids) ?
                Collections.emptyMap() :
                repositoryFunction.apply(ids)
                        .stream()
                        .collect(Collectors.toMap(idFunction, entity -> entity));
    }

    public static <S, I, E> Map<I, E> findMapByIds(Collection<S> sources, Function<S, I> sourceMapper, Function<Collection<I>, Collection<E>> repositoryFunction, Function<E, I> idFunction) {

        return CollectionUtil.isBlank(sources) ?
                Collections.emptyMap() :
                findMapByIds(sources.stream().map(sourceMapper).collect(Collectors.toSet()), repositoryFunction, idFunction);
    }

    public static <R extends JpaRepository<E, I> & QuerydslPredicateExecutor<E>, S, I, E> List<E> findAllByIds(Collection<S> sources, Function<S, I> sourceMapper, R repository) {
        return CollectionUtil.isBlank(sources) ?
                Collections.emptyList() :
                repository.findAllById(sources.stream().map(sourceMapper).collect(Collectors.toSet()));
    }
}
