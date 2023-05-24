package freela.api.FREELAAPI.domain.services;

import freela.api.FREELAAPI.application.web.dtos.response.FreelancerResponse;
import freela.api.FREELAAPI.resourses.entities.Category;
import freela.api.FREELAAPI.resourses.entities.SubCategory;
import freela.api.FREELAAPI.resourses.entities.Users;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserInterestService {
    void createUserInterest (List<Integer> subCategories, Users user);
    void updateUserInterest(List<Integer> subCategories, Users user);
    List<SubCategory> getAllSubCategoriesByUser(Users user);
    List<Category> getAllCategoriesByUser(Users users);

}
