package freela.api.FREELAAPI.domain.services.impl;

import freela.api.FREELAAPI.application.web.dtos.response.FreelancerResponse;
import freela.api.FREELAAPI.domain.repositories.SubCategoryRepository;
import freela.api.FREELAAPI.domain.repositories.UserInterestRepository;
import freela.api.FREELAAPI.domain.services.AvaliationService;
import freela.api.FREELAAPI.domain.services.OrderService;
import freela.api.FREELAAPI.domain.services.UserInterestService;
import freela.api.FREELAAPI.domain.services.UserService;
import freela.api.FREELAAPI.domain.services.authentication.dto.TokenDetailsDto;
import freela.api.FREELAAPI.domain.services.mapper.UsuarioMapper;
import freela.api.FREELAAPI.resourses.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserInterestServiceImpl implements UserInterestService {

    private SubCategoryRepository subCategoryRepository;
    private UserInterestRepository userInterestRepository;
    private AvaliationService avaliationService;
    private OrderService orderService;

    public UserInterestServiceImpl(
            SubCategoryRepository subCategoryRepository,
            UserInterestRepository userInterestRepository,
            AvaliationService avaliationService,
            OrderService orderService
    ) {
        this.subCategoryRepository = subCategoryRepository;
        this.userInterestRepository = userInterestRepository;
        this.avaliationService = avaliationService;
        this.orderService = orderService;
    }

    public void createUserInterest(List<Integer> subCategories, Users user) {
        for (Integer subCategorieId : subCategories) {
            Optional<SubCategory> subCategory = this.subCategoryRepository.findById(subCategorieId);
            if (subCategory.isPresent()) {
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
                if (subCategoryOptional.isPresent()) {
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


    public List<SubCategory> getAllSubCategoriesByUser(Users user) {
        List<UserInterest> interests = this.userInterestRepository.findAllByUser(user);
        List<SubCategory> subCategories = new ArrayList<>();

        for (UserInterest interest : interests) {
            subCategories.add(interest.getSubCategory());
        }
        return subCategories;
    }

    public List<Category> getAllCategoriesByUser(Users users) {
        List<SubCategory> subCategories = this.getAllSubCategoriesByUser(users);
        List<Category> categories = new ArrayList<>();
        for (SubCategory subCategory : subCategories) {
            categories.add(subCategory.getCategory());
        }
        return categories;
    }

    public FreelancerResponse getFreelancerUser(Users user) {
        Double rate = avaliationService.getUserAvaliation(user);

        List<Orders> concludedOrders = orderService.getConcludedOrders(user);

        List<SubCategory> subCategories = getAllSubCategoriesByUser(user);

        return UsuarioMapper.freelancerResponse(user, rate, concludedOrders.size(), subCategories);
    }
}
