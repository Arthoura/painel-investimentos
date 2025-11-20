package aplicacao.proj.rest.controller;

import aplicacao.proj.rest.dto.historicoInvestimentos.InvestimentoDTO;
import aplicacao.proj.service.InvestimentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/investimentos")
public class InvestimentoController {

    private final InvestimentoService investimentoService;

    public InvestimentoController(InvestimentoService investimentoService) {
        this.investimentoService = investimentoService;
    }

    @Operation(summary = "Histórico de investimentos do cliente", description = "Retorna todos os investimentos realizados por um cliente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Investimentos retornados com sucesso.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = InvestimentoDTO.class))),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado ou sem investimentos.")
    })
    @GetMapping(value = "/{clienteId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<InvestimentoDTO>> listarInvestimentosPorCliente(
            @Parameter(description = "ID do cliente", required = true, example = "1")
            @PathVariable Integer clienteId
    ) {
        List<InvestimentoDTO> investimentos = investimentoService.listarPorCliente(clienteId);
        return ResponseEntity.ok(investimentos);
    }
}