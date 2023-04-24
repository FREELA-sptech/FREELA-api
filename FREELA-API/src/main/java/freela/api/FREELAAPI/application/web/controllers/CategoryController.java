package freela.api.FREELAAPI.application.web.controllers;

import freela.api.FREELAAPI.domain.repositories.CategoryRepository;
import freela.api.FREELAAPI.resourses.entities.Category;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    @ApiResponses({
            @ApiResponse(responseCode = "203", description =
                    "Lista vazia.", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "200", description = "Lista com as categorias.")
    })
    @GetMapping
    public ResponseEntity<List<Category>> findAll(){
        List<Category> categories = this.categoryRepository.findAll();

        if(categories.isEmpty()){
            return ResponseEntity.status(203).body(categories);
        }
        return ResponseEntity.status(200).body(categories);

    }


}
