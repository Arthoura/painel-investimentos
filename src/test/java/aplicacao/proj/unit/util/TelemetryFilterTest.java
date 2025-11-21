package aplicacao.proj.unit.util;

import aplicacao.proj.domain.EndpointData;
import aplicacao.proj.domain.repository.TelemetriaRepository;
import aplicacao.proj.filter.TelemetriaFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TelemetryFilterTest {

    private final TelemetriaRepository telemetriaRepository = mock(TelemetriaRepository.class);
    private final TelemetriaFilter telemetryFilter = new TelemetriaFilter(telemetriaRepository);


    @Test
    void deveRegistrarDadosDeUmaRequisicao() throws ServletException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain filterChain = mock(FilterChain.class);

        when(request.getRequestURI()).thenReturn("/api/teste");
        when(response.getStatus()).thenReturn(200);

        telemetryFilter.doFilterInternal(request, response, filterChain);

        List<EndpointData> dados = telemetryFilter.getTelemetryDataList();
        assertFalse(dados.isEmpty());

        EndpointData data = dados.get(0);
        assertEquals("/api/teste", data.getEndpointName());
        assertEquals(200, data.getHttpStatus());

        assertFalse(data.getExecutionTime() < 0);
    }

    @Test
    void deveRegistrarMultiplasChamadas() throws ServletException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain filterChain = mock(FilterChain.class);

        when(request.getRequestURI()).thenReturn("/api/teste");
        when(response.getStatus()).thenReturn(201);

        telemetryFilter.doFilterInternal(request, response, filterChain);
        telemetryFilter.doFilterInternal(request, response, filterChain);

        List<EndpointData> dados = telemetryFilter.getTelemetryDataList();
        assertEquals(2, dados.size());
        assertEquals("/api/teste", dados.get(1).getEndpointName());
        assertEquals(201, dados.get(1).getHttpStatus());
    }
}
