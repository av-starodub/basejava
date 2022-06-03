package ru.javawebinar.basejava.model.item;

import ru.javawebinar.basejava.model.chapters.AbstractEnumChapter;
import ru.javawebinar.basejava.model.enumKeyTypes.HeaderType;

import java.util.EnumMap;

/**
 * The class is EnumMap type chapter of Item to store title of organization and link on it.
 * Search key type - HeaderType.
 * Data type - String.
 */
public class Header extends AbstractEnumChapter<HeaderType, String> {
    public Header() {
        super(new EnumMap<>(HeaderType.class));
    }

    @Override
    protected String title(HeaderType key) {
        return key.getTitle();
    }

    @Override
    protected String getDefault() {
        return "";
    }
}
