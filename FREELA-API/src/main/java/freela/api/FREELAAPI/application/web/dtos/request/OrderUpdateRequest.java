package freela.api.FREELAAPI.application.web.dtos.request;

import freela.api.FREELAAPI.resourses.entities.Category;

import java.util.ArrayList;
import java.util.List;

public class OrderUpdateRequest {
     private String description;
        private String title;
        private Double maxValue;
        private Integer category;
        private ArrayList<Integer> subCategoriesIds;

    public String getDescription() {
        return description;
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

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public ArrayList<Integer> getSubCategoriesIds() {
        return subCategoriesIds;
    }

    public void setSubCategoriesIds(ArrayList<Integer> subCategoriesIds) {
        this.subCategoriesIds = subCategoriesIds;
    }

    public OrderUpdateRequest(String description, String title, Double maxValue, Integer category, ArrayList<Integer> subCategoriesIds) {
        this.description = description;
        this.title = title;
        this.maxValue = maxValue;
        this.category = category;
        this.subCategoriesIds = subCategoriesIds;
    }
}
