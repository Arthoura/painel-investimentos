package aplicacao.proj.filter;

import aplicacao.proj.domain.EndpointData;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class TelemetryFilter extends OncePerRequestFilter {

    private final List<EndpointData> telemetryDataList = Collections.synchronizedList(new ArrayList<>());

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        long startTime = System.currentTimeMillis();
        try {
            filterChain.doFilter(request, response);
        } finally {
            long endTime = System.currentTimeMillis();
            long executionTime = endTime - startTime;


            EndpointData data = new EndpointData();
            data.setEndpointName(request.getRequestURI());
            data.setExecutionTime(executionTime);
            data.setHttpStatus(response.getStatus());
            data.setTimestamp(LocalDateTime.now());

            telemetryDataList.add(data);
        }
    }

    public List<EndpointData> getTelemetryDataList() {
        return telemetryDataList;
    }
}