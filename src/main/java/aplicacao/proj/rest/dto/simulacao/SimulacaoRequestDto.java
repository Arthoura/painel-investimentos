package aplicacao.proj.rest.dto.simulacao;

public record SimulacaoRequestDto(
        Long clienteId,
        Double valor,
        Integer prazoMeses,
        String tipoProduto
) {}