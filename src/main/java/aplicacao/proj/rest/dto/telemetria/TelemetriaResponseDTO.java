package aplicacao.proj.rest.dto.telemetria;
import java.util.List;
    public class TelemetriaResponseDTO {

        private List<ServicoDesempenhoDTO> servicos;
        private PeriodoDTO periodo;

        // Getters e Setters
        public List<ServicoDesempenhoDTO> getServicos() {
            return servicos;
        }

        public void setServicos(List<ServicoDesempenhoDTO> servicos) {
            this.servicos = servicos;
        }

        public PeriodoDTO getPeriodo() {
            return periodo;
        }

        public void setPeriodo(PeriodoDTO periodo) {
            this.periodo = periodo;
        }
    }