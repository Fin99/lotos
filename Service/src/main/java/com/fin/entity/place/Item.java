package com.fin.entity.place;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table
@Setter
@Getter
@NoArgsConstructor
public class Item implements Serializable {
    @Id
    @SequenceGenerator(name = "item_id", sequenceName = "item_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "item_id")
    private long id;
    @ManyToOne
    private Place place;
    @Temporal(TemporalType.TIMESTAMP)
    @JsonbDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    @Column(name = "shelf_life")
    private Date shelfLife;
    @Column
    private String name;
    @Column
    private double price;
    @Column
    private String note;
}
