package org.yarkov.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

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

    @OneToMany(mappedBy = "studentTheme")
    private Set<Timetable> timetables;

    @Override
    public String toString() {
        return "StudentTheme{" +
                "studentId=" + studentId +
                ", themeId=" + themeId +
                ", mark=" + mark +
                ", appointmentDate=" + appointmentDate +
                '}';
    }
}
