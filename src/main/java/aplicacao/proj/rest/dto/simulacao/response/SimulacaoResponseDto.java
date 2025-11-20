package aplicacao.proj.rest.dto.simulacao.response;

import aplicacao.proj.rest.dto.produtosRecomendados.ProdutoRecomendadoDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class SimulacaoResponseDto {

    private ProdutoRecomendadoDTO produtoValidado;
    private ResultadoSimulacaoDTO resultadoSimulacao;
    private LocalDateTime dataSimulacao;

}