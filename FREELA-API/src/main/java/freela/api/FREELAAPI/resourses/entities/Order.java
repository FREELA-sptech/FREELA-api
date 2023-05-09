package freela.api.FREELAAPI.resourses.entities;

import javax.persistence.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "ORDERS")
public class Order {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer id;
    @Schema(name = "Descrição", description = "Descrição do pedido", example = "Arte com traços finos!")
    private String description;
    @Schema(name = "Titulo", description = "Titulo do pedido", example = "Tatuagem realista de rosto")
    private String title;
    @Schema(name = "Valor maximo", description = "Valor maximo a pagar", example = "150.00")
    private Double maxValue;
    @ManyToOne
    private User user;
    @OneToOne
    private Proposals proposals;
    @Schema(name = "Aceito?", description = "Se o pedido foi aceito", example = "true")
    private boolean isAccepted;
    private String expirationTime;

    public Order(String description, String title,Double maxValue,User user, String expirationTime) {
        this.description = description;
        this.title = title;
        this.maxValue = maxValue;
        this.user = user;
        this.expirationTime = expirationTime;
    }
}
