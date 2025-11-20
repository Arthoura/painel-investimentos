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
@Table(name = "INVESTIMENTO", schema = "dbo")
public class Investimento {

    @Id
    @Column(name = "ID")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "CLIENTE_ID", referencedColumnName = "ID")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "PRODUTO_ID", referencedColumnName = "ID")
    private Produto produto;

    @Column(name = "VALOR", precision = 12, scale = 2)
    private BigDecimal valor;

    @Column(name = "DATA")
    private LocalDateTime data;
}