package aplicacao.proj.service;

import aplicacao.proj.domain.EndpointData;
import aplicacao.proj.filter.TelemetryFilter;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TelemetryService {

    private final TelemetryFilter telemetryFilter;

    public TelemetryService(TelemetryFilter telemetryFilter) {
        this.telemetryFilter = telemetryFilter;
    }

    public TelemetryResponseDto getTelemetryData(LocalDate dataReferencia) {
        // 1. Filtra os dados de telemetria da data de referência
        List<EndpointData> filteredData = telemetryFilter.getTelemetryDataList().stream()
                .filter(data -> data.getTimestamp().toLocalDate().isEqual(dataReferencia))
                .collect(Collectors.toList());

        // 2. Agrupa os dados por nome do endpoint
        Map<String, List<EndpointData>> groupedByEndpoint = filteredData.stream()
                .collect(Collectors.groupingBy(EndpointData::getEndpointName));

        // 3. Mapeia para os DTOs de métricas
        List<EndpointMetricsDto> metricsList = groupedByEndpoint.entrySet().stream()
                .map(entry -> {
                    String endpointName = entry.getKey();
                    List<EndpointData> requests = entry.getValue();

                    long totalRequests = requests.size();
                    long successfulRequests = requests.stream()
                            .filter(data -> data.getHttpStatus() >= 200 && data.getHttpStatus() < 300)
                            .count();

                    long totalTime = requests.stream()
                            .mapToLong(EndpointData::getExecutionTime)
                            .sum();

                    long minTime = requests.stream()
                            .mapToLong(EndpointData::getExecutionTime)
                            .min().orElse(0L);

                    long maxTime = requests.stream()
                            .mapToLong(EndpointData::getExecutionTime)
                            .max().orElse(0L);

                    long avgTime = totalRequests > 0 ? totalTime / totalRequests : 0L;
                    BigDecimal successRate = totalRequests > 0
                            ? BigDecimal.valueOf(successfulRequests).divide(BigDecimal.valueOf(totalRequests), 2, RoundingMode.HALF_UP)
                            : BigDecimal.ZERO;

                    return new EndpointMetricsDto(
                            endpointName,
                            totalRequests,
                            avgTime,
                            minTime,
                            maxTime,
                            successRate
                    );
                })
                .collect(Collectors.toList());

        return new TelemetryResponseDto(dataReferencia, metricsList);
    }
}