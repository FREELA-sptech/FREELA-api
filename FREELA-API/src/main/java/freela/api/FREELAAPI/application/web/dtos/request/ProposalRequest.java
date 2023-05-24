package freela.api.FREELAAPI.application.web.dtos.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class ProposalRequest {

    @NotNull
    @DecimalMin("0.1")
    @Schema(name = "valor Proposta", description = "valor da proposta", example = "10.0")
    private Double proposalValue;
    @Schema(name = "descrição", description = "descrição da proposta", example = "minimalista e colorida")
    private String description;
    private String expirationTime;
}
