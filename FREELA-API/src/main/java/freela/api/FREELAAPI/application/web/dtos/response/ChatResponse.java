package freela.api.FREELAAPI.application.web.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatResponse {
    private Integer id;
    private UserOrderResponse freelancerUser;
    private UserOrderResponse clientUser;
    private OrderChatResponse order;
    private String lastUpdate;
}
