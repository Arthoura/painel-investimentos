package aplicacao.proj.rest.dto.simulacao.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class ResultadoSimulacaoDTO {

    private BigDecimal valorFinal;
    private BigDecimal rentabilidadeEfetiva;
    private Integer prazoMeses;

}
