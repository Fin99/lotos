package com.fin.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table
@Setter
@Getter
@NoArgsConstructor
public class Client implements Serializable {
    @Id
    @SequenceGenerator(name = "client_id", sequenceName = "client_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "client_id")
    private long id;
    @Column
    private String username;
    @Column
    private String password;
    @Column
    private String token;

    public Client(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
