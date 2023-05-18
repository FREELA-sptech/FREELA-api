package freela.api.FREELAAPI.application.web.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    @Size(min = 2, max = 50)
    @Schema(name = "Nome", description = "Nome do usuario", example = "Joaquim")
    private String name;
    @Email
    @Schema(name = "Email", description = "Email do usuario", example = "joaquim,carvalho@sptech.school")
    private String email;
    @Size(min = 8, max = 50)
    @Schema(name = "Senha", description = "Senha do usuario", example = "#Gf44433366688")
    private String password;

    @NotNull
    @NotEmpty
    @Schema(name = "SubCategorias", description = "Lista de subCategorias")
    private ArrayList<Integer> subCategoryId;

    private String city;

    private String uf;

    private Boolean isFreelancer;

    private byte[] profilePhoto;

    private String description;

}
