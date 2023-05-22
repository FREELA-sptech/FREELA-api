package freela.api.FREELAAPI.application.web.dtos.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@NoArgsConstructor
public class OrderUpdateRequest {
     private String description;
        private String title;
        private Double maxValue;
        private Integer category;
        private ArrayList<Integer> subCategoriesIds;

    public OrderUpdateRequest(String description, String title, Double maxValue, Integer category, ArrayList<Integer> subCategoriesIds) {
        this.description = description;
        this.title = title;
        this.maxValue = maxValue;
        this.category = category;
        this.subCategoriesIds = subCategoriesIds;
    }
}
