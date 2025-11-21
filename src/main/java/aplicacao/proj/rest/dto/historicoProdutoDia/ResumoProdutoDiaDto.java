package aplicacao.proj.rest.dto.historicoProdutoDia;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@Schema(description = "Resumo diário de simulações agrupadas por produto")
public class ResumoProdutoDiaDto {

    @Schema(description = "Nome do produto simulado", example = "Tesouro Selic 2029")
    @NotNull
    @Size(min = 2, max = 100)
    private String produto;

    @Schema(description = "Data da simulação", example = "2025-11-20")
    @NotNull
    private LocalDate data;

    @Schema(description = "Quantidade de simulações realizadas para o produto nesta data", example = "15")
    @NotNull
    @Min(value = 0)
    private Integer quantidadeSimulacoes;

    @Schema(description = "Valor médio final das simulações para o produto nesta data", example = "10500.75")
    @NotNull
    private BigDecimal mediaValorFinal;
}