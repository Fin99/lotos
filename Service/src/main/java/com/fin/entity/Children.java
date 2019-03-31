package com.fin.entity;

import com.fin.entity.group.Group;
import com.fin.entity.medical.MedicalBook;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table
@Setter
@Getter
@NoArgsConstructor
public class Children implements Serializable, Jsonable {
    @Id
    @SequenceGenerator(name = "children_id", sequenceName = "children_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "children_id")
    private long id;
    @Column
    private String name;
    @Column
    private String surname;
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "medical_book_id")
    private MedicalBook medicalBook;
    @ManyToOne
    private Group group;
    @ManyToOne
    private Parent parent1;
    @ManyToOne
    private Parent parent2;
    @OneToOne(cascade = CascadeType.ALL)
    private Client client;

    public Children(String name, String surname, MedicalBook medicalBook, Group group, Parent parent1, Parent parent2, Client client) {
        this.name = name;
        this.surname = surname;
        this.medicalBook = medicalBook;
        this.group = group;
        this.parent1 = parent1;
        this.parent2 = parent2;
        this.client = client;
    }

    public JsonObject toJson() {
        JsonObjectBuilder builder = Json.createObjectBuilder();

        builder.add("id", id);

        if (name != null) {
            builder.add("name", name);
        }
        if (surname != null) {
            builder.add("surname", surname);
        }
        if (medicalBook != null) {
            builder.add("medicalBook", medicalBook.toJson());
        }
        if (group != null) {
            builder.add("group", group.toJson());
        }
        if (parent1 != null) {
            builder.add("parent1", parent1.toJson());
        }
        if (parent2 != null) {
            builder.add("parent2", parent2.toJson());
        }
        if (client != null) {
            builder.add("client", client.toJson());
        }

        return builder.build();
    }
}
