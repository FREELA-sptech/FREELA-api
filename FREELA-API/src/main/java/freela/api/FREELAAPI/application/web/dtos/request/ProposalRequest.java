package freela.api.FREELAAPI.application.web.dtos.request;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.ManyToOne;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

public class ProposalRequest {

    @NotNull
    @DecimalMin("0.1")
    @Schema(name = "valor Proposta", description = "valor da proposta", example = "10.0")
    private Double ProposalValue;
    @Schema(name = "descrição", description = "descrição da proposta", example = "minimalista e colorida")
    private String description;
    @Schema(name = "foto", description = "foto da proposta", example = "sequencia de alfanumericos")
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
