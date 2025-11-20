package aplicacao.proj.rest.controller;

import aplicacao.proj.rest.dto.telemetria.TelemetriaResponseDTO;
import aplicacao.proj.service.TelemetriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/telemetria")
public class TelemetriaController {

    private final TelemetriaService telemetriaService;

    public TelemetriaController(TelemetriaService telemetriaService) {
        this.telemetriaService = telemetriaService;
    }

    @Operation(summary = "Retorna dados de telemetria dos serviços", description = "Inclui quantidade de chamadas e tempo médio de resposta por serviço.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dados de telemetria retornados com sucesso.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = TelemetriaResponseDTO.class)))
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('investidor')")
    public ResponseEntity<TelemetriaResponseDTO> obterTelemetria() {
        return ResponseEntity.ok(telemetriaService.obterDadosTelemetria());
    }
}
