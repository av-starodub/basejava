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

public class Experience {

    public static ListItemSection createSection() {
        return new ListItemSection(new ArrayList<>() {{
            add(new Item(
                    new EnumMap<>(HeaderType.class) {{
                        put(TITLE, "Java Online Projects");
                        put(LINK, "https://javaops.ru/");
                    }},
                    new ArrayList<>() {{
                        add(new Info() {{
                            save(new EnumMap<>(InfoType.class) {{
                                put(START, "10/2013");
                                put(END, "Сейчас");
                                put(HEADER, "Автор проекта");
                                put(DESCRIPTION, "Создание, организация и проведение Java онлайн проектов и стажировок.");
                            }});
                        }});
                    }}));
        }});
    }
}
