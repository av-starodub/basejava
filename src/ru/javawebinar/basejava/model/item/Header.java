package ru.javawebinar.basejava.model.item;

import ru.javawebinar.basejava.model.chapters.AbstractEnumChapter;
import ru.javawebinar.basejava.model.enumKeyTypes.HeaderType;

import java.io.Serial;
import java.util.EnumMap;

/**
 * The class is EnumMap type chapter of Item to store title of organization and link on it.
 * Search key type - HeaderType.
 * Data type - String.
 */
public class Header extends AbstractEnumChapter<HeaderType, String> {
    @Serial
    private static final long serialVersionUID = 1L;

    public Header() {
        super(new EnumMap<>(HeaderType.class){{
            put(HeaderType.TITLE, "");
            put(HeaderType.LINK, "");
        }});
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
