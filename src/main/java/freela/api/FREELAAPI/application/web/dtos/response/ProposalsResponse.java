package freela.api.FREELAAPI.application.web.dtos.response;

import freela.api.FREELAAPI.resourses.entities.Users;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProposalsResponse {
    private Integer id;
    private Double proposalValue;
    private UserOrderResponse originUser;
    private String description;
    private String expirationTime;
    private Integer destinedOrder;
    private Boolean isAccepted;
    private Boolean isRefused;
}
