package ru.javawebinar.basejava.model.chapters;

import ru.javawebinar.basejava.model.enumKeyTypes.SectionType;
import ru.javawebinar.basejava.model.interfaces.Section;
import ru.javawebinar.basejava.model.sections.TextSection;

import java.io.Serial;
import java.util.EnumMap;

/**
 * The class is EnumMap type chapter of Resume to store different sections with information about person.
 * Search key type - SectionType.
 * Data type - Section.
 */

public class Sections extends AbstractEnumChapter<SectionType, Section> {
    @Serial
    private static final long serialVersionUID = 1L;

    public Sections() {
        super(new EnumMap<>(SectionType.class));
    }

    @Override
    protected String title(SectionType key) {
        return key.getTitle();
    }

    @Override
    protected Section getDefault() {
        return new TextSection("");
    }
}
