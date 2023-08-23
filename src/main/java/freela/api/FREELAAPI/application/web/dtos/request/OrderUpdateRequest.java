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
    private ArrayList<Integer> subCategoryId;
    private String expirationTime;

}
