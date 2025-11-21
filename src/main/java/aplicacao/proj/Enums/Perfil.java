package aplicacao.proj.Enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Perfil {
    CONSERVADOR("Perfil voltado à segurança e liquidez."),
    MODERADO("Perfil equilibrado entre segurança e rentabilidade."),
    AGRESSIVO("Perfil voltado à rentabilidade com maior tolerância a risco.");

    private final String descricao;
}