package freela.api.FREELAAPI.application.web.controllers;

import freela.api.FREELAAPI.domain.repositories.CategoryRepository;
import freela.api.FREELAAPI.domain.repositories.SubCategoryRepository;
import freela.api.FREELAAPI.resourses.entities.Category;
import freela.api.FREELAAPI.resourses.entities.SubCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/sub-categories")
public class SubCategoryController {
    @Autowired
    private SubCategoryRepository subCategoryRepository;
    @GetMapping
    public ResponseEntity<List<SubCategory>> findAll(){
        List<SubCategory> subCategories = this.subCategoryRepository.findAll();

        if(subCategories.isEmpty()){
            return ResponseEntity.status(203).body(subCategories);
        }
        return ResponseEntity.status(200).body(subCategories);
    }
}