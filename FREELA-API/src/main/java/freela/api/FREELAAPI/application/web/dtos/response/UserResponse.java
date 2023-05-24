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
    private String name;
    private byte[] profilePhoto;
    private Double rate;
    private String uf;
    private String city;
    private List<SubCategory> subcategories;
}
