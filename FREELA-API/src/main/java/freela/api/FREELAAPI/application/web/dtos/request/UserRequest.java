package freela.api.FREELAAPI.application.web.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    @Size(min = 2, max = 50)
    private String name;
    @Email
    private String email;
    @Size(min = 8, max = 50)
    private String password;
    @Size(min = 4, max = 50)
    private String userName;
}
