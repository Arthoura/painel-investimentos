package aplicacao.proj.rest.dto.historicoInvestimentos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@Data
@Schema(description = "Representa um investimento realizado por um cliente")
public class InvestimentoDTO {

    @Schema(description = "Identificador único do investimento", example = "101")
    @NotNull
    private Integer id;

    @Schema(description = "Tipo de investimento (ex: CDB, Tesouro Direto, Ações)", example = "Tesouro Direto")
    @NotNull
    @Size(min = 2, max = 50)
    private String tipo;

    @Schema(description = "Valor investido", example = "10000.00")
    @NotNull
    @DecimalMin(value = "0.01", message = "O valor deve ser maior que zero")
    private BigDecimal valor;

    @Schema(description = "Rentabilidade percentual do investimento", example = "12.5")
    @NotNull
    private BigDecimal rentabilidade;

    @Schema(description = "Data em que o investimento foi realizado", example = "2025-10-15")
    @NotNull
    @PastOrPresent(message = "A data não pode estar no futuro")
    private LocalDate data;
}