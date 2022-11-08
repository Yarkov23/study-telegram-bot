package org.yarkov.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.yarkov.entity.Theme;

@Repository
public interface ThemeRepo extends JpaRepository<Theme, Long> {
}
