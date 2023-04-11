package freela.api.FREELAAPI.resourses.entities;

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
    private Category category;
    @ManyToOne
    private Users user;
    @OneToOne
    private Proposals proposals;
    private boolean isAccepted;

    public Orders(String description, String title,Category category, Double maxValue,Users user) {
        this.description = description;
        this.title = title;
        this.category = category;
        this.maxValue = maxValue;
        this.user = user;
    }

    public Orders() {
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public boolean isAccepted() {
        return isAccepted;
    }

    public void setAccepted(boolean accepted) {
        isAccepted = accepted;
    }

    public Proposals getProposals() {
        return proposals;
    }

    public void setProposals(Proposals proposals) {
        this.proposals = proposals;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
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
