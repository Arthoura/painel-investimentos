package aplicacao.proj.unit.service;


import aplicacao.proj.Enums.PreferenciaLiquidezRentabilidade;
import aplicacao.proj.domain.entity.Cliente;
import aplicacao.proj.domain.entity.Investimento;
import aplicacao.proj.domain.entity.Produto;
import aplicacao.proj.domain.repository.ClienteRepository;
import aplicacao.proj.domain.repository.InvestimentoRepository;
import aplicacao.proj.rest.dto.perfilRisco.PerfilRiscoDTO;
import aplicacao.proj.service.PerfilRiscoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PerfilRiscoServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private InvestimentoRepository investimentoRepository;

    @InjectMocks
    private PerfilRiscoService perfilRiscoService;

    @Test
    void deveCalcularPerfilConservador() {
        Cliente cliente = new Cliente();
        cliente.setId(1);
        cliente.setFrequenciaMovimentacoesMensal(1);
        cliente.setPreferenciaLiquidezRentabilidade(PreferenciaLiquidezRentabilidade.LIQUIDEZ);

        Produto produto = new Produto();
        produto.setTipo("CDB");
        produto.setRentabilidade(new BigDecimal("0.10"));

        Investimento investimento = new Investimento();
        investimento.setId(1);
        investimento.setProduto(produto);
        investimento.setValor(new BigDecimal("5000"));
        investimento.setData(LocalDateTime.now());

        when(clienteRepository.findById(1)).thenReturn(Optional.of(cliente));
        when(investimentoRepository.findByClienteId(1)).thenReturn(List.of(investimento));

        PerfilRiscoDTO dto = perfilRiscoService.calcularPerfil(1);

        assertEquals("CONSERVADOR", dto.getPerfil());
        assertEquals(30, dto.getPontuacao());
    }

    @Test
    void deveCalcularPerfilModerado() {
        Cliente cliente = new Cliente();
        cliente.setId(2);
        cliente.setFrequenciaMovimentacoesMensal(4);
        cliente.setPreferenciaLiquidezRentabilidade(PreferenciaLiquidezRentabilidade.EQUILIBRIO);

        Produto produto = new Produto();
        produto.setTipo("LCI");
        produto.setRentabilidade(new BigDecimal("0.15"));

        Investimento investimento = new Investimento();
        investimento.setId(2);
        investimento.setProduto(produto);
        investimento.setValor(new BigDecimal("15000"));
        investimento.setData(LocalDateTime.now());

        when(clienteRepository.findById(2)).thenReturn(Optional.of(cliente));
        when(investimentoRepository.findByClienteId(2)).thenReturn(List.of(investimento));

        PerfilRiscoDTO dto = perfilRiscoService.calcularPerfil(2);

        assertEquals("MODERADO", dto.getPerfil());
        assertEquals(65, dto.getPontuacao());
    }

    @Test
    void deveCalcularPerfilAgressivo() {
        Cliente cliente = new Cliente();
        cliente.setId(3);
        cliente.setFrequenciaMovimentacoesMensal(10);
        cliente.setPreferenciaLiquidezRentabilidade(PreferenciaLiquidezRentabilidade.RENTABILIDADE);

        Produto produto = new Produto();
        produto.setTipo("Ações");
        produto.setRentabilidade(new BigDecimal("0.20"));

        Investimento investimento = new Investimento();
        investimento.setId(3);
        investimento.setProduto(produto);
        investimento.setValor(new BigDecimal("50000"));
        investimento.setData(LocalDateTime.now());

        when(clienteRepository.findById(3)).thenReturn(Optional.of(cliente));
        when(investimentoRepository.findByClienteId(3)).thenReturn(List.of(investimento));

        PerfilRiscoDTO dto = perfilRiscoService.calcularPerfil(3);

        assertEquals("AGRESSIVO", dto.getPerfil());
        assertEquals(100, dto.getPontuacao());
    }
}
