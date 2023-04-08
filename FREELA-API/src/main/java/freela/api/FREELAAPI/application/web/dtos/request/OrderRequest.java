package freela.api.FREELAAPI.application.web.dtos.request;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class OrderRequest {
    @NotNull
    private String description;

    @NotNull
    private String title;

    @NotNull
    private Integer category;

    @NotNull
    private Double maxValue;

    @NotNull
    @NotEmpty
    private ArrayList<Integer> subCategoryIds;
}
