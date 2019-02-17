package com.fin.entity.medical;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
@Table
@Setter
@Getter
@NoArgsConstructor
public class MedicalBook implements Serializable {
    @Id
    @SequenceGenerator(name = "medical_book_id", sequenceName = "medical_book_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "medical_book_id")
    private long id;
    @Column
    private double growth;
    @Column
    private double weight;
    @Temporal(TemporalType.TIMESTAMP)
    @JsonbDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    @Column(name = "date_of_birth")
    private Date dateOfBirth;
    @Column
    private char sex;
    @Column
    private String policy;
    @ManyToMany
    @JoinTable(name = "medical_book_allergy")
    private Set<Allergy> allergySet;
    @ManyToMany
    @JoinTable(name = "medical_book_ill")
    private Set<Ill> illSet;
    @ManyToMany
    @JoinTable(name = "medical_book_vacination")
    private Set<Vaccination> vaccinationSet;
}
