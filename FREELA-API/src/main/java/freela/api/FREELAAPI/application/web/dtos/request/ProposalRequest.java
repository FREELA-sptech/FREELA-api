package freela.api.FREELAAPI.application.web.dtos.request;

import freela.api.FREELAAPI.resourses.entities.Orders;
import freela.api.FREELAAPI.resourses.entities.Users;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.ManyToOne;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class ProposalRequest {

    @NotNull
    @DecimalMin("0.1")
    @Schema(name = "valor Proposta", description = "valor da proposta", example = "10.0")
    private Double ProposalValue;
    @Schema(name = "descrição", description = "descrição da proposta", example = "minimalista e colorida")
    private String description;
    private LocalDate deadline_date;

    public LocalDate getDeadline_date() {
        return deadline_date;
    }

    public void setDeadline_date(LocalDate deadline_date) {
        this.deadline_date = deadline_date;
    }

    public Double getProposalValue() {
        return ProposalValue;
    }

    public void setProposalValue(Double proposalValue) {
        ProposalValue = proposalValue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
