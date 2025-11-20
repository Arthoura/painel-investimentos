package aplicacao.proj.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "PRODUTO", schema = "dbo")
public class Produto {

    @Id
    @Column(name = "ID")
    private Integer id;

    @Column(name = "NOME")
    private String nome;

    @Column(name = "TIPO")
    private String tipo;

    @Column(name = "RENTABILIDADE", precision = 5, scale = 4)
    private BigDecimal rentabilidade;

    @Column(name = "RISCO")
    private String risco;

    @Column(name = "LIQUIDEZ")
    private String liquidez;
}