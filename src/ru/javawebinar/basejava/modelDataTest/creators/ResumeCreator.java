package ru.javawebinar.basejava.modelDataTest.creators;

import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.model.enumKeyTypes.ContactType;
import ru.javawebinar.basejava.model.enumKeyTypes.SectionType;

import java.util.EnumMap;

public class ResumeCreator {
    public static Resume createResume() {
        return new Resume("Григорий Кислин") {{
            setContacts(new EnumMap<>(ContactType.class) {{
                ContactsCreator.create().forEach(contact -> this.put(contact.getKey(), contact.getValue()));
            }});
            setSections(new EnumMap<>(SectionType.class) {{
                SectionsCreator.create().forEach(section -> this.put(section.getKey(), section.getValue()));
            }});
        }};
    }
    public static Resume createResume(String uuid, String fullName) {
        return new Resume(uuid, fullName) {{
            setContacts(new EnumMap<>(ContactType.class) {{
                ContactsCreator.create().forEach(contact -> this.put(contact.getKey(), contact.getValue()));
            }});
            setSections(new EnumMap<>(SectionType.class) {{
                SectionsCreator.create().forEach(section -> this.put(section.getKey(), section.getValue()));
            }});
        }};
    }

}
