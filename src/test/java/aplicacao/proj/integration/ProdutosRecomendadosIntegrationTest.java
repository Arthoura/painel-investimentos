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
@Sql(scripts = "/insert-produtos.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/cleanup-produtos.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class ProdutosRecomendadosIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void deveRecomendarProdutosParaPerfilModerado() throws Exception {
        String token = KeycloakTokenUtil.getAccessToken();

        mockMvc.perform(get("/produtos-recomendados/moderado")
                        .header("Authorization", "Bearer " + token)
                        .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(101))
                .andExpect(jsonPath("$[0].nome").value("CDB Caixa 2026"))
                .andExpect(jsonPath("$[0].tipo").value("CDB"))
                .andExpect(jsonPath("$[0].rentabilidade").value(0.1200))
                .andExpect(jsonPath("$[0].risco").value("baixo"));
    }

    @Test
    void deveRetornar400ParaPerfilInvalido() throws Exception {
        String token = KeycloakTokenUtil.getAccessToken();

        mockMvc.perform(get("/produtos-recomendados/invalido")
                        .header("Authorization", "Bearer " + token)
                        .accept(APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}
