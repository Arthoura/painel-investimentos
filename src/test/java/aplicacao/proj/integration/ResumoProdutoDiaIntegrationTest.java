package aplicacao.proj.integration;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = "/insert-simulacoes.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/cleanup-simulacoes.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class ResumoProdutoDiaIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void deveAgruparSimulacoesPorProdutoEDia() throws Exception {
        String token = KeycloakTokenUtil.getAccessToken();

        mockMvc.perform(get("/simulacoes/por-produto-dia")
                        .header("Authorization", "Bearer " + token)
                        .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].produto").value("CDB Caixa Liquidez Diária"))
                .andExpect(jsonPath("$[0].data").value("2025-11-25"))
                .andExpect(jsonPath("$[0].quantidadeSimulacoes").value(1))
                .andExpect(jsonPath("$[0].mediaValorFinal").value(10950.00));
    }
}

