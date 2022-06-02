package ru.javawebinar.basejava.model.enumKeyTypes;

import ru.javawebinar.basejava.model.interfaces.KeyType;

public enum InfoType implements KeyType {
    START("Начало, ММ/ГГГГ"),
    END("Окончание, ММ/ГГГГ"),
    HEADER("Заголовок"),
    DESCRIPTION("Описание");

    private final String title;

    InfoType(String title) {
        this.title = title;
    }

    @Override
    public String getTitle() {
        return title;
    }
}
