package ru.javawebinar.basejava.model.enumKeyTypes;

import ru.javawebinar.basejava.model.interfaces.KeyType;

public enum ContactType implements KeyType {
    TEL("Тел.:"),
    SKYPE("Skype:"),
    MAIL("Почта:"),
    LINKEDIN("Профиль Linkedin"),
    GITHUB("Профиль GitHub"),
    STACKOVERFLOW("Профиль Stackoverflow"),
    HOMEPAGE("Домашняя страница");

    private final String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
