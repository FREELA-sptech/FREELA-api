package freela.api.FREELAAPI.application.web.dtos.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class ProposalUpdate {
    private Double proposalValue;
    private String description;
    private LocalDate deadline_date;

    public ProposalUpdate(Double proposalValue, String description, LocalDate deadline_date) {
        this.proposalValue = proposalValue;
        this.description = description;
        this.deadline_date = deadline_date;
    }
}
