package ru.javawebinar.basejava.model.enumKeyTypes;

import ru.javawebinar.basejava.model.interfaces.KeyType;

public enum HeaderType implements KeyType {
    TITLE("Название"),
    LINK("Ссылка");

    private final String title;

    HeaderType(String title) {
        this.title = title;
    }

    @Override
    public String getTitle() {
        return title;
    }
}
