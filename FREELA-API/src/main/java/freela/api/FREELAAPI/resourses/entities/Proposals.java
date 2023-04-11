package freela.api.FREELAAPI.resourses.entities;

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
    private Double ProposalValue;

    @ManyToOne
    private Users originUser;
    private String description;
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
