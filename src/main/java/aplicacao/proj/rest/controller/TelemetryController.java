package aplicacao.proj.rest.controller;

import aplicacao.proj.service.TelemetryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@RestController
@RequestMapping("/telemetria")
@Validated
public class TelemetryController {

    private final TelemetryService telemetryService;

    public TelemetryController(TelemetryService telemetryService) {
        this.telemetryService = telemetryService;
    }

    @Operation(summary = "Retorna métricas de telemetria da API", description = "Fornece um resumo de métricas de desempenho para uma data específica.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Métricas de telemetria retornadas com sucesso.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TelemetryResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Formato de data inválido ou parâmetro ausente.")
    })
    @GetMapping(value = "/resumo", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TelemetryResponseDto> getTelemetryData(
            @Parameter(description = "Data de referência para as métricas (formato AAAA-MM-DD)", required = true, example = "2025-07-30")
            @RequestParam("data") LocalDate dataReferencia) {

        TelemetryResponseDto response = telemetryService.getTelemetryData(dataReferencia);
        return ResponseEntity.ok(response);
    }
}