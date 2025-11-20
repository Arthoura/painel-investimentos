package aplicacao.proj.rest.dto.telemetria;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ServicoDesempenhoDTO {

    private String nome;
    private Integer quantidadeChamadas;
    private Integer mediaTempoRespostaMs;

}