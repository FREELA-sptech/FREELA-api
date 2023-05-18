package freela.api.FREELAAPI.application.web.dtos.request;

import java.time.LocalDate;

public class ProposalUpdate {
    private Double proposalValue;
    private String description;
    private LocalDate deadline_date;

    public Double getProposalValue() {
        return proposalValue;
    }

    public void setProposalValue(Double proposalValue) {
        this.proposalValue = proposalValue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDeadline_date() {
        return deadline_date;
    }

    public void setDeadline_date(LocalDate deadline_date) {
        this.deadline_date = deadline_date;
    }

    public ProposalUpdate(Double proposalValue, String description, LocalDate deadline_date) {
        this.proposalValue = proposalValue;
        this.description = description;
        this.deadline_date = deadline_date;
    }
}
