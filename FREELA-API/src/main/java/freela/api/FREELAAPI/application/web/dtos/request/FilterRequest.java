package freela.api.FREELAAPI.application.web.dtos.request;

import java.util.List;

public class FilterRequest {
    private String nome;
    private List<Integer> subCategoriesId;
    private String email;
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
