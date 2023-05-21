package freela.api.FREELAAPI.application.web.dtos.response;


import freela.api.FREELAAPI.resourses.entities.Category;
import freela.api.FREELAAPI.resourses.entities.SubCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FreelancerResponse {
    private Integer id;
    private String name;
    private byte[] profilePhoto;
    private String description;
    private Double rate;
    private String uf;
    private String city;
    private Integer closedOrders;
    private List<Category> categories;
    private List<SubCategory> subcategories;
}
