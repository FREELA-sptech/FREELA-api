package freela.api.FREELAAPI.application.web.dtos.response;

import freela.api.FREELAAPI.resourses.entities.Category;
import freela.api.FREELAAPI.resourses.entities.SubCategory;

import java.util.List;

public class UserResponse {
    private String name;
    private String email;
    private byte[] profilePhoto;
    private Double rate;
    private String uf;
    private String city;
    private List<Category> categories;
    private List<SubCategory> subcategories;


    public UserResponse() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public byte[] getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(byte[] profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<SubCategory> getSubcategories() {
        return subcategories;
    }

    public void setSubcategories(List<SubCategory> subcategories) {
        this.subcategories = subcategories;
    }

    public UserResponse(String name, String email, byte[] profilePhoto, Double rate, String uf, String city, List<Category> categories, List<SubCategory> subcategories) {
        this.name = name;
        this.email = email;
        this.profilePhoto = profilePhoto;
        this.rate = rate;
        this.uf = uf;
        this.city = city;
        this.categories = categories;
        this.subcategories = subcategories;
    }
}
