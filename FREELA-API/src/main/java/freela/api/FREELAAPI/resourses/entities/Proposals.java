package freela.api.FREELAAPI.resourses.entities;

import org.apache.catalina.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
public class    Proposals {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @NotNull
    private Users originUser;

    private Double estimatedValue;
    private LocalDate estimatedDeadLineDate;
    private String description;
    private String photo;


    public Double getEstimatedValue() {
        return estimatedValue;
    }

    public Users getOriginUser() {
        return originUser;
    }

    public void setOriginUser(Users originUser) {
        this.originUser = originUser;
    }

    public void setEstimatedValue(Double estimatedValue) {
        this.estimatedValue = estimatedValue;
    }

    public LocalDate getEstimatedDeadLineDate() {
        return estimatedDeadLineDate;
    }

    public void setEstimatedDeadLineDate(LocalDate estimatedDeadLineDate) {
        this.estimatedDeadLineDate = estimatedDeadLineDate;
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
