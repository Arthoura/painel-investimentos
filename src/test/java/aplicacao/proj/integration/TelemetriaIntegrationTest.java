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
class TelemetriaIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Sql(scripts = "/cleanup-telemetria.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/insert-telemetria.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/cleanup-telemetria.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void deveRetornarDadosDeTelemetria() throws Exception {
        String token = KeycloakTokenUtil.getAccessToken();

        mockMvc.perform(get("/telemetria")
                        .header("Authorization", "Bearer " + token)
                        .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.servicos[0].nome").value("simular-investimento"))
                .andExpect(jsonPath("$.servicos[0].quantidadeChamadas").value(10))
                .andExpect(jsonPath("$.servicos[0].mediaTempoRespostaMs").value(121)) // arredondado
                .andExpect(jsonPath("$.servicos[1].nome").value("perfil-risco"))
                .andExpect(jsonPath("$.servicos[1].quantidadeChamadas").value(5))
                .andExpect(jsonPath("$.servicos[1].mediaTempoRespostaMs").value(95))
                .andExpect(jsonPath("$.periodo.inicio").value("2025-11-01"))
                .andExpect(jsonPath("$.periodo.fim").value("2025-11-20"));
    }
}
