package aplicacao.proj.rest.dto.simulacao.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@Schema(description = "Resultado da simulação de investimento")
public class ResultadoSimulacaoDTO {

    @Schema(description = "Valor final estimado ao fim do investimento", example = "12500.00")
    @NotNull
    @DecimalMin(value = "0.01", message = "O valor final deve ser maior que zero")
    private BigDecimal valorFinal;

    @Schema(description = "Rentabilidade efetiva percentual obtida na simulação", example = "12.5")
    @NotNull
    @DecimalMin(value = "0.0", inclusive = false, message = "A rentabilidade deve ser maior que zero")
    private BigDecimal rentabilidadeEfetiva;

    @Schema(description = "Prazo do investimento em meses", example = "24")
    @NotNull
    @Min(value = 1, message = "O prazo deve ser de pelo menos 1 mês")
    private Integer prazoMeses;
}