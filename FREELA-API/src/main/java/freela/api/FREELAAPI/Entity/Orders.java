package freela.api.FREELAAPI.Entity;

import javax.persistence.*;

@Entity
public class Orders {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer id;
    private String description;
    private String title;
    private Double maxValue;

    @ManyToOne
    private Users oringinUser;

    @ManyToOne
    private Users providerUser;
    private boolean isAccepted;

    public Users getProviderUser() {
        return providerUser;
    }

    public void setProviderUser(Users providerUser) {
        this.providerUser = providerUser;
    }

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
