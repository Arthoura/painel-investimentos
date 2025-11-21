package aplicacao.proj.unit.service;

import aplicacao.proj.domain.entity.Produto;
import aplicacao.proj.domain.repository.ProdutoRepository;
import aplicacao.proj.exception.RecursoNaoEncontradoException;
import aplicacao.proj.rest.dto.produtosRecomendados.ProdutoRecomendadoDTO;
import aplicacao.proj.service.ProdutoRecomendadoService;
import aplicacao.proj.service.ProdutoScoringService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProdutoRecomendadoServiceTest {

    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private ProdutoScoringService scoringService;

    @InjectMocks
    private ProdutoRecomendadoService produtoRecomendadoService;

    @Test
    void deveRecomendarProdutosParaPerfilConservador() {
        Produto produto1 = new Produto();
        produto1.setId(1);
        produto1.setNome("CDB Banco X");
        produto1.setTipo("CDB");
        produto1.setRentabilidade(new BigDecimal("0.10"));
        produto1.setRisco("baixo");

        Produto produto2 = new Produto();
        produto2.setId(2);
        produto2.setNome("Tesouro Selic");
        produto2.setTipo("Tesouro");
        produto2.setRentabilidade(new BigDecimal("0.08"));
        produto2.setRisco("médio");

        when(produtoRepository.findAll()).thenReturn(List.of(produto1, produto2));

        // Simula cálculo de score pelo serviço de scoring
        when(scoringService.calcularScore(produto1, 0.5, 0.4, 0.1)).thenReturn(3.5);
        when(scoringService.calcularScore(produto2, 0.5, 0.4, 0.1)).thenReturn(4.0);

        List<ProdutoRecomendadoDTO> recomendados = produtoRecomendadoService.recomendarPorPerfil("conservador");

        assertEquals(2, recomendados.size());
        assertEquals("Tesouro Selic", recomendados.get(0).getNome()); // maior score primeiro
        assertEquals("CDB Banco X", recomendados.get(1).getNome());
    }

    @Test
    void deveRecomendarProdutosParaPerfilAgressivo() {
        Produto produto = new Produto();
        produto.setId(3);
        produto.setNome("Ações Tech");
        produto.setTipo("Ações");
        produto.setRentabilidade(new BigDecimal("0.20"));
        produto.setRisco("alto");

        when(produtoRepository.findAll()).thenReturn(List.of(produto));
        when(scoringService.calcularScore(produto, 0.1, 0.2, 0.7)).thenReturn(5.0);

        List<ProdutoRecomendadoDTO> recomendados = produtoRecomendadoService.recomendarPorPerfil("agressivo");

        assertEquals(1, recomendados.size());
        assertEquals("Ações Tech", recomendados.get(0).getNome());
        assertEquals("Ações", recomendados.get(0).getTipo());
    }

    @Test
    void deveLancarExcecaoRecursoNaoEncontrado() {
        try {
            produtoRecomendadoService.recomendarPorPerfil("invalido");
        } catch (RecursoNaoEncontradoException e) {
            assertEquals("Perfil inválido: invalido", e.getMessage());
        }
    }
}
