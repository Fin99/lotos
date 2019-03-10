package com.fin.entity;

import com.fin.security.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(schema = "lotos")
@Setter
@Getter
@NoArgsConstructor
public class Client implements Serializable {
    @Id
    @SequenceGenerator(schema = "lotos", name = "client_id", sequenceName = "client_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "client_id")
    private long id;
    @Column
    private String username;
    @Column
    private String password;
    @Column
    private String token;
    @Enumerated(EnumType.STRING)
    private Role role;

    public Client(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public JsonObject toJson() {
        JsonObjectBuilder builder = Json.createObjectBuilder();

        builder.add("id", id);

        if (username != null) {
            builder.add("username", username);
        }
        if (password != null) {
            builder.add("password", password);
        }
        if (token != null) {
            builder.add("token", token);
        }
        if (role != null) {
            builder.add("role", role.toString());
        }

        return builder.build();
    }
}
