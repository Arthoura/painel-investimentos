package aplicacao.proj.rest.dto.telemetria;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class PeriodoDTO {

    private LocalDate inicio;
    private LocalDate fim;

}