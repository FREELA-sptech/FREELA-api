package freela.api.FREELAAPI.application.web.dtos.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class FilterRequest {
    @Schema(name = "nome", description = "nome do filtro", example = "Preço")
    private String nome;
    @Schema(name = "Lista", description = "Lista de subcategorias", example = "Anime")
    private List<Integer> subCategoriesId;
    @Schema(name = "Email", description = "Email do usuario para o login", example = "nome.sobrenome@gmail.com")
    private String email;
    @Schema(name = "Usuario ou nickname", description = "nome de usuario ou nickname de usuario", example = "Joa232")
    private String userName;
}
