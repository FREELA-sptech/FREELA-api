package freela.api.FREELAAPI.application.web.dtos.response;

import freela.api.FREELAAPI.resourses.entities.Category;
import freela.api.FREELAAPI.resourses.entities.SubCategory;
import freela.api.FREELAAPI.resourses.entities.Users;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class OrderResponse {
    private Integer id;
    private String description;
    private String title;
    private Double maxValue;
    private Users user;
    private String expirationTime;
    private List<SubCategory> subCategories;
    private List<byte[]> photos;

    public OrderResponse(
            Integer id,
            String description,
            String title,
            Double maxValue,
            Users user,
            String expirationTime,
            List<SubCategory> subCategories,
            List<byte[]> photos
    ) {
        this.id = id;
        this.description = description;
        this.title = title;
        this.maxValue = maxValue;
        this.user = user;
        this.expirationTime = expirationTime;
        this.subCategories = subCategories;
        this.photos = photos;
    }
}
