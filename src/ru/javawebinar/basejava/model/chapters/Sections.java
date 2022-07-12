package ru.javawebinar.basejava.model.chapters;

import ru.javawebinar.basejava.model.enumKeyTypes.SectionType;
import ru.javawebinar.basejava.model.interfaces.Section;
import ru.javawebinar.basejava.model.sections.ListItemSection;
import ru.javawebinar.basejava.model.sections.ListStringSection;
import ru.javawebinar.basejava.model.sections.TextSection;

import java.io.Serial;
import java.util.ArrayList;
import java.util.EnumMap;

import static ru.javawebinar.basejava.model.enumKeyTypes.SectionType.*;

/**
 * The class is EnumMap type chapter of Resume to store different sections with information about person.
 * Search key type - SectionType.
 * Data type - Section.
 */

public class Sections extends AbstractEnumChapter<SectionType, Section> {
    @Serial
    private static final long serialVersionUID = 1L;

    public Sections() {
        super(new EnumMap<>(SectionType.class) {{
            put(PERSONAL, new TextSection(""));
            put(OBJECTIVE, new TextSection(""));
            put(ACHIEVEMENT, new ListStringSection(new ArrayList<>()));
            put(QUALIFICATIONS, new ListStringSection(new ArrayList<>()));
            put(EXPERIENCE, new ListItemSection(new ArrayList<>()));
            put(EDUCATION, new ListItemSection( new ArrayList<>()));
        }});
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
