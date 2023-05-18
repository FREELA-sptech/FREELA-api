package freela.api.FREELAAPI.domain.services.impl;

import freela.api.FREELAAPI.application.web.dtos.response.FreelancerResponse;
import freela.api.FREELAAPI.domain.repositories.SubCategoryRepository;
import freela.api.FREELAAPI.domain.repositories.UserInterestRepository;
import freela.api.FREELAAPI.domain.services.UserInterestService;
import freela.api.FREELAAPI.resourses.entities.Category;
import freela.api.FREELAAPI.resourses.entities.SubCategory;
import freela.api.FREELAAPI.resourses.entities.UserInterest;
import freela.api.FREELAAPI.resourses.entities.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
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
                                subCategory.get(),
                                subCategory.get().getCategory()
                ));
            }
        }
    }

    public void updateUserInterest(List<Integer> subCategories, Users user) {
        List<UserInterest> existingInterests = this.userInterestRepository.findAllByUser(user);

        existingInterests.forEach(userInterest -> {
            if (!subCategories.contains(userInterest.getSubCategory().getId())) {
                this.userInterestRepository.delete(userInterest);
            }
        });

        for (Integer subCategoryId : subCategories) {
            boolean interestExists = existingInterests.stream()
                    .anyMatch(userInterest -> userInterest.getSubCategory().getId().equals(subCategoryId));

            if (!interestExists) {
                Optional<SubCategory> subCategoryOptional = subCategoryRepository.findById(subCategoryId);
                if(subCategoryOptional.isPresent()){
                    this.userInterestRepository.save(
                        new UserInterest(
                                user,
                                subCategoryOptional.get(),
                                subCategoryOptional.get().getCategory()
                        )
                    );
                }
            }
        }
    }

//    public FreelancerResponse getFreelancerUser(Users user){
//        return new FreelancerResponse(
//                user.getId()),
//                user.getName(),
//                user.getUserName(),
//                user.getProfilePhoto(),
//                user.getDescription(),
//                user.
//    }



    public List<SubCategory> getAllSubCategoriesByUser(Users user){
        List<UserInterest> interests = this.userInterestRepository.findAllByUser(user);
        List<SubCategory> subCategories = new ArrayList<>();

        for(UserInterest interest : interests){
            subCategories.add(interest.getSubCategory());
        }
        return  subCategories;
    }

    public List<Category> getAllCategoriesByUser(Users users){
        List<SubCategory> subCategories = this.getAllSubCategoriesByUser(users);
        List<Category> categories = new ArrayList<>();
        for (SubCategory subCategory : subCategories){
            categories.add(subCategory.getCategory());
        }
        return categories;
    }

    public List<Users> getUsersBySubcategories(List<Integer> subCategories) {
        List<Users> user = new ArrayList<>();
        for(Integer sub : subCategories){

            if(this.subCategoryRepository.existsById(sub)){
                SubCategory subC = this.subCategoryRepository.findById(sub).get();
                List<UserInterest> interest =  this.userInterestRepository.findAllBySubCategory(subC);

                for(UserInterest inte : interest)
                user.add(inte.getUser());
            }

        }
        Set<Users> clearArray = new LinkedHashSet<Users>(user);
        List<Users> returnList = new ArrayList<>();

        returnList.addAll(clearArray);

        return returnList;
    }
}
