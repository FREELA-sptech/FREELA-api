package freela.api.FREELAAPI.domain.services.impl;

import freela.api.FREELAAPI.domain.repositories.SubCategoryRepository;
import freela.api.FREELAAPI.domain.repositories.UserInterestRepository;
import freela.api.FREELAAPI.domain.services.UserInterestService;
import freela.api.FREELAAPI.resourses.entities.SubCategory;
import freela.api.FREELAAPI.resourses.entities.UserInterest;
import freela.api.FREELAAPI.resourses.entities.Users;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class UserInterestServiceImpl implements UserInterestService {

    @Autowired
    private SubCategoryRepository subCategoryRepository;
    @Autowired
    private UserInterestRepository userInterestRepository;

    public void createUserInterest(List<Integer> subCategories,Users user) {
        for(Integer subCategorieId : subCategories){
            Optional<SubCategory> subCategory = this.subCategoryRepository.findById(subCategorieId);
            if(subCategory.isPresent()){
                this.userInterestRepository.save(
                        new UserInterest(
                                user,
                                subCategory.get()
                ));
            }
        }
    }
}
