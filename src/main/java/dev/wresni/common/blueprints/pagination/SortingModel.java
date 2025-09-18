package dev.wresni.common.blueprints.pagination;

import dev.wresni.common.utilities.StringUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Sort;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SortingModel {
    private String sort;
    private String direction;
    private boolean isIgnoreCase;

    public SortingModel(String sort, String direction) {
        this.sort = sort;
        this.direction = direction;
        this.isIgnoreCase = false;
    }

    public static SortingModel asc(String sort) {
        return new SortingModel(sort, "asc");
    }

    public static SortingModel desc(String sort) {
        return new SortingModel(sort, "desc");
    }

    public static SortingModel of(String sort, String direction, boolean isIgnoreCase) {
        return new SortingModel(sort, direction, isIgnoreCase);
    }

    public static SortingModel of(String sort, String direction) {
        return SortingModel.of(sort, direction, false);
    }

    public static SortingModel ofIgnoreCase(String sort, String direction) {
        return SortingModel.of(sort, direction, true);
    }

    public static SortingModel empty() {
        return SortingModel.of(null, null);
    }

    public static SortingModel singleSort(String sort) {
        return Optional.ofNullable(sort)
                .filter(StringUtil::nonBlank)
                .map(val -> val.split(" "))
                .filter(arr -> arr.length >= 1 && arr.length <= 2)
                .map(arr -> arr.length == 1 ? SortingModel.of(arr[0], "asc") : SortingModel.of(arr[0], arr[1]))
                .orElseGet(SortingModel::empty);
    }

    public static SortingModel ignoreCaseSort(String sort) {
        return Optional.ofNullable(sort)
                .filter(StringUtil::nonBlank)
                .map(val -> val.split(" "))
                .filter(arr -> arr.length >= 1 && arr.length <= 2)
                .map(arr -> arr.length == 1 ? SortingModel.of(arr[0], "asc", true) : SortingModel.of(arr[0], arr[1], true))
                .orElseGet(SortingModel::empty);
    }

    public static SortingModel convert(String sort, Function<String, String> mapper, String defaultSort, String delimiter) {
        SortingModel defaultSortModel = StringUtil.nonBlank(defaultSort) ? SortingModel.singleSort(defaultSort) : SortingModel.empty();

        if (StringUtil.isBlank(sort)) return defaultSortModel;
        String[] splitted = StringUtil.nonBlank(delimiter) && sort.contains(delimiter) ?
                sort.split(delimiter) :
                sort.split(" ");

        if (splitted.length < 1) return defaultSortModel;

        String column = mapper.apply(splitted[0]);
        String direction = splitted.length > 1 ? splitted[1] : "asc";

        return StringUtil.isBlank(column) ? defaultSortModel : SortingModel.of(column, direction);
    }

    public static SortingModel convert(String sort, Function<String, String> mapper, String defaultSort) {
        return convert(sort, mapper, defaultSort, null);
    }

    public static SortingModel convert(String sort, Function<String, String> mapper) {
        return convert(sort, mapper, null);
    }

    public boolean isEmpty() {
        return StringUtil.isBlank(sort);
    }

    public Sort.Order build() {
        if (Objects.isNull(sort) || sort.isEmpty()) return null;
        Sort.Direction direction = Sort.Direction.DESC.name().equalsIgnoreCase(this.direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort.Order order = new Sort.Order(direction, sort);
        return isIgnoreCase ? order.ignoreCase() : order;
    }

    public Sort buildSorting() {
        if (Objects.isNull(sort) || sort.isEmpty()) return Sort.unsorted();
        return Sort.by(build());
    }

    public SortingModel ignoreCase() {
        this.isIgnoreCase = true;
        return this;
    }

    public SortingModel caseSensitive() {
        this.isIgnoreCase = false;
        return this;
    }
}
