package aplicacao.proj.filter;

import aplicacao.proj.domain.entity.Telemetria;
import aplicacao.proj.domain.repository.TelemetriaRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Optional;

@Component
public class TelemetriaFilter extends OncePerRequestFilter {

    private final TelemetriaRepository telemetriaRepository;

    public TelemetriaFilter(TelemetriaRepository telemetriaRepository) {
        this.telemetriaRepository = telemetriaRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        long inicio = System.currentTimeMillis();

        try {
            filterChain.doFilter(request, response);
        } finally {
            long fim = System.currentTimeMillis();
            long tempoResposta = fim - inicio;

            String servico = request.getRequestURI();
            LocalDate hoje = LocalDate.now();

            Optional<Telemetria> existente = telemetriaRepository.findByServico(servico);

            if (existente.isPresent()) {
                Telemetria t = existente.get();
                int novasChamadas = t.getQuantidadeChamadas() + 1;

                BigDecimal novaMedia = t.getMediaTempoResposta()
                        .multiply(BigDecimal.valueOf(t.getQuantidadeChamadas()))
                        .add(BigDecimal.valueOf(tempoResposta))
                        .divide(BigDecimal.valueOf(novasChamadas), 2, RoundingMode.HALF_UP);

                t.setQuantidadeChamadas(novasChamadas);
                t.setMediaTempoResposta(novaMedia);

                // Atualiza o período
                if (hoje.isBefore(t.getPeriodoInicio())) {
                    t.setPeriodoInicio(hoje);
                }
                if (hoje.isAfter(t.getPeriodoFim())) {
                    t.setPeriodoFim(hoje);
                }

                telemetriaRepository.save(t);
            } else {
                Telemetria nova = new Telemetria();
                nova.setServico(servico);
                nova.setQuantidadeChamadas(1);
                nova.setMediaTempoResposta(BigDecimal.valueOf(tempoResposta));
                nova.setPeriodoInicio(hoje);
                nova.setPeriodoFim(hoje);

                telemetriaRepository.save(nova);
            }
        }
    }
}