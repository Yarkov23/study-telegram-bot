package org.yarkov.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "`subjects`")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Subject {

    @Id
    private Long id;

    @Column(name = "`caption`")
    private String caption;

    @OneToMany(mappedBy = "subjectId")
    private Set<Theme> themes;

}
