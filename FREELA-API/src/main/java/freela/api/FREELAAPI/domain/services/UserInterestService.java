package freela.api.FREELAAPI.domain.services;

import freela.api.FREELAAPI.resourses.entities.Category;
import freela.api.FREELAAPI.resourses.entities.SubCategory;
import freela.api.FREELAAPI.resourses.entities.Users;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserInterestService {
    void createUserInterest (List<Integer> subCategories, Users user);
    void updateUserInterest(List<Integer> subCategories, Users user);
    List<SubCategory> getAllSubCategoriesByUser(Users user);
    List<Users> getUsersBySubcategories(List<Integer> subCategories);
    List<Category> getAllCategoriesByUser(Users users);

}
