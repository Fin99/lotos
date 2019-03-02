package com.fin.entity;

import com.fin.entity.group.Group;
import com.fin.entity.medical.MedicalBook;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(schema = "lotos")
@Setter
@Getter
@NoArgsConstructor
public class    Children implements Serializable {
    @Id
    @SequenceGenerator(schema = "lotos", name = "children_id", sequenceName = "children_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "children_id")
    private long id;
    @Column
    private String name;
    @Column
    private String surname;
    @OneToOne
    @JoinColumn(name = "medical_book_id")
    private MedicalBook medicalBook;
    @OneToOne
    private Group group;
    @ManyToOne
    private Parent parent1;
    @ManyToOne
    private Parent parent2;
    @OneToOne
    private Client client;
}
