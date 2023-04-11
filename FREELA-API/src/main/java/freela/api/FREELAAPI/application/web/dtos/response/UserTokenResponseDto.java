package freela.api.FREELAAPI.application.web.dtos.response;

import freela.api.FREELAAPI.resourses.entities.Users;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserTokenResponseDto {
    private Integer userId;
    private String name;
    private String email;
    private String token;

    public static UserTokenResponseDto of(Users users, String token) {
      UserTokenResponseDto userTokenResponseDto = new UserTokenResponseDto();

      userTokenResponseDto.setUserId(users.getId());
      userTokenResponseDto.setEmail(users.getEmail());
      userTokenResponseDto.setName(users.getName());
      userTokenResponseDto.setToken(token);

      return userTokenResponseDto;
    }
}
