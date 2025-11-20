package aplicacao.proj.rest.controller;

import aplicacao.proj.rest.dto.historicoProdutoDia.ResumoProdutoDiaDto;
import aplicacao.proj.rest.dto.historicoSimulacoes.SimulacaoResumoDTO;
import aplicacao.proj.service.SimulacaoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;


@RestController
@RequestMapping("/simulacoes")
public class HistoricoSimulacaoController {

    private final SimulacaoService simulacaoService;

    public HistoricoSimulacaoController(SimulacaoService simulacaoService) {
        this.simulacaoService = simulacaoService;
    }

    @Operation(summary = "Lista o histórico completo de simulações realizadas", description = "Retorna todas as simulações com dados do cliente, produto e valores.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Histórico de simulações retornado com sucesso.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = SimulacaoResumoDTO.class)))
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<SimulacaoResumoDTO>> listarSimulacoes() {
        return ResponseEntity.ok(simulacaoService.listarSimulacoes());
    }

    @Operation(summary = "Agrupa simulações por produto e data", description = "Retorna a quantidade de simulações e média de valor final por produto e dia.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Resumo por produto e dia retornado com sucesso.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResumoProdutoDiaDto.class)))
    })
    @GetMapping(value = "/por-produto-dia", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ResumoProdutoDiaDto>> listarPorProdutoEDia() {
        return ResponseEntity.ok(simulacaoService.agruparPorProdutoEDia());
    }
}
