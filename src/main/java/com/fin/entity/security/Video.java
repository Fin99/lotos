package com.fin.entity.security;

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
public class Video implements Serializable {
    @Id
    @SequenceGenerator(name = "video_id", sequenceName = "video_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "video_id")
    private long id;
    @ManyToOne
    private Camera camera;
    @Column
    private String name;
    @Column
    private byte[] video;
}
