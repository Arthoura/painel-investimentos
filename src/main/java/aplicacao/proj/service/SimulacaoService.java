package aplicacao.proj.service;

import aplicacao.proj.Enums.Perfil;
import aplicacao.proj.domain.entity.Produto;
import aplicacao.proj.domain.entity.Simulacao;
import aplicacao.proj.domain.repository.ProdutoRepository;
import aplicacao.proj.domain.repository.SimulacaoRepository;
import aplicacao.proj.exception.RecursoNaoEncontradoException;
import aplicacao.proj.rest.dto.historicoProdutoDia.ResumoProdutoDiaDto;
import aplicacao.proj.rest.dto.historicoSimulacoes.SimulacaoResumoDTO;
import aplicacao.proj.rest.dto.perfilRisco.PerfilRiscoDTO;
import aplicacao.proj.rest.dto.produtosRecomendados.ProdutoRecomendadoDTO;
import aplicacao.proj.rest.dto.simulacao.SimulacaoRequestDto;
import aplicacao.proj.rest.dto.simulacao.response.ResultadoSimulacaoDTO;
import aplicacao.proj.rest.dto.simulacao.response.SimulacaoResponseDto;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SimulacaoService {

    private final ProdutoRepository produtoRepository;
    private final PerfilRiscoService perfilRiscoService;
    private final ProdutoScoringService scoringService;
    private final SimulacaoRepository simulacaoRepository;


    public SimulacaoService(SimulacaoRepository simulacaoRepository, ProdutoRepository produtoRepository, PerfilRiscoService perfilRiscoService, ProdutoScoringService scoringService) {
        this.produtoRepository = produtoRepository;
        this.perfilRiscoService = perfilRiscoService;
        this.scoringService = scoringService;
        this.simulacaoRepository = simulacaoRepository;
    }

    public SimulacaoResponseDto simularInvestimento(SimulacaoRequestDto request) {
        PerfilRiscoDTO perfilDto = perfilRiscoService.calcularPerfil(request.getClienteId());
        Perfil perfil = Perfil.valueOf(perfilDto.getPerfil().toUpperCase());

        double pesoRisco, pesoLiquidez, pesoRentabilidade;
        switch (perfil) {
            case CONSERVADOR -> {
                pesoRisco = 0.5;
                pesoLiquidez = 0.3;
                pesoRentabilidade = 0.2;
            }
            case MODERADO -> {
                pesoRisco = 0.3;
                pesoLiquidez = 0.3;
                pesoRentabilidade = 0.4;
            }
            case AGRESSIVO -> {
                pesoRisco = 0.1;
                pesoLiquidez = 0.2;
                pesoRentabilidade = 0.7;
            }
            default -> throw new IllegalArgumentException("Perfil inválido: " + perfil);
        }

        List<Produto> produtos = produtoRepository.findByTipo(request.getTipoProduto());

        ProdutoComScore melhorProduto = produtos.stream()
                .map(p -> new ProdutoComScore(p, scoringService.calcularScore(p, pesoRisco, pesoLiquidez, pesoRentabilidade)))
                .max(Comparator.comparingDouble(ProdutoComScore::score))
                .orElseThrow(() -> new RecursoNaoEncontradoException("Nenhum produto encontrado para o tipo informado: " + request.getTipoProduto()));
        ;

        Produto produto = melhorProduto.produto();
        BigDecimal rentabilidade = produto.getRentabilidade();

        BigDecimal taxaMensal = rentabilidade.divide(BigDecimal.valueOf(12), 10, BigDecimal.ROUND_HALF_UP);
        BigDecimal fator = BigDecimal.ONE.add(taxaMensal.multiply(BigDecimal.valueOf(request.getPrazoMeses())));
        BigDecimal valorFinal = request.getValor().multiply(fator).setScale(2, BigDecimal.ROUND_HALF_UP);

        Simulacao simulacao = new Simulacao();
        simulacao.setClientId(request.getClienteId());
        simulacao.setProduto(produto);
        simulacao.setValorInvestido(request.getValor());
        simulacao.setValorFinal(valorFinal);
        simulacao.setPrazoMeses(request.getPrazoMeses());
        simulacao.setDataSimulacao(LocalDateTime.now());

        simulacaoRepository.save(simulacao);

        return new SimulacaoResponseDto(
                new ProdutoRecomendadoDTO(
                        produto.getId(),
                        produto.getNome(),
                        produto.getTipo(),
                        rentabilidade,
                        produto.getRisco()
                ),
                new ResultadoSimulacaoDTO(
                        valorFinal,
                        rentabilidade,
                        request.getPrazoMeses()
                ),
                LocalDateTime.now()
        );
    }

    public List<SimulacaoResumoDTO> listarSimulacoes() {
        return simulacaoRepository.findAll().stream()
                .map(s -> new SimulacaoResumoDTO(
                        s.getId(),
                        s.getClientId(),
                        s.getProduto().getNome(),
                        s.getValorInvestido(),
                        s.getValorFinal(),
                        s.getPrazoMeses(),
                        s.getDataSimulacao()
                ))
                .toList();
    }

    public List<ResumoProdutoDiaDto> agruparPorProdutoEDia() {
        return simulacaoRepository.findAll().stream()
                .collect(Collectors.groupingBy(
                        s -> new ProdutoDataKey(s.getProduto().getNome(), s.getDataSimulacao().toLocalDate()),
                        Collectors.collectingAndThen(Collectors.toList(), simulacoes -> {
                            int quantidade = simulacoes.size();
                            BigDecimal soma = simulacoes.stream()
                                    .map(Simulacao::getValorFinal)
                                    .reduce(BigDecimal.ZERO, BigDecimal::add);
                            BigDecimal media = quantidade > 0
                                    ? soma.divide(BigDecimal.valueOf(quantidade), 2, RoundingMode.HALF_UP)
                                    : BigDecimal.ZERO;
                            return new ResumoProdutoDiaDto(
                                    simulacoes.get(0).getProduto().getNome(),
                                    simulacoes.get(0).getDataSimulacao().toLocalDate(),
                                    quantidade,
                                    media
                            );
                        })
                ))
                .values()
                .stream()
                .toList();
    }

    private record ProdutoDataKey(String produto, LocalDate data) {}

    private record ProdutoComScore(Produto produto, double score) {}
}