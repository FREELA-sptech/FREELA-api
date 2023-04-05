package freela.api.FREELAAPI.resourses.entities;

import javax.persistence.*;

@Entity
public class Proposals {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer id;
    private Double valor;

    @ManyToOne
    private Users originUser;
    private String description;
    private String photo;


    public Users getOriginUser() {return originUser;}

    public void setOriginUser(Users originUser) {this.originUser = originUser;}

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
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
