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
public class Messages {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    private Users from;
    private String message;
    private String time;
    @ManyToOne
    private Chat chat;

    public Messages(Users from, String message, String time, Chat chat) {
        this.from = from;
        this.message = message;
        this.time = time;
        this.chat = chat;
    }
}
