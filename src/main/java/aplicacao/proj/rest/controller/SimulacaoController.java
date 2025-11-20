package aplicacao.proj.rest.controller;

import aplicacao.proj.rest.dto.simulacao.SimulacaoRequestDto;
import aplicacao.proj.rest.dto.simulacao.response.SimulacaoResponseDto;
import aplicacao.proj.service.SimulacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/simular-investimento")
public class SimulacaoController {

    private final SimulacaoService simulacaoService;

    public SimulacaoController(SimulacaoService simulacaoService) {
        this.simulacaoService = simulacaoService;
    }

    @Operation(summary = "Simula um investimento com base no perfil do cliente e tipo de produto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Simulação realizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    @PostMapping
    public ResponseEntity<SimulacaoResponseDto> simular(@RequestBody SimulacaoRequestDto request) {
        return ResponseEntity.ok(simulacaoService.simularInvestimento(request));
    }
}