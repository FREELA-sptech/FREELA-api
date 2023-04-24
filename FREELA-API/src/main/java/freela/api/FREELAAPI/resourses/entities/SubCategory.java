package freela.api.FREELAAPI.resourses.entities;

import javax.persistence.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Entity
public class SubCategory {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer id;

    @Schema(name = "Nome", description = "Nome da subCategoria", example = "Tatuagem realista")
    private String name;

    @ManyToOne
    private Category category;



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }



}
