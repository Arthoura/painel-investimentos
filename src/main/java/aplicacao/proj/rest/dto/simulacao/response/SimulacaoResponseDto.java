package aplicacao.proj.rest.dto.simulacao.response;

import aplicacao.proj.rest.dto.produtosRecomendados.ProdutoRecomendadoDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Schema(description = "Resposta da simulação de investimento, contendo o produto validado, resultado e data da simulação")
public class SimulacaoResponseDto {

    @Schema(description = "Produto recomendado validado para o perfil do cliente")
    @NotNull
    private ProdutoRecomendadoDTO produtoValidado;

    @Schema(description = "Resultado da simulação com valores finais e rentabilidade")
    @NotNull
    private ResultadoSimulacaoDTO resultadoSimulacao;

    @Schema(description = "Data e hora em que a simulação foi realizada", example = "2025-11-20T19:15:00")
    @NotNull
    private LocalDateTime dataSimulacao;
}