package aplicacao.proj.service;

import aplicacao.proj.domain.entity.Investimento;
import aplicacao.proj.domain.repository.InvestimentoRepository;
import aplicacao.proj.rest.dto.historicoInvestimentos.InvestimentoDTO;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.stereotype.Service;
import aplicacao.proj.exception.RecursoNaoEncontradoException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvestimentoService {

    private final InvestimentoRepository investimentoRepository;

    public InvestimentoService(InvestimentoRepository investimentoRepository) {
        this.investimentoRepository = investimentoRepository;
    }

    @CircuitBreaker(name = "sqlServerCircuitBreaker", fallbackMethod = "fallbackListarPorCliente")
    public List<InvestimentoDTO> listarPorCliente(Integer clienteId) {
        List<Investimento> investimentos = investimentoRepository.findByClienteId(clienteId);

        if (investimentos.isEmpty()) {
            throw new RecursoNaoEncontradoException("Nenhum investimento encontrado para o cliente informado.");
        }

        return investimentos.stream()
                .map(inv -> new InvestimentoDTO(
                        inv.getId(),
                        inv.getProduto().getTipo(),
                        inv.getValor(),
                        inv.getProduto().getRentabilidade(),
                        inv.getData().toLocalDate()
                ))
                .collect(Collectors.toList());
    }

    // Fallback: retorna mensagem de indisponibilidade
    public List<InvestimentoDTO> fallbackListarPorCliente(Integer clienteId, Throwable t) {
        if (t instanceof RecursoNaoEncontradoException) {
            throw (RecursoNaoEncontradoException) t;
        }
        throw new RecursoNaoEncontradoException("Serviço temporariamente indisponível. Tente novamente mais tarde.");
    }
}
