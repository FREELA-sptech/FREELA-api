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

    public Users(Integer id, String name, String email, String password, byte[] profilePhoto, String description, String uf, String city, Boolean isFreelancer) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.profilePhoto = profilePhoto;
        this.description = description;
        this.uf = uf;
        this.city = city;
        this.isFreelancer = isFreelancer;
    }

    public Users(String name, String email, String password, byte[] profilePhoto, String description, String uf, String city, Boolean isFreelancer) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.profilePhoto = profilePhoto;
        this.description = description;
        this.uf = uf;
        this.city = city;
        this.isFreelancer = isFreelancer;
    }
}
