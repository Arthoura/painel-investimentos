package aplicacao.proj.rest.dto.historicoInvestimentos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;


@AllArgsConstructor
@Getter
@Setter
public class InvestimentoDTO {

    private Integer id;
    private String tipo;
    private BigDecimal valor;
    private BigDecimal rentabilidade;
    private LocalDate data;

}