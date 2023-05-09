package freela.api.FREELAAPI.domain.services.impl;

import freela.api.FREELAAPI.domain.repositories.SubCategoryRepository;
import freela.api.FREELAAPI.domain.repositories.UserInterestRepository;
import freela.api.FREELAAPI.domain.services.UserInterestService;
import freela.api.FREELAAPI.resourses.entities.SubCategory;
import freela.api.FREELAAPI.resourses.entities.UserInterest;
import freela.api.FREELAAPI.resourses.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserInterestServiceImpl implements UserInterestService {

    @Autowired
    private SubCategoryRepository subCategoryRepository;
    @Autowired
    private UserInterestRepository userInterestRepository;

    public void createUserInterest(List<Integer> subCategories,User user) {
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



    public List<SubCategory> getAllSubCategoriesByUser(User user){
        List<UserInterest> interests = this.userInterestRepository.findAllByUser(user);
        List<SubCategory> subCategories = new ArrayList<>();

        for(UserInterest interest : interests){
            subCategories.add(interest.getSubCategory());
        }
        return  subCategories;
    }

    public List<User> getUsersBySubcategories(List<Integer> subCategories) {
        List<User> user = new ArrayList<>();
        for(Integer sub : subCategories){

            if(this.subCategoryRepository.existsById(sub)){
                SubCategory subC = this.subCategoryRepository.findById(sub).get();
                List<UserInterest> interest =  this.userInterestRepository.findAllBySubCategory(subC);

                for(UserInterest inte : interest)
                user.add(inte.getUser());
            }

        }
        Set<User> clearArray = new LinkedHashSet<User>(user);
        List<User> returnList = new ArrayList<>();

        returnList.addAll(clearArray);

        return returnList;
    }
}
