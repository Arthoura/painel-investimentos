package aplicacao.proj.rest.dto.simulacao;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@Schema(description = "Dados necessários para realizar uma simulação de investimento")
public class SimulacaoRequestDto {

    @Schema(description = "ID do cliente que deseja realizar a simulação", example = "42")
    @NotNull
    private Integer clienteId;

    @Schema(description = "Valor a ser investido na simulação", example = "10000.00")
    @NotNull
    @DecimalMin(value = "0.01", message = "O valor deve ser maior que zero")
    private BigDecimal valor;

    @Schema(description = "Prazo do investimento em meses", example = "12")
    @NotNull
    @Min(value = 1, message = "O prazo deve ser de pelo menos 1 mês")
    private Integer prazoMeses;

    @Schema(description = "Tipo de produto financeiro a ser simulado", example = "Tesouro Direto")
    @NotNull
    @Size(min = 2, max = 100)
    private String tipoProduto;
}