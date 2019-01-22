package com.fin.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.bind.annotation.JsonbDateFormat;
import javax.persistence.*;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table
@Setter
@Getter
@NoArgsConstructor
public class Refill implements Serializable {
    @Id
    @SequenceGenerator(name = "refill_id", sequenceName = "refill_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "refill_id")
    private long id;

    @Column
    private double amount;

    @OneToOne
    private Wallet wallet;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonbDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    @Column(name = "date_refill")
    private Date dateRefill;

    public JsonObject toJson() {
        SimpleDateFormat formatted = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        return Json.createObjectBuilder()
                .add("id", id)
                .add("amount", amount)
                .add("wallet", wallet.toJson())
                .add("status", status.toString())
                .add("dateRefill", formatted.format(dateRefill))
                .build();
    }

    public enum Status {
        SATISFIED,
        DISSATISFIED
    }
}
