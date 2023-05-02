package freela.api.FREELAAPI.application.web.dtos.request;

import java.util.List;

public class SubCategoriesRequest {
    private List<Integer> subCategories;

    public SubCategoriesRequest(List<Integer> subCategories) {
        this.subCategories = subCategories;
    }

    public SubCategoriesRequest() {
    }

    public List<Integer> getSubCategories() {
        return subCategories;
    }
    public void setSubCategories(List<Integer> subCategories) {
        this.subCategories = subCategories;
    }
}
