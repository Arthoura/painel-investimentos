package aplicacao.proj.rest.dto.historicoProdutoDia;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class ResumoProdutoDiaDto {

    private String produto;
    private LocalDate data;
    private Integer quantidadeSimulacoes;
    private BigDecimal mediaValorFinal;

}