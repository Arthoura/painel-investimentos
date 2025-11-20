package aplicacao.proj.rest.dto.perfilRisco;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PerfilRiscoDTO {

    private Integer clienteId;
    private String perfil;
    private Integer pontuacao;
    private String descricao;

}