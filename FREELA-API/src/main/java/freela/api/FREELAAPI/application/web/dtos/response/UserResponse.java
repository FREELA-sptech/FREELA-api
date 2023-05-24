package freela.api.FREELAAPI.application.web.dtos.response;

import freela.api.FREELAAPI.application.web.dtos.request.UserRequest;
import freela.api.FREELAAPI.resourses.entities.Category;
import freela.api.FREELAAPI.resourses.entities.SubCategory;
import freela.api.FREELAAPI.resourses.entities.Users;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {
    private Integer id;
    private String name;
    private byte[] profilePhoto;
    private Double rate;
    private String uf;
    private String city;
    private List<SubCategory> subcategories;

    public static UserResponse mapper(Users users, Double rate, List<SubCategory> subCategories) {
        UserResponse userResponse = new UserResponse();

        userResponse.setName(users.getName());
        userResponse.setProfilePhoto(users.getProfilePhoto());
        userResponse.setRate(rate);
        userResponse.setUf(users.getUf());
        userResponse.setCity(users.getCity());
        userResponse.setSubcategories(subCategories);

        return userResponse;
    }
}
