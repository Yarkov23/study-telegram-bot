package org.yarkov.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "`students`")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    @Id
    private Long id;

    @Column(name = "`fullname`")
    private String fullName;

    @Column(name = "`group`")
    private String group;

    @Column(name = "`telegramId`")
    private Integer telegramId;

    @Column(name = "`course`")
    private Integer course;

    @Enumerated(EnumType.STRING)
    private State state;

    @ManyToMany
    @JoinTable(
            name = "student_role",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;

    @OneToMany(mappedBy = "studentId")
    private Set<StudentTheme> studentThemes;

    @Override
    public String toString() {
        return "Student{" +
                "fullName='" + fullName + '\'' +
                ", group='" + group + '\'' +
                ", telegramId=" + telegramId +
                ", course=" + course +
                '}';
    }
}
