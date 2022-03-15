package br.com.bmont.sorteador.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity(name = "tb_participant")
public class Participant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name="group_id", nullable = false)
    private Group group;
    private boolean isActive;

    public Participant(String name, Group group) {
        this.name = name;
        this.group = group;
    }
}
