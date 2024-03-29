package freela.api.FREELAAPI.application.web.dtos.response;

import freela.api.FREELAAPI.resourses.entities.SubCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserOrderResponse {
    private Integer id;
    private String name;
    private byte[] profilePhoto;
    private Double rate;
}
