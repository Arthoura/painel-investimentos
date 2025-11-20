package aplicacao.proj.rest.dto.produtosRecomendados;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@AllArgsConstructor
@Data
public class ProdutoRecomendadoDTO {

    private Integer id;
    private String nome;
    private String tipo;
    private BigDecimal rentabilidade;
    private String risco;


}