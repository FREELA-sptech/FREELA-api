package freela.api.FREELAAPI.resourses.entities;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
public class Proposals {

    public Proposals() {
    }

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

    @Future
    private LocalDate deadline_date;

    private Integer destined_order;

    public Proposals(Double proposalValue, Users originUser, String description, LocalDate deadline_date, Integer destined_order) {
        ProposalValue = proposalValue;
        this.originUser = originUser;
        this.description = description;
        this.deadline_date = deadline_date;
        this.destined_order = destined_order;
    }
}
