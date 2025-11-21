package aplicacao.proj.unit.service;

import aplicacao.proj.domain.entity.Telemetria;
import aplicacao.proj.domain.repository.TelemetriaRepository;
import aplicacao.proj.rest.dto.telemetria.TelemetriaResponseDTO;
import aplicacao.proj.service.TelemetriaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TelemetriaServiceTest {

    @Mock
    private TelemetriaRepository telemetriaRepository;

    @InjectMocks
    private TelemetriaService telemetriaService;

    @Test
    void deveRegistrarChamada() {

        telemetriaService.registrarChamada("/api/teste", 120L);
        telemetriaService.registrarChamada("/api/teste", 80L);


        when(telemetriaRepository.findAll()).thenReturn(List.of());
        TelemetriaResponseDTO response = telemetriaService.obterDadosTelemetria();

        assertEquals(0, response.getServicos().size()); // sem registros no repo
    }

    @Test
    void deveObterDadosTelemetriaComRegistros() {
        Telemetria registro1 = new Telemetria();
        registro1.setServico("/api/simular-investimento");
        registro1.setQuantidadeChamadas(10);
        registro1.setMediaTempoResposta(new BigDecimal("150.7"));
        registro1.setPeriodoInicio(LocalDate.of(2025, 11, 1));
        registro1.setPeriodoFim(LocalDate.of(2025, 11, 10));

        Telemetria registro2 = new Telemetria();
        registro2.setServico("/api/perfil-risco");
        registro2.setQuantidadeChamadas(5);
        registro2.setMediaTempoResposta(new BigDecimal("200.4"));
        registro2.setPeriodoInicio(LocalDate.of(2025, 11, 5));
        registro2.setPeriodoFim(LocalDate.of(2025, 11, 15));

        when(telemetriaRepository.findAll()).thenReturn(List.of(registro1, registro2));


        TelemetriaResponseDTO response = telemetriaService.obterDadosTelemetria();


        assertEquals(2, response.getServicos().size());


        assertEquals("simular-investimento", response.getServicos().get(0).getNome());
        assertEquals(10, response.getServicos().get(0).getQuantidadeChamadas());
        assertEquals(151, response.getServicos().get(0).getMediaTempoRespostaMs()); // arredondado


        assertEquals("perfil-risco", response.getServicos().get(1).getNome());
        assertEquals(5, response.getServicos().get(1).getQuantidadeChamadas());
        assertEquals(200, response.getServicos().get(1).getMediaTempoRespostaMs()); // arredondado

        assertEquals(LocalDate.of(2025, 11, 1), response.getPeriodo().getInicio());
        assertEquals(LocalDate.of(2025, 11, 15), response.getPeriodo().getFim());
    }
}
