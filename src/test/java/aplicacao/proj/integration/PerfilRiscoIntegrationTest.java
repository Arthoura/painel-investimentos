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
@Sql(scripts = "/insert-perfil-risco.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/cleanup-perfil-risco.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class PerfilRiscoIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void deveCalcularPerfilDeRiscoDoCliente() throws Exception {
        String token = KeycloakTokenUtil.getAccessToken();

        mockMvc.perform(get("/perfil-risco/15")
                        .header("Authorization", "Bearer " + token)
                        .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.clienteId").value(15))
                .andExpect(jsonPath("$.perfil").value("MODERADO"))
                .andExpect(jsonPath("$.pontuacao").value(50))
                .andExpect(jsonPath("$.descricao").value("Perfil equilibrado entre segurança e rentabilidade."));

    }

    @Test
    void deveRetornar404QuandoClienteNaoExiste() throws Exception {
        String token = KeycloakTokenUtil.getAccessToken();

        mockMvc.perform(get("/perfil-risco/99")
                        .header("Authorization", "Bearer " + token)
                        .accept(APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }
}
