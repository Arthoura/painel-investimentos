package aplicacao.proj.rest.controller;

import aplicacao.proj.rest.dto.historicoProdutoDia.ResumoProdutoDiaDto;
import aplicacao.proj.rest.dto.historicoSimulacoes.SimulacaoResumoDTO;
import aplicacao.proj.service.SimulacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Histórico de Simulações", description = "Endpoints para consulta e agrupamento de simulações realizadas")
@RestController
@RequestMapping("/simulacoes")
public class HistoricoSimulacaoController {

    private final SimulacaoService simulacaoService;

    public HistoricoSimulacaoController(SimulacaoService simulacaoService) {
        this.simulacaoService = simulacaoService;
    }

    @Operation(
            summary = "Listar histórico de simulações",
            description = "Retorna todas as simulações realizadas com dados do cliente, produto e valores."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Histórico de simulações retornado com sucesso.",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = SimulacaoResumoDTO.class))
                    )
            ),
            @ApiResponse(responseCode = "500", description = "Erro interno ao buscar simulações")
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<SimulacaoResumoDTO>> listarSimulacoes() {
        return ResponseEntity.ok(simulacaoService.listarSimulacoes());
    }

    @Operation(
            summary = "Agrupar simulações por produto e data",
            description = "Retorna a quantidade de simulações e a média de valor final agrupadas por produto e dia."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Resumo por produto e dia retornado com sucesso.",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ResumoProdutoDiaDto.class))
                    )
            ),
            @ApiResponse(responseCode = "500", description = "Erro interno ao agrupar simulações")
    })
    @GetMapping(value = "/por-produto-dia", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ResumoProdutoDiaDto>> listarPorProdutoEDia() {
        return ResponseEntity.ok(simulacaoService.agruparPorProdutoEDia());
    }
}