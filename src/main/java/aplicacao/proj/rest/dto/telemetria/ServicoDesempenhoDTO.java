package aplicacao.proj.rest.dto.telemetria;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Desempenho de um serviço da API, incluindo volume de chamadas e tempo médio de resposta")
public class ServicoDesempenhoDTO {

    @Schema(description = "Nome do serviço monitorado", example = "simular-investimento")
    @NotNull
    @Size(min = 2, max = 100)
    private String nome;

    @Schema(description = "Quantidade total de chamadas ao serviço", example = "128")
    @NotNull
    @Min(value = 0)
    private Integer quantidadeChamadas;

    @Schema(description = "Tempo médio de resposta do serviço em milissegundos", example = "245")
    @NotNull
    @Min(value = 0)
    private Integer mediaTempoRespostaMs;
}