package ru.practicum.ewm;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import ru.practicum.stats.client.StatsClient;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
@RequiredArgsConstructor
public class StatsReportingInterceptor implements HandlerInterceptor {
    private final StatsClient statsClient;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) {

        if (request.getMethod().equals("GET")) {
            statsClient.hit(request);
        }
        return true;
    }
}
