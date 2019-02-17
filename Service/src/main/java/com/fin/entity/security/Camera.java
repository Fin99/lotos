package com.fin.entity.security;

import com.fin.entity.place.Place;
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
public class Camera implements Serializable {
    @Id
    @SequenceGenerator(name = "camera_id", sequenceName = "camera_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "camera_id")
    private long id;
    @ManyToOne
    private Place place;
}
