package ru.javawebinar.basejava.model.chapters;

import ru.javawebinar.basejava.model.enumKeyTypes.ContactType;

import java.util.EnumMap;

/**
 * The class is EnumMap type chapter of Resume to store person's contacts.
 * Search key type - ContactType.
 * Data type - String.
 */
public class Contacts extends AbstractEnumChapter<ContactType, String> {
    public Contacts() {
        super(new EnumMap<>(ContactType.class));
    }

    @Override
    protected String title(ContactType key) {
        return key.getTitle();
    }

    @Override
    protected String getDefault() {
        return "";
    }
}
