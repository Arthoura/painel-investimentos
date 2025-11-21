package aplicacao.proj.rest.controller;

import aplicacao.proj.rest.dto.simulacao.SimulacaoRequestDto;
import aplicacao.proj.rest.dto.simulacao.response.SimulacaoResponseDto;
import aplicacao.proj.service.SimulacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@Tag(name = "Simulação de Investimentos", description = "Endpoints para simular investimentos com base no perfil do cliente e tipo de produto")
@RestController
@RequestMapping("/simular-investimento")
public class SimulacaoController {

    private final SimulacaoService simulacaoService;

    public SimulacaoController(SimulacaoService simulacaoService) {
        this.simulacaoService = simulacaoService;
    }

    @Operation(
            summary = "Simular investimento",
            description = "Realiza uma simulação de investimento com base no perfil do cliente e tipo de produto informado."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Simulação realizada com sucesso.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SimulacaoResponseDto.class)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Dados inválidos."),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado.")
    })
    @PostMapping
    public ResponseEntity<SimulacaoResponseDto> simular(
            @Valid @RequestBody SimulacaoRequestDto request
    ) {
        return ResponseEntity.ok(simulacaoService.simularInvestimento(request));
    }
}