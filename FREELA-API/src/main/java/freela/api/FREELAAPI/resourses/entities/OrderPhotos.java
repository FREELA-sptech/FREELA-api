package freela.api.FREELAAPI.resourses.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Data
public class OrderPhotos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    private Orders order;

    private byte[] photo;

    public OrderPhotos(Orders order, byte[] photo) {
        this.order = order;
        this.photo = photo;
    }
}
