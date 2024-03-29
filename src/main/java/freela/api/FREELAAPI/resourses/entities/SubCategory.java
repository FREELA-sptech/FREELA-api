package freela.api.FREELAAPI.resourses.entities;

import javax.persistence.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class SubCategory {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer id;

    @Schema(name = "Nome", description = "Nome da subCategoria", example = "Tatuagem realista")
    private String name;

    @ManyToOne
    private Category category;
}
