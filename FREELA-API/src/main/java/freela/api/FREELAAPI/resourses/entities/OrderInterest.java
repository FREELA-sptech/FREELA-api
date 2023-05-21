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
    private Orders order;

    @ManyToOne
    private SubCategory subCategory;

    public OrderInterest(Orders order, SubCategory subCategory) {
        this.order = order;
        this.subCategory = subCategory;
    }
}
