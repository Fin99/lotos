package com.fin.entity.medical;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.bind.annotation.JsonbDateFormat;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "medical_book")
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
    private Character sex;
    @Column
    private String policy;
    @ManyToMany
    @JoinTable(name = "medical_book_allergy", joinColumns = @JoinColumn(name = "medical_book_id"))
    private Set<Allergy> allergySet;
    @ManyToMany
    @JoinTable(name = "medical_book_ill", joinColumns = @JoinColumn(name = "medical_book_id"))
    private Set<Ill> illSet;
    @ManyToMany
    @JoinTable(name = "medical_book_vaccination", joinColumns = @JoinColumn(name = "medical_book_id"))
    private Set<Vaccination> vaccinationSet;

    public JsonObject toJson() {
        JsonObjectBuilder builder = Json.createObjectBuilder();

        builder.add("id", id)
                .add("growth", growth)
                .add("weight", weight);

        if(sex != null){
            builder.add("sex", sex.toString());
        }
        if (dateOfBirth != null) {
            builder.add("dateOfBirth", dateOfBirth.toString());
        }
        if (policy != null) {
            builder.add("policy", policy);
        }
        if (allergySet != null) {
            JsonArrayBuilder allergyBuilder = Json.createArrayBuilder();
            for (Allergy allergy : allergySet) {
                allergyBuilder.add(allergy.toJson());
            }
            builder.add("allergy", allergyBuilder.build());
        }
        if (illSet != null) {
            JsonArrayBuilder illBuilder = Json.createArrayBuilder();
            for (Ill ill : illSet) {
                illBuilder.add(ill.toJson());
            }
            builder.add("ill", illBuilder.build());
        }
        if (vaccinationSet != null) {
            JsonArrayBuilder vaccinationBuilder = Json.createArrayBuilder();
            for (Vaccination vaccination : vaccinationSet) {
                vaccinationBuilder.add(vaccination.toJson());
            }
            builder.add("vaccination", vaccinationBuilder.build());
        }

        return builder.build();
    }
}
