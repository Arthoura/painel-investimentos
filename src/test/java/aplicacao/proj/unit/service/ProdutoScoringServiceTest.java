package aplicacao.proj.unit.service;

import aplicacao.proj.domain.entity.Produto;
import aplicacao.proj.service.ProdutoScoringService;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProdutoScoringServiceTest {

    private final ProdutoScoringService scoringService = new ProdutoScoringService();

    @Test
    void deveCalcularScoreParaProdutoBaixoRiscoAltaLiquidezAltaRentabilidade() {
        Produto produto = new Produto();
        produto.setRisco("baixo");
        produto.setLiquidez("alta");
        produto.setRentabilidade(new BigDecimal("0.20"));

        double score = scoringService.calcularScore(produto, 0.5, 0.3, 0.2);

        assertEquals(3.0, score);
    }

    @Test
    void deveCalcularScoreParaProdutoMedioRiscoMediaLiquidezRentabilidadeMedia() {
        Produto produto = new Produto();
        produto.setRisco("médio");
        produto.setLiquidez("média");
        produto.setRentabilidade(new BigDecimal("0.12"));

        double score = scoringService.calcularScore(produto, 0.4, 0.4, 0.2);

        assertEquals(2.0, score);
    }

    @Test
    void deveCalcularScoreParaProdutoAltoRiscoBaixaLiquidezBaixaRentabilidade() {
        Produto produto = new Produto();
        produto.setRisco("alto");
        produto.setLiquidez("baixa");
        produto.setRentabilidade(new BigDecimal("0.05"));

        double score = scoringService.calcularScore(produto, 0.3, 0.3, 0.4);

        assertEquals(1.0, score);
    }

    @Test
    void deveRetornarZeroParaValoresInvalidos() {
        Produto produto = new Produto();
        produto.setRisco("invalido");
        produto.setLiquidez("invalido");
        produto.setRentabilidade(new BigDecimal("0.00"));

        double score = scoringService.calcularScore(produto, 0.5, 0.3, 0.2);

        assertEquals(0.2, score);
    }
}
