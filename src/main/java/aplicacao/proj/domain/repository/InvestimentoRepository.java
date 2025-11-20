package aplicacao.proj.domain.repository;

import aplicacao.proj.domain.entity.Investimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface InvestimentoRepository extends JpaRepository<Investimento, Integer> {


    // Buscar todos os investimentos de um cliente específico
    List<Investimento> findByClienteId(Integer clienteId);

    // Buscar investimentos por tipo de produto
    List<Investimento> findByProdutoTipo(String tipo);

    // Buscar investimentos dentro de um intervalo de datas
    List<Investimento> findByDataBetween(LocalDateTime inicio, LocalDateTime fim);
}