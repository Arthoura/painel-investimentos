package aplicacao.proj.domain.repository;

import aplicacao.proj.domain.entity.Simulacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SimulacaoRepository extends JpaRepository<Simulacao, Integer> {
}