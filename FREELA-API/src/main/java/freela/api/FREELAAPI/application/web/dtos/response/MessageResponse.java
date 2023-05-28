package freela.api.FREELAAPI.application.web.dtos.response;

import freela.api.FREELAAPI.resourses.entities.Chat;
import freela.api.FREELAAPI.resourses.entities.Users;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageResponse {
    private Integer id;
    private UserOrderResponse from;
    private String message;
    private String time;
    private ChatResponse chat;
}
