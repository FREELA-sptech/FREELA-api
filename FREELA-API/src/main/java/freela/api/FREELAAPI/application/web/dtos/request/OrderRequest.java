package freela.api.FREELAAPI.application.web.dtos.request;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;

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

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public ArrayList<Integer> getSubCategoryIds() {
        return subCategoryIds;
    }

    public void setSubCategoryIds(ArrayList<Integer> subCategoryIds) {
        this.subCategoryIds = subCategoryIds;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(Double maxValue) {
        this.maxValue = maxValue;
    }
}
