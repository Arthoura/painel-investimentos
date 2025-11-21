package aplicacao.proj.unit.service;

import aplicacao.proj.domain.entity.Investimento;
import aplicacao.proj.domain.entity.Produto;
import aplicacao.proj.domain.repository.InvestimentoRepository;
import aplicacao.proj.rest.dto.historicoInvestimentos.InvestimentoDTO;
import aplicacao.proj.service.InvestimentoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InvestimentoServiceTest {

    @Mock
    private InvestimentoRepository investimentoRepository;

    @InjectMocks
    private InvestimentoService investimentoService;

    @Test
    void deveListarInvestimentosPorCliente() {
        Produto produto = new Produto();
        produto.setTipo("CDB");
        produto.setRentabilidade(new BigDecimal("0.12"));

        Investimento investimento = new Investimento();
        investimento.setId(1);
        investimento.setProduto(produto);
        investimento.setValor(new BigDecimal("1000.00"));
        investimento.setData(LocalDateTime.of(2025, 7, 30, 10, 0));

        when(investimentoRepository.findByClienteId(123))
                .thenReturn(List.of(investimento));


        List<InvestimentoDTO> resultado = investimentoService.listarPorCliente(123);


        assertEquals(1, resultado.size());
        InvestimentoDTO dto = resultado.get(0);
        assertEquals(1, dto.getId());
        assertEquals("CDB", dto.getTipo());
        assertEquals(new BigDecimal("1000.00"), dto.getValor());
        assertEquals(new BigDecimal("0.12"), dto.getRentabilidade());
        assertEquals(investimento.getData().toLocalDate(), dto.getData());
    }
}
