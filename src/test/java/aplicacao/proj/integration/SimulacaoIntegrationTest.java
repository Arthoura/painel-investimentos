package aplicacao.proj.integration;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SimulacaoIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Sql(scripts = "/insert-simulacao-cliente-sem-investimentos.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/cleanup-simulacao.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void deveRetornar404QuandoClienteNaoTemInvestimentos() throws Exception {
        String token = KeycloakTokenUtil.getAccessToken();

        String requestJson = """
            {
              "clienteId": 1,
              "valor": 1000.00,
              "prazoMeses": 12,
              "tipoProduto": "CDB"
            }
            """;

        mockMvc.perform(post("/simular-investimento")
                        .header("Authorization", "Bearer " + token)
                        .contentType(APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isNotFound());
    }

    @Test
    @Sql(scripts = "/insert-simulacao-com-investimento.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/cleanup-simulacao.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void deveRetornar400QuandoProdutoNaoExiste() throws Exception {
        String token = KeycloakTokenUtil.getAccessToken();

        String requestJson = """
            {
              "clienteId": 1,
              "valor": 1000.00,
              "prazoMeses": 12,
              "tipoProduto": "Inexistente"
            }
            """;

        mockMvc.perform(post("/simular-investimento")
                        .header("Authorization", "Bearer " + token)
                        .contentType(APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isNotFound());
    }

    @Test
    @Sql(scripts = "/insert-simulacao-com-investimento.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/cleanup-simulacao.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void deveSimularInvestimentoComSucesso() throws Exception {
        String token = KeycloakTokenUtil.getAccessToken();

        String requestJson = """
            {
              "clienteId": 1,
              "valor": 1000.00,
              "prazoMeses": 12,
              "tipoProduto": "CDB"
            }
            """;

        mockMvc.perform(post("/simular-investimento")
                        .header("Authorization", "Bearer " + token)
                        .contentType(APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.produtoValidado.nome").value("CDB Banco X"))
                .andExpect(jsonPath("$.resultadoSimulacao.valorFinal").exists())
                .andExpect(jsonPath("$.resultadoSimulacao.prazoMeses").value(12));
    }
}
