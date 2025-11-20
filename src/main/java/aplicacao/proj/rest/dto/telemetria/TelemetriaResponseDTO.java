package aplicacao.proj.rest.dto.telemetria;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
    public class TelemetriaResponseDTO {

        private List<ServicoDesempenhoDTO> servicos;
        private PeriodoDTO periodo;

    }