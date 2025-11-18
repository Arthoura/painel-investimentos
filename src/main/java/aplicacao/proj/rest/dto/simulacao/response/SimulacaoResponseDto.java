package aplicacao.proj.rest.dto.simulacao.response;

import java.time.LocalDateTime;

public class SimulacaoResponseDto {

    private ProdutoValidadoDTO produtoValidado;
    private ResultadoSimulacaoDTO resultadoSimulacao;
    private LocalDateTime dataSimulacao;

    // Getters e Setters
    public ProdutoValidadoDTO getProdutoValidado() {
        return produtoValidado;
    }

    public void setProdutoValidado(ProdutoValidadoDTO produtoValidado) {
        this.produtoValidado = produtoValidado;
    }

    public ResultadoSimulacaoDTO getResultadoSimulacao() {
        return resultadoSimulacao;
    }

    public void setResultadoSimulacao(ResultadoSimulacaoDTO resultadoSimulacao) {
        this.resultadoSimulacao = resultadoSimulacao;
    }

    public LocalDateTime getDataSimulacao() {
        return dataSimulacao;
    }

    public void setDataSimulacao(LocalDateTime dataSimulacao) {
        this.dataSimulacao = dataSimulacao;
    }
}