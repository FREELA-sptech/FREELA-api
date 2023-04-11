package freela.api.FREELAAPI.resourses.entities;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.*;

@Entity
public class OrderInterest {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @Schema(name = "Categoria", description = "Categoria que ira linkar a subcategoria")
    private Category category;

    @OneToOne
    @Schema(name = "Pedido", description = "Pedido da categoria")
    private Orders order;

    @ManyToOne
    private SubCategory subCategory;

    public OrderInterest(Category category, Orders order, SubCategory subCategory) {
        this.category = category;
        this.order = order;
        this.subCategory = subCategory;
    }

    public OrderInterest() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Orders getOrder() {
        return order;
    }

    public void setOrder(Orders order) {
        this.order = order;
    }

    public SubCategory getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(SubCategory subCategory) {
        this.subCategory = subCategory;
    }
}
