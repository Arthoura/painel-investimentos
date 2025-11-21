package aplicacao.proj.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.stereotype.Service;
import aplicacao.proj.domain.entity.Telemetria;
import aplicacao.proj.domain.repository.TelemetriaRepository;
import aplicacao.proj.rest.dto.telemetria.PeriodoDTO;
import aplicacao.proj.rest.dto.telemetria.ServicoDesempenhoDTO;
import aplicacao.proj.rest.dto.telemetria.TelemetriaResponseDTO;

import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TelemetriaService {

    private final Map<String, List<Long>> chamadas = new ConcurrentHashMap<>();
    private final TelemetriaRepository telemetriaRepository;

    public TelemetriaService(TelemetriaRepository telemetriaRepository) {
        this.telemetriaRepository = telemetriaRepository;
    }

    public void registrarChamada(String endpoint, long tempoRespostaMs) {
        chamadas.computeIfAbsent(endpoint, k -> new ArrayList<>()).add(tempoRespostaMs);
    }

    @CircuitBreaker(name = "sqlServerCircuitBreaker", fallbackMethod = "fallbackObterDadosTelemetria")
    public TelemetriaResponseDTO obterDadosTelemetria() {
        List<Telemetria> registros = telemetriaRepository.findAll();

        List<ServicoDesempenhoDTO> servicos = registros.stream()
                .map(t -> {
                    String nome = normalizarNome(t.getServico());
                    int quantidade = t.getQuantidadeChamadas();
                    int media = t.getMediaTempoResposta().setScale(0, RoundingMode.HALF_UP).intValue();
                    return new ServicoDesempenhoDTO(nome, quantidade, media);
                })
                .toList();

        LocalDate inicio = registros.stream()
                .map(Telemetria::getPeriodoInicio)
                .min(LocalDate::compareTo)
                .orElse(LocalDate.now());

        LocalDate fim = registros.stream()
                .map(Telemetria::getPeriodoFim)
                .max(LocalDate::compareTo)
                .orElse(LocalDate.now());

        PeriodoDTO periodo = new PeriodoDTO(inicio, fim);

        return new TelemetriaResponseDTO(servicos, periodo);
    }

    // Fallback chamado quando o circuito está aberto ou ocorre falha
    public TelemetriaResponseDTO fallbackObterDadosTelemetria(Throwable t) {
        throw new RuntimeException("Serviço temporariamente indisponível. Tente novamente mais tarde.");
    }

    private String normalizarNome(String path) {
        if (path.contains("simular-investimento")) return "simular-investimento";
        if (path.contains("perfil-risco")) return "perfil-risco";
        return path;
    }
}