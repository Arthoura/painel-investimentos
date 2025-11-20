package aplicacao.proj.service;


import aplicacao.proj.Enums.Perfil;
import aplicacao.proj.Enums.PreferenciaLiquidezRentabilidade;
import aplicacao.proj.domain.entity.Cliente;
import aplicacao.proj.domain.entity.Investimento;
import aplicacao.proj.domain.repository.ClienteRepository;
import aplicacao.proj.domain.repository.InvestimentoRepository;
import aplicacao.proj.rest.dto.perfilRisco.PerfilRiscoDTO;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PerfilRiscoService {

    private final ClienteRepository clienteRepository;
    private final InvestimentoRepository investimentoRepository;

    public PerfilRiscoService(ClienteRepository clienteRepository, InvestimentoRepository investimentoRepository) {
        this.clienteRepository = clienteRepository;
        this.investimentoRepository = investimentoRepository;
    }

    public PerfilRiscoDTO calcularPerfil(Integer clienteId) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado: " + clienteId));

        List<Investimento> investimentos = investimentoRepository.findByClienteId(clienteId);
        BigDecimal volumeTotal = investimentos.stream()
                .map(i -> i.getValor())
                .reduce((v1, v2) -> v1.add(v2))
                .orElseThrow(() -> new IllegalArgumentException("Cliente sem investimentos"));

        int pontuacao = 0;

        // Volume de investimentos
        double volume = volumeTotal.doubleValue();
        if (volume < 6000) pontuacao += 10;
        else if (volume < 20000) pontuacao += 25;
        else pontuacao += 40;

        // Frequência de movimentações (quanto mais, mais agressivo)
        int mov = cliente.getFrequenciaMovimentacoesMensal();
        if (mov <= 2) pontuacao += 10;
        else if (mov <= 6) pontuacao += 20;
        else pontuacao += 30;

        // Preferência
        PreferenciaLiquidezRentabilidade preferencia = cliente.getPreferenciaLiquidezRentabilidade();
        switch (preferencia) {
            case LIQUIDEZ -> pontuacao += 10;
            case RENTABILIDADE -> pontuacao += 30;
            case EQUILIBRIO -> pontuacao += 20;
            default -> pontuacao += 15; // opcional: caso queira tratar nulo ou valor inesperado
        }


        Perfil perfil;

        if (pontuacao < 40) {
            perfil = Perfil.CONSERVADOR;
        } else if (pontuacao < 70) {
            perfil = Perfil.MODERADO;
        } else {
            perfil = Perfil.AGRESSIVO;
        }

        return new PerfilRiscoDTO(clienteId, perfil.name(), pontuacao, perfil.getDescricao());
    }
}