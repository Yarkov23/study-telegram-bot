package org.yarkov.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "`student_theme`")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StudentTheme {

    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student studentId;

    @ManyToOne
    @JoinColumn(name = "theme_id")
    private Theme themeId;

    @Column(name = "`mark`")
    private Integer mark;

    @Column(name = "`appointment_date`")
    private LocalDate appointmentDate;

    @ManyToOne
    @JoinColumn(name = "`student_theme_id`", nullable = false)
    private Timetable timetable;

}
