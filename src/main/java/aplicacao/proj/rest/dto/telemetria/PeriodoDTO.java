package aplicacao.proj.rest.dto.telemetria;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@Schema(description = "Período de tempo utilizado para filtrar dados de telemetria")
public class PeriodoDTO {

    @Schema(description = "Data de início do período", example = "2025-01-01")
    @NotNull(message = "A data de início é obrigatória")
    @PastOrPresent(message = "A data de início não pode estar no futuro")
    private LocalDate inicio;

    @Schema(description = "Data de fim do período", example = "2025-11-20")
    @NotNull(message = "A data de fim é obrigatória")
    @PastOrPresent(message = "A data de fim não pode estar no futuro")
    private LocalDate fim;
}