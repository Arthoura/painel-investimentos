package aplicacao.proj.domain.repository;

import aplicacao.proj.domain.entity.Telemetria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface TelemetriaRepository extends JpaRepository<Telemetria, Integer> {

    Optional<Telemetria> findByServicoAndPeriodoInicio(String servico, LocalDate periodoInicio);
    Optional<Telemetria> findByServico(String servico);

}
