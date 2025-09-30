package dev.wresni.common.apps.interceptors;

import dev.wresni.common.utilities.StringUtil;
import dev.wresni.common.utilities.UuidUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class MdcInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        UUID requestId = getUuid(request);
        log.debug("Request Id: {}", requestId);
        MDC.put("requestId", requestId.toString());
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        // this empty body
    }

    private UUID getUuid(HttpServletRequest request) {
        String gatewayRequestId = request.getHeader("X-Gateway-Request-Id");
        log.debug("Gateway Request Id: {}", gatewayRequestId);
        if (StringUtil.isBlank(gatewayRequestId)) return UUID.randomUUID();

        UUID requestId = UuidUtil.fromString(gatewayRequestId);
        return Objects.isNull(requestId) ? UUID.randomUUID() : requestId;
    }
}
