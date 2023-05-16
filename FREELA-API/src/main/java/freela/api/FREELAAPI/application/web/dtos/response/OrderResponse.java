package freela.api.FREELAAPI.application.web.dtos.response;

import freela.api.FREELAAPI.resourses.entities.Category;
import freela.api.FREELAAPI.resourses.entities.SubCategory;

import java.util.List;

public class OrderResponse {
    private String description;
    private String title;
    private Double maxValue;
    private Category category;
    private List<SubCategory> subCategories;
    private byte[] photo;

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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<SubCategory> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(List<SubCategory> subCategories) {
        this.subCategories = subCategories;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public OrderResponse(String description, String title, Double maxValue, Category category, List<SubCategory> subCategories, byte[] photo) {
        this.description = description;
        this.title = title;
        this.maxValue = maxValue;
        this.category = category;
        this.subCategories = subCategories;
        this.photo = photo;
    }
}
