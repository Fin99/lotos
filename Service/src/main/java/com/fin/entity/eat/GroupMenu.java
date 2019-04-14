package com.fin.entity.eat;

import com.fin.entity.group.Group;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "group_menu")
@Setter
@Getter
@NoArgsConstructor
public class GroupMenu implements Serializable {
    @Id
    @SequenceGenerator(name = "group_menu_id", sequenceName = "group_menu_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "group_menu_id")
    private long id;
    @OneToOne
    private Group group;
    @OneToOne
    private Menu menu;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonbDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    @Column
    private Date date;
}
