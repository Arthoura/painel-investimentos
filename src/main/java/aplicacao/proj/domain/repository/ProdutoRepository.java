package aplicacao.proj.domain.repository;

import aplicacao.proj.domain.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Short> {

    @Query(value = "SELECT p FROM Produto p WHERE p.vrMinimo <= :valor " +
            "AND (p.vrMaximo IS NULL OR p.vrMaximo >= :valor) " +
            "AND p.nuMinimoMeses <= :prazo " +
            "AND (p.nuMaximoMeses IS NULL OR p.nuMaximoMeses >= :prazo)")
    Optional<Produto> findProdutoByValorAndPrazo(
            @Param("valor") BigDecimal valor,
            @Param("prazo") Short prazo);
}