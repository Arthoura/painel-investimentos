package aplicacao.proj.service;



import aplicacao.proj.domain.repository.ProdutoRepository;
import aplicacao.proj.rest.dto.*;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;




import java.time.LocalDate;
import java.util.*;

@Service
public class SimularService {


    @Autowired
    @Qualifier("produtoRepository")
    ProdutoRepository produtoRepository;






    public SimularService(ProdutoRepository produtoRepository
                          ) {
        this.produtoRepository = produtoRepository;
    }




    @Transactional(transactionManager = "sqlServerTransactionManager")
    @CircuitBreaker(name = "sqlServerCircuitBreaker", fallbackMethod = "processarSimulacaoFallback")
    public ResponseEntity<?> processarSimulacao(EnvelopeDto request) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

    }



    @Cacheable(value = "listaSimulacao")
    public List<PaginacaoDto<SimulacaoResumoDto>> listarSimulacao() {

        return null;
    }



    @Cacheable(value = "simulacoesPorData")
    public SimulacoesPorDataDto listarSimulacoesPorData(LocalDate dataReferencia) {


        return null;
    }



}

