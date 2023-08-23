package freela.api.FREELAAPI.application.web.dtos.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class ProposalUpdate {
    private Double proposalValue;
    private String description;
    private String expirationTime;

    public ProposalUpdate(Double proposalValue, String description, String expirationTime) {
        this.proposalValue = proposalValue;
        this.description = description;
        this.expirationTime = expirationTime;
    }
}
