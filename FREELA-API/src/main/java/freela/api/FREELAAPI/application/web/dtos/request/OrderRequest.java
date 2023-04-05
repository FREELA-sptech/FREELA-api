package freela.api.FREELAAPI.application.web.dtos.request;

import freela.api.FREELAAPI.resourses.entities.Category;

import javax.validation.constraints.NotNull;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class OrderRequest {
    @NotNull
    private String description;

    @NotNull
    private String title;

    @NotNull
    private Category category;

    @NotNull
    private Double maxValue;

    @NotNull
    private ArrayList<Integer> subCategoryIds = new ArrayList<>();


    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
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
