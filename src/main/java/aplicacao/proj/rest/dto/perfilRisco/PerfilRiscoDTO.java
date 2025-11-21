package aplicacao.proj.rest.dto.perfilRisco;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Representa o perfil de risco calculado para um cliente")
public class PerfilRiscoDTO {

    @Schema(description = "ID do cliente", example = "42")
    @NotNull
    private Integer clienteId;

    @Schema(description = "Perfil de risco atribuído ao cliente", example = "moderado")
    @NotNull
    @Size(min = 3, max = 20)
    private String perfil;

    @Schema(description = "Pontuação total obtida no cálculo do perfil", example = "75")
    @NotNull
    @Min(value = 0)
    private Integer pontuacao;

    @Schema(description = "Descrição detalhada do perfil de risco", example = "Perfil moderado: busca equilíbrio entre segurança e rentabilidade.")
    @NotNull
    @Size(min = 5, max = 255)
    private String descricao;
}