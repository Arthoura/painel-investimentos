package aplicacao.proj.rest.dto.produtosRecomendados;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@Schema(description = "Representa um produto financeiro recomendado com base no perfil de risco do cliente")
public class ProdutoRecomendadoDTO {

    @Schema(description = "Identificador único do produto", example = "301")
    @NotNull
    private Integer id;

    @Schema(description = "Nome do produto financeiro", example = "CDB Banco XP 120% CDI")
    @NotNull
    @Size(min = 2, max = 100)
    private String nome;

    @Schema(description = "Tipo do produto (ex: CDB, Tesouro Direto, Fundo)", example = "CDB")
    @NotNull
    @Size(min = 2, max = 50)
    private String tipo;

    @Schema(description = "Rentabilidade percentual estimada do produto", example = "13.75")
    @NotNull
    @DecimalMin(value = "0.0", inclusive = false, message = "A rentabilidade deve ser maior que zero")
    private BigDecimal rentabilidade;

    @Schema(description = "Nível de risco do produto (ex: baixo, médio, alto)", example = "médio")
    @NotNull
    @Size(min = 3, max = 20)
    private String risco;
}