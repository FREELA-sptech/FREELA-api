package freela.api.FREELAAPI.resourses.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@Data
public class Post {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer id;
    @Schema(name = "Descrição", description = "Descrição da proposta ou do pedido", example = "Arte com traços finos!")
    private String description;
    @Schema(name = "Foto", description = "Foto do pedido")
    private String photo;
}
