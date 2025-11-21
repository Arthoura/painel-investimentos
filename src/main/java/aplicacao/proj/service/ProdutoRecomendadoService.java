package aplicacao.proj.service;

import aplicacao.proj.domain.entity.Produto;
import aplicacao.proj.domain.repository.ProdutoRepository;
import aplicacao.proj.exception.RecursoNaoEncontradoException;
import aplicacao.proj.rest.dto.produtosRecomendados.ProdutoRecomendadoDTO;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;


@Service
public class ProdutoRecomendadoService {

    private final ProdutoRepository produtoRepository;
    private final ProdutoScoringService scoringService;

    public ProdutoRecomendadoService(ProdutoRepository produtoRepository, ProdutoScoringService scoringService) {
        this.produtoRepository = produtoRepository;
        this.scoringService = scoringService;
    }

    @CircuitBreaker(name = "sqlServerCircuitBreaker", fallbackMethod = "fallbackRecomendarPorPerfil")
    public List<ProdutoRecomendadoDTO> recomendarPorPerfil(String perfil) {
        double pesoRisco, pesoLiquidez, pesoRentabilidade, scoreMinimo = 2.0;

        switch (perfil.toLowerCase()) {
            case "conservador" -> {
                pesoRisco = 0.5;
                pesoLiquidez = 0.4;
                pesoRentabilidade = 0.1;
            }
            case "moderado" -> {
                pesoRisco = 0.3;
                pesoLiquidez = 0.3;
                pesoRentabilidade = 0.4;
            }
            case "agressivo" -> {
                pesoRisco = 0.1;
                pesoLiquidez = 0.2;
                pesoRentabilidade = 0.7;
            }
            default -> throw new RecursoNaoEncontradoException("Perfil inválido: " + perfil);
        }

        return produtoRepository.findAll().stream()
                .map(p -> new ProdutoComScore(p, scoringService.calcularScore(p, pesoRisco, pesoLiquidez, pesoRentabilidade)))
                .filter(pcs -> pcs.score() >= scoreMinimo)
                .sorted(Comparator.comparingDouble(ProdutoComScore::score).reversed())
                .map(pcs -> new ProdutoRecomendadoDTO(
                        pcs.produto().getId(),
                        pcs.produto().getNome(),
                        pcs.produto().getTipo(),
                        pcs.produto().getRentabilidade(),
                        pcs.produto().getRisco()
                ))
                .collect(Collectors.toList());
    }

    // Fallback chamado quando o circuito está aberto ou ocorre falha
    public List<ProdutoRecomendadoDTO> fallbackRecomendarPorPerfil(String perfil, Throwable t) {
        if (t instanceof RecursoNaoEncontradoException) {
            throw (RecursoNaoEncontradoException) t;
        }
        throw new RecursoNaoEncontradoException("Serviço temporariamente indisponível. Tente novamente mais tarde.");
    }

    private record ProdutoComScore(Produto produto, double score) {}
}