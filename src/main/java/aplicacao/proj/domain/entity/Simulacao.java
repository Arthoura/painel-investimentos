package aplicacao.proj.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "SIMULACAO", schema = "dbo")
public class Simulacao {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "CLIENT_ID")
    private Integer clientId;

    @ManyToOne
    @JoinColumn(name = "PRODUTO_ID", referencedColumnName = "ID")
    private Produto produto;

    @Column(name = "VALOR_INVESTIDO", precision = 12, scale = 2)
    private BigDecimal valorInvestido;

    @Column(name = "VALOR_FINAL", precision = 12, scale = 2)
    private BigDecimal valorFinal;

    @Column(name = "PRAZO_MESES")
    private Integer prazoMeses;

    @Column(name = "DATA_SIMULACAO")
    private LocalDateTime dataSimulacao;
}