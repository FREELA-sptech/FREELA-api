package freela.api.FREELAAPI.resourses.entities;

import javax.persistence.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
public class OrderInterest {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @Schema(name = "Categoria", description = "Categoria que ira linkar a subcategoria")
    private Category category;

    @OneToOne
    private Orders order;

    @ManyToOne
    private SubCategory subCategory;

    public OrderInterest(Category category, Orders order, SubCategory subCategory) {
        this.category = category;
        this.order = order;
        this.subCategory = subCategory;
    }
}
