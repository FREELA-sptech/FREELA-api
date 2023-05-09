package freela.api.FREELAAPI.resourses.entities;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
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
    private User originUser;
    @Schema(name = "Descrição", description = "Descrição da proposta", example = "Aceito este pedido, faço em 3 horas")
    private String description;
    @Schema(name = "Foto", description = "Foto do pedido")
    private String photo;

    private Integer destined_order;

    public Proposals(Double proposalValue, User originUser, String description, String photo, Integer destined_order) {
        ProposalValue = proposalValue;
        this.originUser = originUser;
        this.description = description;
        this.photo = photo;
        this.destined_order = destined_order;
    }
}
