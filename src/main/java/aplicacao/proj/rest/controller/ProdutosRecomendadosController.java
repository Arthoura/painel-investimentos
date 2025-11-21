package aplicacao.proj.rest.controller;

import aplicacao.proj.rest.dto.produtosRecomendados.ProdutoRecomendadoDTO;
import aplicacao.proj.service.ProdutoRecomendadoService;
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

@Tag(name = "Produtos Recomendados", description = "Recomendações de produtos com base no perfil de risco do cliente")
@RestController
@RequestMapping("/produtos-recomendados")
public class ProdutosRecomendadosController {

    private final ProdutoRecomendadoService produtoService;

    public ProdutosRecomendadosController(ProdutoRecomendadoService produtoService) {
        this.produtoService = produtoService;
    }

    @Operation(
            summary = "Recomendar produtos por perfil de risco",
            description = "Retorna uma lista de produtos recomendados com base no perfil do investidor: conservador, moderado ou agressivo."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de produtos recomendados retornada com sucesso.",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ProdutoRecomendadoDTO.class))
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Perfil inválido. Os valores aceitos são: conservador, moderado ou agressivo."
            )
    })
    @GetMapping(value = "/{perfil}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProdutoRecomendadoDTO>> recomendarProdutos(
            @Parameter(description = "Perfil do investidor: conservador, moderado ou agressivo", required = true, example = "moderado")
            @PathVariable String perfil
    ) {
        List<ProdutoRecomendadoDTO> recomendados = produtoService.recomendarPorPerfil(perfil);
        return ResponseEntity.ok(recomendados);
    }
}