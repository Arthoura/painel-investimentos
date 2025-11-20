package aplicacao.proj.service;

import aplicacao.proj.domain.entity.Produto;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ProdutoScoringService {

    public double calcularScore(Produto produto, double pesoRisco, double pesoLiquidez, double pesoRentabilidade) {
        return pesoRisco * riscoScore(produto.getRisco()) +
                pesoLiquidez * liquidezScore(produto.getLiquidez()) +
                pesoRentabilidade * rentabilidadeScore(produto.getRentabilidade());
    }

    private int riscoScore(String risco) {
        return switch (risco.toLowerCase()) {
            case "baixo" -> 3;
            case "médio", "medio" -> 2;
            case "alto" -> 1;
            default -> 0;
        };
    }

    private int liquidezScore(String liquidez) {
        return switch (liquidez.toLowerCase()) {
            case "alta" -> 3;
            case "média", "media" -> 2;
            case "baixa" -> 1;
            default -> 0;
        };
    }

    private int rentabilidadeScore(BigDecimal rentabilidade) {
        double r = rentabilidade.doubleValue();
        if (r >= 0.15) return 3;
        else if (r >= 0.10) return 2;
        else return 1;
    }
}