package dev.wresni.common.blueprints.pagination;

import dev.wresni.common.utilities.CollectionUtil;
import dev.wresni.common.utilities.ObjectUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PageModel {
    public static final int DEFAULT_PAGE = 0;
    public static final int DEFAULT_PAGE_SIZE = 10;

    private Integer page;
    private Integer pageSize;
    private Collection<SortingModel> sorts;

    public PageModel asc(String fieldName) {
        sorts = CollectionUtil.isBlank(sorts) ? new ArrayList<>() : sorts;
        sorts.add(SortingModel.asc(fieldName));
        return this;
    }

    public PageModel desc(String fieldName) {
        sorts = CollectionUtil.isBlank(sorts) ? new ArrayList<>() : sorts;
        sorts.add(SortingModel.desc(fieldName));
        return this;
    }

    public PageModel sortBy(SortingModel sorting) {
        sorts = CollectionUtil.isBlank(sorts) ? new ArrayList<>() : sorts;
        sorts.add(sorting);
        return this;
    }

    public PageModel sortBy(String fieldName, String direction) {
        return sortBy(SortingModel.of(fieldName, direction));
    }

    public Pageable build() {
        Sort sorting = buildSorting();
        return PageRequest.of(ObjectUtil.orElse(page, DEFAULT_PAGE), ObjectUtil.orElse(pageSize, DEFAULT_PAGE_SIZE), sorting);
    }

    public Sort buildSorting() {
        if (CollectionUtil.isBlank(sorts)) return Sort.unsorted();
        return Sort.by(sorts.stream()
                .map(SortingModel::build)
                .filter(Objects::nonNull)
                .collect(Collectors.toList()));
    }

    public void setDefault(SortingModel... sortingModels) {
        page = ObjectUtil.orElse(page, PageModel.DEFAULT_PAGE);
        pageSize = ObjectUtil.orElse(pageSize, PageModel.DEFAULT_PAGE_SIZE);
        sorts = ObjectUtil.orElse(sorts, new ArrayList<>());

        if (CollectionUtil.nonBlank(sortingModels)) sorts.addAll(Arrays.asList(sortingModels));
    }

    public static PageModel unpaged() {
        return new PageModel();
    }

    public static PageModel of(Integer page, Integer pageSize) {
        return new PageModel(page, pageSize, new ArrayList<>());
    }

    public static PageModel of(Integer page, Integer pageSize, SortingModel... sortings) {
        List<SortingModel> sortingList = new ArrayList<>();
        if (Objects.nonNull(sortings)) sortingList.addAll(Arrays.asList(sortings));

        return new PageModel(page, pageSize, sortingList);
    }
}
