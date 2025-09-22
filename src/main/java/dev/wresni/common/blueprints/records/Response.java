package dev.wresni.common.blueprints.records;

import lombok.Builder;
import org.springframework.http.HttpStatus;

import java.util.Collection;
import java.util.Collections;

@Builder
public record Response<T>(
        Boolean success,
        Integer code,
        String message,
        T data,
        Collection<String> errors
) {
    public static <T> Response<T> of(HttpStatus status, T data, Collection<String> errors) {
        return Response.<T>builder()
                .success(!status.isError())
                .code(status.value())
                .message(status.getReasonPhrase())
                .data(data)
                .errors(errors)
                .build();
    }

    public static <T> Response<T> of(HttpStatus status, T data) {
        return Response.of(status, data, null);
    }

    public static <T> Response<T> error(HttpStatus status, Collection<String> errors) {
        return Response.of(status, null, errors);
    }

    public static <T> Response<T> error(HttpStatus status, String error) {
        return Response.of(status, null, Collections.singletonList(error));
    }

    public static <T> Response<T> ok(T data) {
        return Response.of(HttpStatus.OK, data);
    }

    public static <T> Response<T> created(T data) {
        return Response.of(HttpStatus.CREATED, data);
    }

    public static <T> Response<T> accepted(T data) {
        return Response.of(HttpStatus.ACCEPTED, data);
    }

    public static <T> Response<T> badRequest(Collection<String> errors) {
        return Response.error(HttpStatus.BAD_REQUEST, errors);
    }

    public static <T> Response<T> badRequest(String error) {
        return Response.error(HttpStatus.BAD_REQUEST, Collections.singletonList(error));
    }

    public static <T> Response<T> internalError(Collection<String> errors) {
        return Response.error(HttpStatus.INTERNAL_SERVER_ERROR, errors);
    }

    public static <T> Response<T> internalError(String error) {
        return Response.error(HttpStatus.INTERNAL_SERVER_ERROR, Collections.singletonList(error));
    }
}
