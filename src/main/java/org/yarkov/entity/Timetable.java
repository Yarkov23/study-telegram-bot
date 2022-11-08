package org.yarkov.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "`timetable`")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Timetable {

    @Id
    private Long id;

    @Column(name = "`date`")
    private LocalDate date;

    @Column(name = "`step`")
    private String step;

    @Column(name = "`step_status`")
    private Integer stepStatus;

    @ManyToOne
    @JoinColumn(name = "`student_theme_id`")
    private StudentTheme studentTheme;

}
