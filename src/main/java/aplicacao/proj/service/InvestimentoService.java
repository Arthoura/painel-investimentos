package aplicacao.proj.service;

import aplicacao.proj.domain.entity.Investimento;
import aplicacao.proj.domain.repository.InvestimentoRepository;
import aplicacao.proj.rest.dto.historicoInvestimentos.InvestimentoDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvestimentoService {

    private final InvestimentoRepository investimentoRepository;

    public InvestimentoService(InvestimentoRepository investimentoRepository) {
        this.investimentoRepository = investimentoRepository;
    }

    public List<InvestimentoDTO> listarPorCliente(Integer clienteId) {
        List<Investimento> investimentos = investimentoRepository.findByClienteId(clienteId);

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
}