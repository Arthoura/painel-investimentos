package aplicacao.proj.domain;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class EndpointData {
    private String endpointName;
    private long executionTime;
    private int httpStatus;
    private LocalDateTime timestamp;
}