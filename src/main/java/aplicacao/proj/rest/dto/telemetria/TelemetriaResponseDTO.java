package aplicacao.proj.rest.dto.telemetria;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Schema(description = "Resposta contendo dados de telemetria dos serviços da API")
public class TelemetriaResponseDTO {

    @Schema(description = "Lista de serviços monitorados com suas métricas de desempenho")
    @NotNull
    @Size(min = 1, message = "A lista de serviços não pode estar vazia")
    private List<ServicoDesempenhoDTO> servicos;

    @Schema(description = "Período de tempo referente aos dados de telemetria")
    @NotNull
    private PeriodoDTO periodo;
}