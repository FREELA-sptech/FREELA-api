package freela.api.FREELAAPI.application.web.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatRequest {
    private Integer freelancerId;
    private Integer userId;
    private Integer orderId;
    private String lastUpdate;
}
