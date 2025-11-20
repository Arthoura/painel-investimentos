package aplicacao.proj.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TELEMETRIA", schema = "dbo")
public class Telemetria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "SERVICO")
    private String servico;

    @Column(name = "QUANTIDADE_CHAMADAS")
    private Integer quantidadeChamadas;

    @Column(name = "MEDIA_TEMPO_RESPOSTA", precision = 6, scale = 2)
    private BigDecimal mediaTempoResposta;

    @Column(name = "PERIODO_INICIO")
    private LocalDate periodoInicio;

    @Column(name = "PERIODO_FIM")
    private LocalDate periodoFim;
}