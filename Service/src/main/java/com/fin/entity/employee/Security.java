package com.fin.entity.employee;

import com.fin.entity.security.Camera;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(schema = "lotos")
@Setter
@Getter
@NoArgsConstructor
public class Security implements Serializable {
    @Id
    @OneToOne
    private Employee employee;

    @ManyToMany
    @JoinTable(name = "security_camera")
    private Set<Camera> cameraSet;

    public Security(Employee employee) {
        this.employee = employee;
    }
}
