package freela.api.FREELAAPI.application.web.dtos.request;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public class FilterRequest {
    @Schema(name = "nome", description = "nome do filtro", example = "Preço")
    private String nome;
    @Schema(name = "Lista", description = "Lista de subcategorias", example = "Anime")
    private List<Integer> subCategoriesId;
    @Schema(name = "Email", description = "Email do usuario para o login", example = "nome.sobrenome@gmail.com")
    private String email;
    @Schema(name = "Usuario ou nickname", description = "nome de usuario ou nickname de usuario", example = "Joa232")
    private String userName;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Integer> getSubCategoriesId() {
        return subCategoriesId;
    }

    public void setSubCategoriesId(List<Integer> subCategoriesId) {
        this.subCategoriesId = subCategoriesId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
