package aplicacao.proj.rest.dto.telemetria;

import java.time.LocalDate;

public class PeriodoDTO {

    private LocalDate inicio;
    private LocalDate fim;

    // Getters e Setters
    public LocalDate getInicio() {
        return inicio;
    }

    public void setInicio(LocalDate inicio) {
        this.inicio = inicio;
    }

    public LocalDate getFim() {
        return fim;
    }

    public void setFim(LocalDate fim) {
        this.fim = fim;
    }
}