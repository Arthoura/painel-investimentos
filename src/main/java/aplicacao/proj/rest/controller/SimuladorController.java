package aplicacao.proj.rest.controller;

import aplicacao.proj.service.SimularService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/simulacao")
@Validated
public class SimuladorController {

    private final SimularService simularService;

    public SimuladorController(SimularService simularService) {
        this.simularService = simularService;
    }

    @Operation(summary = "Processa uma nova simulação de crédito", description = "Calcula e retorna as parcelas nos sistemas SAC e PRICE e persiste o resultado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Simulação criada e processada com sucesso.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GenericMessageDto.class))),
            @ApiResponse(responseCode = "400", description = "Dados da requisição inválidos.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Map.class))),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado para os critérios informados.")
    })
    @RateLimiter(name = "simulacaoRateLimiter", fallbackMethod = "simularFallback")
    @PostMapping(value = "/simular",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> simular(@Valid @RequestBody EnvelopeDto request) {
        ResponseEntity responseDto = simularService.processarSimulacao(request);
        return responseDto;
    }

    @Operation(summary = "Lista todas as simulações paginadas", description = "Retorna uma lista de todas as simulações de crédito paginadas.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de simulações retornada com sucesso.", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = PaginacaoDto.class)))),
            @ApiResponse(responseCode = "204", description = "Não há simulações disponíveis.")
    })
    @RateLimiter(name = "simulacaoRateLimiter", fallbackMethod = "listarSimulacoesFallback")
    @GetMapping(value = "/listar",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PaginacaoDto<SimulacaoResumoDto>>> listarSimulacoes() {
        List<PaginacaoDto<SimulacaoResumoDto>> paginas = simularService.listarSimulacao();

        if (paginas.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(paginas);
    }

    @Operation(summary = "Retorna o resumo das simulações por data", description = "Agrupa e sumariza dados de simulações para uma data específica.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Resumo retornado com sucesso.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SimulacoesPorDataDto.class))),
            @ApiResponse(responseCode = "400", description = "Formato de data inválido.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Map.class)))
    })
    @RateLimiter(name = "simulacaoRateLimiter", fallbackMethod = "getSimulacoesPorDataFallback")
    @GetMapping(value = "/resumo-por-data", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SimulacoesPorDataDto> getSimulacoesPorData(
            @Parameter(description = "Data de referência para o resumo (formato AAAA-MM-DD)", required = true, example = "2025-07-30")
            @RequestParam("data") LocalDate dataReferencia) {

        SimulacoesPorDataDto response = simularService.listarSimulacoesPorData(dataReferencia);
        return ResponseEntity.ok(response);
    }

    private ResponseEntity<Object> simularFallback(
            EnvelopeDto request,
            Throwable t) {
        System.out.println("Rate Limiter ativado. Motivo: " + t.getMessage());
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(new GenericMessageDto("O serviço de simulação está temporariamente indisponível.", LocalDateTime.now()));
    }

    private ResponseEntity<?> listarSimulacoesFallback(Throwable t) {
        System.out.println("Rate Limiter ativado para listarSimulacoes. Motivo: " + t.getMessage());
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                .body(new GenericMessageDto("O serviço de listagem está temporariamente indisponível.", LocalDateTime.now()));
    }

    private ResponseEntity<?> getSimulacoesPorDataFallback(LocalDate dataReferencia, Throwable t) {
        System.out.println("Rate Limiter ativado para getSimulacoesPorData. Motivo: " + t.getMessage());
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                .body(new GenericMessageDto("O serviço de resumo por data está temporariamente indisponível.", LocalDateTime.now()));
    }

}
