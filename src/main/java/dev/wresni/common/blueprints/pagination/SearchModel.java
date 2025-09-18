package dev.wresni.common.blueprints.pagination;

import com.querydsl.core.types.Predicate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchModel<F extends FilterModel> {
    private F filter;
    private PageModel paging;

    public boolean isEmpty() {
        return Objects.isNull(filter) || filter.isEmpty();
    }

    public Predicate buildFilter() {
        return Objects.nonNull(filter) ? filter.build() : null;
    }

    public Pageable buildPageable() {
        return Objects.nonNull(paging) ? paging.build() : Pageable.unpaged();
    }

    public Sort buildSorting() {
        return Objects.nonNull(paging) ? paging.buildSorting() : Sort.unsorted();
    }

    public static <T extends FilterModel> SearchModel<T> of(T filter) {
        return new SearchModel<>(filter, PageModel.unpaged());
    }

    public static <T extends FilterModel> SearchModel<T> of(T filter, PageModel paging) {
        return new SearchModel<>(filter, Objects.isNull(paging) ? PageModel.unpaged() : paging);
    }
}
