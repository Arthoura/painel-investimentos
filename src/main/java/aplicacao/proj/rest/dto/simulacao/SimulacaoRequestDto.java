package aplicacao.proj.rest.dto.simulacao;

import java.math.BigDecimal;

public record SimulacaoRequestDto(
        Integer clienteId,
        BigDecimal valor,
        Integer prazoMeses,
        String tipoProduto
) {}