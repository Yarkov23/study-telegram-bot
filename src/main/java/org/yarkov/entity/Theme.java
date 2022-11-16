package org.yarkov.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "`themes`")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Theme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "`caption`")
    private String caption;

    @ManyToOne
    @JoinColumn(name = "`subject_id`", nullable = false)
    private Subject subjectId;

    @OneToMany(mappedBy = "themeId")
    private Set<StudentTheme> studentThemes;

    @Override
    public String toString() {
        return "Theme{" +
                "caption='" + caption + '\'' +
                ", subjectId=" + subjectId +
                '}';
    }
}
