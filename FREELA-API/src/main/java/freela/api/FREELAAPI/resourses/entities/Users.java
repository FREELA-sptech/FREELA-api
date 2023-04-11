package freela.api.FREELAAPI.resourses.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Users {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer id;
    @Schema(name = "Nome", description = "Nome do usuário", example = "João Cardoso")
    private String name;
    @Schema(name = "Email", description = "Email do usuário", example = "joao.cardoso@sptech.school")
    private String email;
    @Schema(name = "Senha", description = "Senha do usuário", example = "#Gf38756798711")
    private String password;
    @Schema(name = "Apelido ou Nickname", description = "Apelido ou nickname do usuário", example = "joCard")
    private String userName;

    public Users(String name, String email, String password, String userName) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.userName = userName;
    }
}
