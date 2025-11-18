package aplicacao.proj.rest.dto.telemetria;

public class ServicoDesempenhoDTO {

    private String nome;
    private Integer quantidadeChamadas;
    private Integer mediaTempoRespostaMs;

    // Getters e Setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getQuantidadeChamadas() {
        return quantidadeChamadas;
    }

    public void setQuantidadeChamadas(Integer quantidadeChamadas) {
        this.quantidadeChamadas = quantidadeChamadas;
    }

    public Integer getMediaTempoRespostaMs() {
        return mediaTempoRespostaMs;
    }

    public void setMediaTempoRespostaMs(Integer mediaTempoRespostaMs) {
        this.mediaTempoRespostaMs = mediaTempoRespostaMs;
    }
}