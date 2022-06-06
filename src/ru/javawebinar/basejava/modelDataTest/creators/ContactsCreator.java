package ru.javawebinar.basejava.modelDataTest.creators;

import ru.javawebinar.basejava.model.enumKeyTypes.ContactType;

import java.util.EnumMap;
import java.util.Map;
import java.util.Set;

import static ru.javawebinar.basejava.model.enumKeyTypes.ContactType.*;

public class ContactsCreator {
    public static Set<Map.Entry<ContactType, String>> create() {
        return new EnumMap<ContactType, String>(ContactType.class) {{
            put(TEL, "+79218550482");
            put(SKYPE, "skype:grigory.kislin");
        }}.entrySet();
    }
}
