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
@Table
@Setter
@Getter
@NoArgsConstructor
public class GroupMenu implements Serializable {
    @EmbeddedId
    private GroupMenuId groupMenuId;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonbDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    @Column
    private Date date;

    @Embeddable
    public static class GroupMenuId implements Serializable {
        private Group group;
        private Menu menu;
    }
}
