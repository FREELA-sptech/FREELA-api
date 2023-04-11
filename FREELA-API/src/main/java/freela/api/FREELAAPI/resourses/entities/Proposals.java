package freela.api.FREELAAPI.resourses.entities;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

@Entity
public class Proposals {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @DecimalMin("0.1")
    @Schema(name = "Valor proposta", description = "Valor solicitado na proposta", example = "340.00")
    private Double ProposalValue;
    @ManyToOne
    @Schema(name = "Nome", description = "Nome do criador da proposta", example = "Maria Valentina")
    private Users originUser;
    @Schema(name = "Descrição", description = "Descrição da proposta", example = "Aceito este pedido, faço em 3 horas")
    private String description;
    @Schema(name = "Foto", description = "Foto do pedido")
    private String photo;


    public Users getOriginUser() {return originUser;}

    public void setOriginUser(Users originUser) {this.originUser = originUser;}

    public Double getProposalValue() {
        return ProposalValue;
    }

    public void setProposalValue(Double ProposalValue) {
        this.ProposalValue = ProposalValue;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
