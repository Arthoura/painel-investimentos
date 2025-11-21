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
@Sql(scripts = "/insert-investimentos.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/cleanup-investimentos.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class HistoricoInvestimentosIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void deveListarInvestimentosPorCliente() throws Exception {
        String token = KeycloakTokenUtil.getAccessToken();

        mockMvc.perform(get("/investimentos/15")
                        .header("Authorization", "Bearer " + token)
                        .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(15))
                .andExpect(jsonPath("$[0].tipo").value("CDB"))
                .andExpect(jsonPath("$[0].valor").value(350.00))
                .andExpect(jsonPath("$[0].rentabilidade").value(0.109))
                .andExpect(jsonPath("$[0].data").value("2025-10-30"));
    }
}

