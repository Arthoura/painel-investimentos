package aplicacao.proj.rest.dto.historicoSimulacoes;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Schema(description = "Resumo de uma simulação de investimento realizada por um cliente")
public class SimulacaoResumoDTO {

    @Schema(description = "Identificador único da simulação", example = "1001")
    @NotNull
    private Integer id;

    @Schema(description = "ID do cliente que realizou a simulação", example = "42")
    @NotNull
    private Integer clienteId;

    @Schema(description = "Nome do produto simulado", example = "CDB Banco XP 120% CDI")
    @NotNull
    @Size(min = 2, max = 100)
    private String produto;

    @Schema(description = "Valor investido na simulação", example = "5000.00")
    @NotNull
    @DecimalMin(value = "0.01", message = "O valor investido deve ser maior que zero")
    private BigDecimal valorInvestido;

    @Schema(description = "Valor final estimado ao fim da simulação", example = "5800.75")
    @NotNull
    @DecimalMin(value = "0.01", message = "O valor final deve ser maior que zero")
    private BigDecimal valorFinal;

    @Schema(description = "Prazo da simulação em meses", example = "12")
    @NotNull
    @Min(value = 1, message = "O prazo deve ser de pelo menos 1 mês")
    private Integer prazoMeses;

    @Schema(description = "Data e hora em que a simulação foi realizada", example = "2025-11-20T18:45:00")
    @NotNull
    private LocalDateTime dataSimulacao;
}