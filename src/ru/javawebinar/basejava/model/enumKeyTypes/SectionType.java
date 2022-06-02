package ru.javawebinar.basejava.model.enumKeyTypes;

import ru.javawebinar.basejava.model.interfaces.KeyType;

public enum SectionType implements KeyType {
    PERSONAL("Личные качества"),
    OBJECTIVE("Позиция"),
    ACHIEVEMENT("Достижения"),
    QUALIFICATIONS("Квалификация"),
    EXPERIENCE("Опыт работы"),
    EDUCATION("Образование");

    private final String title;

    SectionType(String title) {
        this.title = title;
    }

    @Override
    public String getTitle() {
        return title;
    }
}
