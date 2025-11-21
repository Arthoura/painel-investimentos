package aplicacao.proj.integration;

import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

public class KeycloakTokenUtil {

    private static final String TOKEN_URL = "http://localhost:8081/realms/investimentos/protocol/openid-connect/token";
    private static final String CLIENT_ID = "investe-api";
    private static final String CLIENT_SECRET = "DiAxyodMmRJD4r7YPZPh9m0WZXbN2N9e";
    private static final String USERNAME = "arthur";
    private static final String PASSWORD = "Admin";

    public static String getAccessToken() {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "password");
        body.add("client_id", CLIENT_ID);
        body.add("client_secret", CLIENT_SECRET);
        body.add("username", USERNAME);
        body.add("password", PASSWORD);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(TOKEN_URL, request, Map.class);

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            return (String) response.getBody().get("access_token");
        }
        throw new RuntimeException("Não foi possível obter o token do Keycloak");
    }
}
