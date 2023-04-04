package freela.api.FREELAAPI.Entity;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
public class Orders {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(min =10, max = 255)
    private String description;

    @Size(min =5, max = 40)
    private String title;

    @NotNull(message = "max_value is null")
    @DecimalMin(value = "0.1")
    private Double maxValue;

    @ManyToOne
    @NotNull
    private Users oringinUser;

    private boolean isAccepted;

    public boolean isAccepted() {
        return isAccepted;
    }

    public void setAccepted(boolean accepted) {
        isAccepted = accepted;
    }

    public Proposals getAcceptedProposal() {
        return acceptedProposal;
    }

    public void setAcceptedProposal(Proposals acceptedProposal) {
        this.acceptedProposal = acceptedProposal;
    }

    @OneToOne
    private Proposals acceptedProposal;

    public Users getOringinUser() {
        return oringinUser;
    }

    public void setOringinUser(Users oringinUser) {
        this.oringinUser = oringinUser;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(Double maxValue) {
        this.maxValue = maxValue;
    }


}
