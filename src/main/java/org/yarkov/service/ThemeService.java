package org.yarkov.service;

import org.springframework.stereotype.Service;
import org.yarkov.entity.Theme;
import org.yarkov.repository.ThemeRepo;

import java.util.List;
import java.util.Optional;

@Service
public class ThemeService {

    private ThemeRepo themeRepo;

    public ThemeService(ThemeRepo themeRepo) {
        this.themeRepo = themeRepo;
    }


    public List<Theme> findAll() {
        return themeRepo.findAll();
    }

    public void save(Theme theme) {
        if (theme == null)
            return;
        themeRepo.save(theme);
    }


    public Theme findThemeByCaption(String caption) {
        Optional<Theme> themeByCaption = themeRepo.findThemeByCaption(caption);
        return themeByCaption.orElse(null);
    }

    public void update(Theme theme, String newCaption) {
        theme.setCaption(newCaption);
        themeRepo.save(theme);
    }

}
