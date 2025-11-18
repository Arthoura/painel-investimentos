package aplicacao.proj.rest.dto.historicoProdutoDia;

import java.time.LocalDate;

public class ResumoProdutoDiaDto {

    private String produto;
    private LocalDate data;
    private Integer quantidadeSimulacoes;
    private Double mediaValorFinal;

    // Getters e Setters
    public String getProduto() {
        return produto;
    }

    public void setProduto(String produto) {
        this.produto = produto;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Integer getQuantidadeSimulacoes() {
        return quantidadeSimulacoes;
    }

    public void setQuantidadeSimulacoes(Integer quantidadeSimulacoes) {
        this.quantidadeSimulacoes = quantidadeSimulacoes;
    }

    public Double getMediaValorFinal() {
        return mediaValorFinal;
    }

    public void setMediaValorFinal(Double mediaValorFinal) {
        this.mediaValorFinal = mediaValorFinal;
    }
}