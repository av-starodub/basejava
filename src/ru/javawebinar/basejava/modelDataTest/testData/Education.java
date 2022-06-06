package ru.javawebinar.basejava.modelDataTest.testData;

import ru.javawebinar.basejava.model.enumKeyTypes.HeaderType;
import ru.javawebinar.basejava.model.enumKeyTypes.InfoType;
import ru.javawebinar.basejava.model.item.Info;
import ru.javawebinar.basejava.model.item.Item;
import ru.javawebinar.basejava.model.sections.ListItemSection;

import java.util.ArrayList;
import java.util.EnumMap;

import static ru.javawebinar.basejava.model.enumKeyTypes.HeaderType.*;
import static ru.javawebinar.basejava.model.enumKeyTypes.InfoType.*;

public class Education {

    public static ListItemSection createSection() {
        return new ListItemSection(new ArrayList<>() {{
            add(new Item(new EnumMap<>(HeaderType.class) {{
                put(TITLE, "Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики");
                put(LINK, "https://www.ifmo.ru/");
            }}, new ArrayList<>() {{
                add(new Info() {{
                    save(new EnumMap<>(InfoType.class) {{
                        put(START, "09/1993");
                        put(END, "07/1996");
                        put(HEADER, "Аспирантура (программист С, С++)");
                    }});
                }});
                add(new Info() {{
                    save(new EnumMap<>(InfoType.class) {{
                        put(START, "09/1987");
                        put(END, "07/1993");
                        put(HEADER, "Инженер (программист Fortran, C)");
                    }});
                }});
            }}));
        }});

    }
}