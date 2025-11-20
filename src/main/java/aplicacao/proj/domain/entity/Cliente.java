package aplicacao.proj.domain.entity;


import aplicacao.proj.Enums.PreferenciaLiquidezRentabilidade;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "CLIENTE", schema = "dbo")
public class Cliente {

    @Id
    @Column(name = "ID")
    private Integer id;

    @Column(name = "NOME")
    private String nome;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "VOLUME", precision = 12, scale = 2)
    private BigDecimal volume;

    @Column(name = "FREQUENCIA_MOVIMENTACOES_MENSAL")
    private Integer frequenciaMovimentacoesMensal;

    @Enumerated(EnumType.STRING)
    @Column(name = "PREFERENCIA_LIQUIDEZ_RENTABILIDADE")
    private PreferenciaLiquidezRentabilidade preferenciaLiquidezRentabilidade;
}