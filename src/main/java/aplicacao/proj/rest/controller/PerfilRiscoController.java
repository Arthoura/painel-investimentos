package aplicacao.proj.rest.controller;

import aplicacao.proj.rest.dto.perfilRisco.PerfilRiscoDTO;
import aplicacao.proj.service.PerfilRiscoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Perfil de Risco", description = "Operações relacionadas ao cálculo do perfil de risco do cliente")
@RestController
@RequestMapping("/perfil-risco")
public class PerfilRiscoController {

    private final PerfilRiscoService perfilRiscoService;

    public PerfilRiscoController(PerfilRiscoService perfilRiscoService) {
        this.perfilRiscoService = perfilRiscoService;
    }

    @Operation(
            summary = "Calcular perfil de risco do cliente",
            description = "Retorna o perfil de risco calculado com base nos dados e investimentos do cliente."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Perfil de risco calculado com sucesso.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PerfilRiscoDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Cliente não encontrado."
            )
    })
    @GetMapping("/{clienteId}")
    public ResponseEntity<PerfilRiscoDTO> obterPerfil(
            @Parameter(description = "ID do cliente", required = true, example = "42")
            @PathVariable Integer clienteId
    ) {
        return ResponseEntity.ok(perfilRiscoService.calcularPerfil(clienteId));
    }
}