package freela.api.FREELAAPI.application.web.dtos.request;

import freela.api.FREELAAPI.resourses.entities.Orders;
import freela.api.FREELAAPI.resourses.entities.Users;

import javax.persistence.ManyToOne;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

public class ProposalRequest {

    @NotNull
    @DecimalMin("0.1")
    private Double ProposalValue;

    private String description;
    private String photo;

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

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
