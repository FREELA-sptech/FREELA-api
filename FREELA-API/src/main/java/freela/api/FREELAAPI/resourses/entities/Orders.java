package freela.api.FREELAAPI.resourses.entities;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.*;

@Entity
public class Orders {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer id;
    @Schema(name = "Descrição", description = "Descrição do pedido", example = "Arte com traços finos!")
    private String description;
    @Schema(name = "Titulo", description = "Titulo do pedido", example = "Tatuagem realista de rosto")
    private String title;
    @Schema(name = "Valor maximo", description = "Valor maximo a pagar", example = "150.00")
    private Double maxValue;
    @ManyToOne
    @Schema(name = "Categoria", description = "Categoria do pedido", example = "Tatuagem")
    private Category category;
    @ManyToOne
    @Schema(name = "Apelido ou nickname", description = "Apelido ou nickname do usuario a fazer o pedido",
            example = "MarValent")
    private Users user;
    @OneToOne
    @Schema(name = "Proposta", description = "Propostas para o pedido")
    private Proposals proposals;
    @Schema(name = "Aceito?", description = "Se o pedido foi aceito", example = "true")
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
