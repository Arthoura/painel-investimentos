package aplicacao.proj.rest.dto.historicoSimulacoes;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class SimulacaoResumoDTO {

    private Integer id;
    private Integer clienteId;
    private String produto;
    private BigDecimal valorInvestido;
    private BigDecimal valorFinal;
    private Integer prazoMeses;
    private LocalDateTime dataSimulacao;

}