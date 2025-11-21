package aplicacao.proj.unit.service;

import aplicacao.proj.Enums.Perfil;
import aplicacao.proj.domain.entity.Produto;
import aplicacao.proj.domain.entity.Simulacao;
import aplicacao.proj.domain.repository.ProdutoRepository;
import aplicacao.proj.domain.repository.SimulacaoRepository;
import aplicacao.proj.rest.dto.perfilRisco.PerfilRiscoDTO;
import aplicacao.proj.rest.dto.simulacao.SimulacaoRequestDto;
import aplicacao.proj.rest.dto.simulacao.response.SimulacaoResponseDto;
import aplicacao.proj.service.PerfilRiscoService;
import aplicacao.proj.service.ProdutoScoringService;
import aplicacao.proj.service.SimulacaoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SimulacaoServiceTest {

    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private PerfilRiscoService perfilRiscoService;

    @Mock
    private ProdutoScoringService scoringService;

    @Mock
    private SimulacaoRepository simulacaoRepository;

    @InjectMocks
    private SimulacaoService simulacaoService;

    @Test
    void deveSimularInvestimentoComPerfilConservador() {

        SimulacaoRequestDto request = new SimulacaoRequestDto(
                1,
                new BigDecimal("1000.00"),
                12,
                "CDB"
        );

        PerfilRiscoDTO perfilDto = new PerfilRiscoDTO(1, Perfil.CONSERVADOR.name(), 30, "Perfil conservador");
        when(perfilRiscoService.calcularPerfil(1)).thenReturn(perfilDto);

        Produto produto = new Produto();
        produto.setId(10);
        produto.setNome("CDB Banco X");
        produto.setTipo("CDB");
        produto.setRentabilidade(new BigDecimal("0.12"));
        produto.setRisco("baixo");
        produto.setLiquidez("alta");

        when(produtoRepository.findByTipo("CDB")).thenReturn(List.of(produto));
        when(scoringService.calcularScore(produto, 0.5, 0.3, 0.2)).thenReturn(3.5);


        SimulacaoResponseDto response = simulacaoService.simularInvestimento(request);


        assertEquals("CDB Banco X", response.getProdutoValidado().getNome());
        assertEquals("CDB", response.getProdutoValidado().getTipo());
        assertEquals(new BigDecimal("0.12"), response.getProdutoValidado().getRentabilidade());
        assertEquals(request.getPrazoMeses(), response.getResultadoSimulacao().getPrazoMeses());


        BigDecimal taxaMensal = produto.getRentabilidade().divide(BigDecimal.valueOf(12), 10, BigDecimal.ROUND_HALF_UP);
        BigDecimal fator = BigDecimal.ONE.add(taxaMensal.multiply(BigDecimal.valueOf(request.getPrazoMeses())));
        BigDecimal esperado = request.getValor().multiply(fator).setScale(2, BigDecimal.ROUND_HALF_UP);

        assertEquals(esperado, response.getResultadoSimulacao().getValorFinal());


        verify(simulacaoRepository, times(1)).save(any(Simulacao.class));
    }
}
