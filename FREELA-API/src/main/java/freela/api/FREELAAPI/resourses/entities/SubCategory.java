package freela.api.FREELAAPI.resourses.entities;

import javax.persistence.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class SubCategory {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer id;

    @Schema(name = "Nome", description = "Nome da subCategoria", example = "Tatuagem realista")
    private String name;

    @ManyToOne
    private Category category;

    @Override
    public String toString() {
        return "SubCategory{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", category=" + category +
                '}';
    }
}
