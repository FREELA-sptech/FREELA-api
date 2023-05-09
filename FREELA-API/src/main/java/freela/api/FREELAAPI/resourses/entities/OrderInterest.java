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

    @ManyToOne
    private Category category;

    @ManyToOne
    private Order order;

    @ManyToOne
    private SubCategory subCategory;

    public OrderInterest(Category category, Order order, SubCategory subCategory) {
        this.category = category;
        this.order = order;
        this.subCategory = subCategory;
    }
}
