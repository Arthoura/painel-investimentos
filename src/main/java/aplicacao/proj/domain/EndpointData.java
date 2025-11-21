package aplicacao.proj.domain;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class EndpointData {
    private String endpointName;
    private long executionTime;
    private int httpStatus;
    private LocalDateTime timestamp;

    public EndpointData(String endpointName, int httpStatus, long executionTime) {
        this.executionTime = executionTime;
        this.httpStatus = httpStatus;
        this.endpointName = endpointName;
    }
}