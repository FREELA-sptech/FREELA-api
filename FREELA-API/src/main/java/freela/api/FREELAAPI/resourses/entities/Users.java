    package freela.api.FREELAAPI.resourses.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.InputStream;

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
    @Lob
    private byte[] profilePhoto;
    private String description;
    private String uf;
    private String city;
    private Boolean isFreelancer;
    private Double rate;
}
