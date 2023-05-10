package freela.api.FREELAAPI.application.web.dtos.response;


import freela.api.FREELAAPI.resourses.entities.Category;
import freela.api.FREELAAPI.resourses.entities.SubCategory;

import java.util.List;

public class FreelancerResponse {
    private Integer id;
    private String name;
    private String userName;
    private String profilePhoto;
    private String description;
    private Double rate;
    private Integer closedOrders;
    private List<Category> categories;
    private List<SubCategory> subcategories;

    public FreelancerResponse() {
    }

    public FreelancerResponse(Integer id, String name, String userName, String profilePhoto, String description, Double rate, Integer closedOrders, List<Category> categories, List<SubCategory> subcategories) {
        this.id = id;
        this.name = name;
        this.userName = userName;
        this.profilePhoto = profilePhoto;
        this.description = description;
        this.rate = rate;
        this.closedOrders = closedOrders;
        this.categories = categories;
        this.subcategories = subcategories;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Integer getClosedOrders() {
        return closedOrders;
    }

    public void setClosedOrders(Integer closedOrders) {
        this.closedOrders = closedOrders;
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
}
