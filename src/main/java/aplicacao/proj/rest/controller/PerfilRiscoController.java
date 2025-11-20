package aplicacao.proj.rest.controller;

import aplicacao.proj.rest.dto.perfilRisco.PerfilRiscoDTO;
import aplicacao.proj.service.PerfilRiscoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/perfil-risco")
public class PerfilRiscoController {

    private final PerfilRiscoService perfilRiscoService;

    public PerfilRiscoController(PerfilRiscoService perfilRiscoService) {
        this.perfilRiscoService = perfilRiscoService;
    }

    @Operation(summary = "Calcula o perfil de risco do cliente com base em seus dados e investimentos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Perfil de risco calculado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    @GetMapping("/{clienteId}")
    public ResponseEntity<PerfilRiscoDTO> obterPerfil(@PathVariable Integer clienteId) {
        return ResponseEntity.ok(perfilRiscoService.calcularPerfil(clienteId));
    }
}