package freela.api.FREELAAPI.resourses.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    private Users freelancerUser;
    @ManyToOne
    private Users clientUser;
    @ManyToOne
    private Orders order;
    private String lastUpdate;

    public Chat(Users freelancerUser, Users clientUser, Orders order, String lastUpdate) {
        this.freelancerUser = freelancerUser;
        this.clientUser = clientUser;
        this.order = order;
        this.lastUpdate = lastUpdate;
    }
}
