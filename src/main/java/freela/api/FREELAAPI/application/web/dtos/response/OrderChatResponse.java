package freela.api.FREELAAPI.application.web.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderChatResponse {
    private Integer id;
    private String title;
}
