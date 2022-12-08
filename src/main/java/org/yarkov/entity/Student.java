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

    @Column(name = "`telegramid`")
    private Integer telegramId;

    @Column(name = "`course`")
    private Integer course;

    @Enumerated(EnumType.STRING)
    private State state;

    @ManyToOne
    @JoinColumn(name = "`role_id`")
    private Role role;

    @OneToMany(mappedBy = "studentId")
    private Set<StudentTheme> studentThemes;

    @Column(name = "`step`")
    private String step;

    @Column(name = "`state_object`", columnDefinition = "json")
    private String stateObject;

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
