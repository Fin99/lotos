package com.fin.entity.employee;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table
@Setter
@Getter
@NoArgsConstructor
public class Chief implements Serializable {
    @Id
    @OneToOne
    private Employee employee;
}
