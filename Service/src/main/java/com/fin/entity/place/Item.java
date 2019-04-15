package com.fin.entity.place;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.bind.annotation.JsonbDateFormat;
import javax.persistence.*;
import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeParseException;
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
    @ManyToOne(cascade = CascadeType.PERSIST)
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

    public JsonObject toJson() {
        JsonObjectBuilder builder = Json.createObjectBuilder();

        builder.add("id", id)
                .add("price", price);

        if (name != null) {
            builder.add("name", name);
        }
        if (shelfLife != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
            builder.add("shelfLife", dateFormat.format(shelfLife));
        }
        if (note != null) {
            builder.add("note", note);
        }
        if (place != null) {
            builder.add("place", place.toJson());
        }
        return builder.build();
    }
}
