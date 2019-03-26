package com.fin.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.bind.annotation.JsonbDateFormat;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table
@Setter
@Getter
@NoArgsConstructor
public class Message implements Serializable {
    @Id
    @SequenceGenerator(name = "message_id", sequenceName = "message_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "message_id")
    private long id;
    @ManyToOne
    private Client sender;
    @ManyToOne
    private Client receiver;
    @Column
    private String textMessage;
    @Temporal(TemporalType.TIMESTAMP)
    @JsonbDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    @Column
    private Date date;

    public Message(Client sender, Client receiver, String textMessage, Date date) {
        this.sender = sender;
        this.receiver = receiver;
        this.textMessage = textMessage;
        this.date = date;
    }

    public JsonObject toJson() {
        JsonObjectBuilder builder = Json.createObjectBuilder();

        builder.add("id", id);

        if (sender != null) {
            builder.add("sender", sender.toJson());
        }
        if (receiver != null) {
            builder.add("receiver", receiver.toJson());
        }
        if (textMessage != null) {
            builder.add("textMessage", textMessage);
        }
        if (date != null) {
            builder.add("date", date.toString());
        }

        return builder.build();
    }
}
