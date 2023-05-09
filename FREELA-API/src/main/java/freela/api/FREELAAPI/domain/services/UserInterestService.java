package freela.api.FREELAAPI.domain.services;

import freela.api.FREELAAPI.resourses.entities.SubCategory;
import freela.api.FREELAAPI.resourses.entities.User;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserInterestService {
    void createUserInterest (List<Integer> subCategories, User user);
    List<SubCategory> getAllSubCategoriesByUser(User user);
    List<User> getUsersBySubcategories(List<Integer> subCategories);

}
