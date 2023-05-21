package freela.api.FREELAAPI.application.web.dtos.response;

import freela.api.FREELAAPI.resourses.entities.Category;
import freela.api.FREELAAPI.resourses.entities.SubCategory;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class OrderResponse {
    private String description;
    private String title;
    private Double maxValue;
    private String expirationTime;
    private List<SubCategory> subCategories;
    private List<byte[]> photos;

    public OrderResponse(String description, String title, Double maxValue, String expirationTime, List<SubCategory> subCategories, List<byte[]> photos) {
        this.description = description;
        this.title = title;
        this.maxValue = maxValue;
        this.expirationTime = expirationTime;
        this.subCategories = subCategories;
        this.photos = photos;
    }
}
