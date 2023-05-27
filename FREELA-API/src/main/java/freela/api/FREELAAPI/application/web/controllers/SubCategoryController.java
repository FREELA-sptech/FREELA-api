package freela.api.FREELAAPI.application.web.controllers;

import freela.api.FREELAAPI.application.web.helpers.LerArquivoTxt;
import freela.api.FREELAAPI.domain.repositories.CategoryRepository;
import freela.api.FREELAAPI.domain.repositories.SubCategoryRepository;
import freela.api.FREELAAPI.domain.services.SubCategoryService;
import freela.api.FREELAAPI.resourses.entities.SubCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/sub-categories")
public class SubCategoryController {
    private final SubCategoryRepository subCategoryRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public SubCategoryController(SubCategoryRepository subCategoryRepository) {
        this.subCategoryRepository = subCategoryRepository;
    }

    @GetMapping
    public ResponseEntity<List<SubCategory>> findAll(){
        List<SubCategory> subCategories = this.subCategoryRepository.findAll();

        if(subCategories.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(subCategories);
    }

    @PostMapping("/txt")
    public void lerTxt(MultipartFile txt){
        try {
            LerArquivoTxt leitor = new LerArquivoTxt(categoryRepository,subCategoryRepository);
            leitor.leArquivo(txt.getBytes());

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
