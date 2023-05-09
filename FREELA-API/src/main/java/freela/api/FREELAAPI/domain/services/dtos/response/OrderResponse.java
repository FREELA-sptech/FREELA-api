package freela.api.FREELAAPI.domain.services.dtos.response;

import freela.api.FREELAAPI.domain.services.authentication.dto.UsuarioMapper;
import freela.api.FREELAAPI.resourses.entities.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.util.List;

@Data
public class OrderResponse {
    private Integer id;
    private String description;
    private String title;
    private Double maxValue;
    private UserDetails user;
    private Proposals proposals;
    private boolean isAccepted;
    private String expirationTime;
    private List<Category> categories;
    private List<SubCategory> subCategories;

    public OrderResponse(Order order, List<Category> categories, List<SubCategory> subCategories) {
        this.id = order.getId();
        this.description = order.getDescription();
        this.title = order.getTitle();
        this.maxValue = order.getMaxValue();
        this.user = UsuarioMapper.of(order.getUser());
        this.proposals = order.getProposals();
        this.isAccepted = order.isAccepted();
        this.expirationTime = order.getExpirationTime();
        this.categories = categories;
        this.subCategories = subCategories;
    }
}
