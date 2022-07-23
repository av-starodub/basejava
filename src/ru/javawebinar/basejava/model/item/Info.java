package ru.javawebinar.basejava.model.item;

import ru.javawebinar.basejava.model.chapters.AbstractEnumChapter;
import ru.javawebinar.basejava.model.enumKeyTypes.InfoType;

import java.io.Serial;
import java.util.EnumMap;

/**
 * The class is EnumMap type chapter that represent an element in the ListInfoSection field of Item
 * to store block of information about person's period of study or work in a particular organization.
 * Search key type - InfoType.
 * Data type - String.
 */
public class Info extends AbstractEnumChapter<InfoType, String> {
    @Serial
    private static final long serialVersionUID = 1L;

    public Info() {
        super(new EnumMap<>(InfoType.class));
    }

    @Override
    protected String title(InfoType key) {
        return key.getTitle();
    }

    @Override
    protected String getDefault() {
        return "";
    }
}
