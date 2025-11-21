package aplicacao.proj.rest.controller;

import aplicacao.proj.rest.dto.historicoInvestimentos.InvestimentoDTO;
import aplicacao.proj.service.InvestimentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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

@Tag(name = "Investimentos", description = "Operações relacionadas ao histórico de investimentos dos clientes")
@RestController
@RequestMapping("/investimentos")
public class InvestimentoController {

    private final InvestimentoService investimentoService;

    public InvestimentoController(InvestimentoService investimentoService) {
        this.investimentoService = investimentoService;
    }

    @Operation(
            summary = "Listar investimentos de um cliente",
            description = "Retorna todos os investimentos realizados por um cliente específico com base no ID informado."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Investimentos retornados com sucesso.",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = InvestimentoDTO.class))
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Cliente não encontrado ou sem investimentos."
            )
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