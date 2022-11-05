package org.yarkov.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "`timetable`")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Timetable {

    @Id
    private Long id;

    @Column(name = "`caption`")
    private String caption;

    @Column(name = "`date`")
    private LocalDate date;

    @Column(name = "`step`")
    private String step;

    @Column(name = "`step_status`")
    private Integer stepStatus;

    @OneToMany(mappedBy = "timetable")
    private Set<StudentTheme> studentTheme;

}
