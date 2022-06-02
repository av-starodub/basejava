package ru.javawebinar.basejava.modelDataTest.creators;

import ru.javawebinar.basejava.model.enumKeyTypes.SectionType;
import ru.javawebinar.basejava.model.interfaces.Section;
import ru.javawebinar.basejava.modelDataTest.testData.*;

import java.util.EnumMap;
import java.util.Map;
import java.util.Set;

import static ru.javawebinar.basejava.model.enumKeyTypes.SectionType.*;

public class SectionsCreator {
    public static Set<Map.Entry<SectionType, Section>> create() {
        return new EnumMap<SectionType, Section>(SectionType.class) {{
            put(PERSONAL, Personal.createSection());
            put(OBJECTIVE, Objective.createSection());
            put(ACHIEVEMENT, Achievement.createSection());
            put(QUALIFICATIONS, Qualifications.createSection());
            put(EXPERIENCE, Experience.createSection());
            put(EDUCATION, Education.createSection());
        }}.entrySet();
    }
}
